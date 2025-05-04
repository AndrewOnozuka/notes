# %% [markdown]
# ## Homework 3: Symbolic Music Generation Using Markov Chains

# %% [markdown]
# **Before starting the homework:**
# 
# Please run `pip install miditok` to install the [MiDiTok](https://github.com/Natooz/MidiTok) package, which simplifies MIDI file processing by making note and beat extraction more straightforward.
# 
# You’re also welcome to experiment with other MIDI processing libraries such as [mido](https://github.com/mido/mido), [pretty_midi](https://github.com/craffel/pretty-midi) and [miditoolkit](https://github.com/YatingMusic/miditoolkit). However, with these libraries, you’ll need to handle MIDI quantization yourself, for example, converting note-on/note-off events into beat positions and durations.

# %%
# run this command to install MiDiTok
# ! pip install miditok

# %%
# import required packages
import random
from glob import glob
from collections import defaultdict

import numpy as np
from numpy.random import choice

from symusic import Score
from miditok import REMI, TokenizerConfig
from midiutil import MIDIFile

# %%
# You can change the random seed but try to keep your results deterministic!
# If I need to make changes to the autograder it'll require rerunning your code,
# so it should ideally generate the same results each time.
random.seed(42)

# %% [markdown]
# ### Load music dataset
# We will use a subset of the [PDMX dataset](https://zenodo.org/records/14984509). 
# 
# Please find the link in the homework spec.
# 
# All pieces are monophonic music (i.e. one melody line) in 4/4 time signature.

# %%
midi_files = glob('PDMX_subset/*.mid')
len(midi_files)

# %% [markdown]
# ### Train a tokenizer with the REMI method in MidiTok

# %%
config = TokenizerConfig(num_velocities=1, use_chords=False, use_programs=False)
tokenizer = REMI(config)
tokenizer.train(vocab_size=1000, files_paths=midi_files)

# %% [markdown]
# ### Use the trained tokenizer to get tokens for each midi file
# In REMI representation, each note will be represented with four tokens: `Position, Pitch, Velocity, Duration`, e.g. `('Position_28', 'Pitch_74', 'Velocity_127', 'Duration_0.4.8')`; a `Bar_None` token indicates the beginning of a new bar.

# %%
# e.g.:
midi = Score(midi_files[0])
tokens = tokenizer(midi)[0].tokens
tokens[:10]

# %% [markdown]
# 1. Write a function to extract note pitch events from a midi file; and another extract all note pitch events from the dataset and output a dictionary that maps note pitch events to the number of times they occur in the files. (e.g. {60: 120, 61: 58, …}).
# 
# `note_extraction()`
# - **Input**: a midi file
# 
# - **Output**: a list of note pitch events (e.g. [60, 62, 61, ...])
# 
# `note_frequency()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: a dictionary that maps note pitch events to the number of times they occur, e.g {60: 120, 61: 58, …}

# %%
# def note_extraction(midi_file):
#     midi = Score.from_midi(open(midi_file, "rb").read())
#     return [note.pitch for note in midi.notes]

def note_extraction(midi_file):
    tokens = tokenizer(Score(midi_file))[0].tokens

    pitches = []
    for i in range(len(tokens) - 3):
        if tokens[i].startswith("Position_") and \
           tokens[i+1].startswith("Pitch_") and \
           tokens[i+2].startswith("Velocity_") and \
           tokens[i+3].startswith("Duration_"):
            pitch_val = int(tokens[i+1].split("_")[1])
            pitches.append(pitch_val)
    return pitches

# %%
def note_frequency(midi_files):
    pitch_counts = defaultdict(int)
    for file in midi_files:
        notes = note_extraction(file)
        for pitch in notes:
            pitch_counts[pitch] += 1
    return dict(pitch_counts)

# %% [markdown]
# 2. Write a function to normalize the above dictionary to produce probability scores (e.g. {60: 0.13, 61: 0.065, …})
# 
# `note_unigram_probability()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: a dictionary that maps note pitch events to probabilities, e.g. {60: 0.13, 61: 0.06, …}

# %%
def note_unigram_probability(midi_files):
    note_counts = note_frequency(midi_files)
    total_count = sum(note_counts.values())
    unigramProbabilities = {}

    for pitch, count in note_counts.items():
        unigramProbabilities[pitch] = count / total_count

    return unigramProbabilities

# %% [markdown]
# 3. Generate a table of pairwise probabilities containing p(next_note | previous_note) values for the dataset; write a function that randomly generates the next note based on the previous note based on this distribution.
# 
# `note_bigram_probability()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: two dictionaries:
# 
#   - `bigramTransitions`: key: previous_note, value: a list of next_note, e.g. {60:[62, 64, ..], 62:[60, 64, ..], ...} (i.e., this is a list of every other note that occured after note 60, every note that occured after note 62, etc.)
# 
#   - `bigramTransitionProbabilities`: key:previous_note, value: a list of probabilities for next_note in the same order of `bigramTransitions`, e.g. {60:[0.3, 0.4, ..], 62:[0.2, 0.1, ..], ...} (i.e., you are converting the values above to probabilities)
# 
# `sample_next_note()`
# - **Input**: a note
# 
# - **Output**: next note sampled from pairwise probabilities

# %%
def note_bigram_probability(midi_files):
    bigramTransitions = defaultdict(list)
    bigramTransitionProbabilities = {}

    bigramCounts = defaultdict(lambda: defaultdict(int))

    for file in midi_files:
        notes = note_extraction(file)
        for i in range(len(notes) - 1):
            prev_note = notes[i]
            next_note = notes[i + 1]
            bigramCounts[prev_note][next_note] += 1

    for prev_note, next_notes_dict in bigramCounts.items():
        next_notes = list(next_notes_dict.keys())
        counts = list(next_notes_dict.values())
        total = sum(counts)
        probs = [count / total for count in counts]

        bigramTransitions[prev_note] = next_notes
        bigramTransitionProbabilities[prev_note] = probs

    return bigramTransitions, bigramTransitionProbabilities

# %%
def sample_next_note(note, bigramTransitions=None, bigramTransitionProbabilities=None):
    # If not passed in, compute them
    if bigramTransitions is None or bigramTransitionProbabilities is None:
        bigramTransitions, bigramTransitionProbabilities = note_bigram_probability(midi_files)

    if note not in bigramTransitions:
        return random.choice(list(bigramTransitions.keys()))

    return random.choices(
        population=bigramTransitions[note],
        weights=bigramTransitionProbabilities[note]
    )[0]

# %% [markdown]
# 4. Write a function to calculate the perplexity of your model on a midi file.
# 
#     The perplexity of a model is defined as 
# 
#     $\quad \text{exp}(-\frac{1}{N} \sum_{i=1}^N \text{log}(p(w_i|w_{i-1})))$
# 
#     where $p(w_1|w_0) = p(w_1)$, $p(w_i|w_{i-1}) (i>1)$ refers to the pairwise probability p(next_note | previous_note).
# 
# `note_bigram_perplexity()`
# - **Input**: a midi file
# 
# - **Output**: perplexity value

# %%
def note_bigram_perplexity(midi_file):
    unigramProbabilities = note_unigram_probability(midi_files)
    bigramTransitions, bigramTransitionProbabilities = note_bigram_probability(midi_files)

    notes = note_extraction(midi_file)
    if len(notes) < 2:
        return float('inf')  # not enough data to compute perplexity

    log_probs = []

    for i in range(len(notes)):
        if i == 0:
            prob = unigramProbabilities.get(notes[i], 1e-12)  # fallback to small prob
        else:
            prev = notes[i - 1]
            curr = notes[i]
            if prev in bigramTransitions and curr in bigramTransitions[prev]:
                idx = bigramTransitions[prev].index(curr)
                prob = bigramTransitionProbabilities[prev][idx]
            else:
                prob = 1e-12  # small fallback probability for unseen bigrams

        log_probs.append(np.log(prob))

    perplexity = np.exp(-np.mean(log_probs))
    return perplexity

# %% [markdown]
# 5. Implement a second-order Markov chain, i.e., one which estimates p(next_note | next_previous_note, previous_note); write a function to compute the perplexity of this new model on a midi file. 
# 
#     The perplexity of this model is defined as 
# 
#     $\quad \text{exp}(-\frac{1}{N} \sum_{i=1}^N \text{log}(p(w_i|w_{i-2}, w_{i-1})))$
# 
#     where $p(w_1|w_{-1}, w_0) = p(w_1)$, $p(w_2|w_0, w_1) = p(w_2|w_1)$, $p(w_i|w_{i-2}, w_{i-1}) (i>2)$ refers to the probability p(next_note | next_previous_note, previous_note).
# 
# 
# `note_trigram_probability()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: two dictionaries:
# 
#   - `trigramTransitions`: key - (next_previous_note, previous_note), value - a list of next_note, e.g. {(60, 62):[64, 66, ..], (60, 64):[60, 64, ..], ...}
# 
#   - `trigramTransitionProbabilities`: key: (next_previous_note, previous_note), value: a list of probabilities for next_note in the same order of `trigramTransitions`, e.g. {(60, 62):[0.2, 0.2, ..], (60, 64):[0.4, 0.1, ..], ...}
# 
# `note_trigram_perplexity()`
# - **Input**: a midi file
# 
# - **Output**: perplexity value

# %%
def note_trigram_probability(midi_files):
    trigramTransitions = defaultdict(list)
    trigramTransitionProbabilities = defaultdict(list)

    # Step 1: Count trigram occurrences
    trigramCounts = defaultdict(lambda: defaultdict(int))

    for file in midi_files:
        notes = note_extraction(file)
        for i in range(2, len(notes)):
            prev_prev = notes[i - 2]
            prev = notes[i - 1]
            curr = notes[i]
            key = (prev_prev, prev)
            trigramCounts[key][curr] += 1

    # Step 2: Convert counts to transitions and probabilities
    for key, next_note_dict in trigramCounts.items():
        next_notes = list(next_note_dict.keys())
        counts = list(next_note_dict.values())
        total = sum(counts)
        probs = [count / total for count in counts]

        trigramTransitions[key] = next_notes
        trigramTransitionProbabilities[key] = probs

    return trigramTransitions, trigramTransitionProbabilities

# %%
def note_trigram_perplexity(midi_file):
    unigramProbabilities = note_unigram_probability(midi_files)
    bigramTransitions, bigramTransitionProbabilities = note_bigram_probability(midi_files)
    trigramTransitions, trigramTransitionProbabilities = note_trigram_probability(midi_files)

    notes = note_extraction(midi_file)
    if len(notes) < 3:
        return float('inf')  # not enough notes for trigram

    log_probs = []

    for i in range(len(notes)):
        if i == 0:
            # p(w1) ~ unigram
            prob = unigramProbabilities.get(notes[i], 1e-12)
        elif i == 1:
            # p(w2|w1) ~ bigram
            prev = notes[i - 1]
            curr = notes[i]
            if prev in bigramTransitions and curr in bigramTransitions[prev]:
                idx = bigramTransitions[prev].index(curr)
                prob = bigramTransitionProbabilities[prev][idx]
            else:
                prob = 1e-12
        else:
            # p(w_i | w_{i-2}, w_{i-1}) ~ trigram
            prev_prev = notes[i - 2]
            prev = notes[i - 1]
            curr = notes[i]
            key = (prev_prev, prev)
            if key in trigramTransitions and curr in trigramTransitions[key]:
                idx = trigramTransitions[key].index(curr)
                prob = trigramTransitionProbabilities[key][idx]
            else:
                prob = 1e-12

        log_probs.append(np.log(prob))

    perplexity = np.exp(-np.mean(log_probs))
    return perplexity

# %% [markdown]
# 6. Our model currently doesn’t have any knowledge of beats. Write a function that extracts beat lengths and outputs a list of [(beat position; beat length)] values.
# 
#     Recall that each note will be encoded as `Position, Pitch, Velocity, Duration` using REMI. Please keep the `Position` value for beat position, and convert `Duration` to beat length using provided lookup table `duration2length` (see below).
# 
#     For example, for a note represented by four tokens `('Position_24', 'Pitch_72', 'Velocity_127', 'Duration_0.4.8')`, the extracted (beat position; beat length) value is `(24, 4)`.
# 
#     As a result, we will obtain a list like [(0,8),(8,16),(24,4),(28,4),(0,4)...], where the next beat position is the previous beat position + the beat length. As we divide each bar into 32 positions by default, when reaching the end of a bar (i.e. 28 + 4 = 32 in the case of (28, 4)), the beat position reset to 0.

# %%
duration2length = {
    '0.2.8': 2,  # sixteenth note, 0.25 beat in 4/4 time signature
    '0.4.8': 4,  # eighth note, 0.5 beat in 4/4 time signature
    '1.0.8': 8,  # quarter note, 1 beat in 4/4 time signature
    '2.0.8': 16, # half note, 2 beats in 4/4 time signature
    '4.0.4': 32, # whole note, 4 beats in 4/4 time signature
}

# %% [markdown]
# `beat_extraction()`
# - **Input**: a midi file
# 
# - **Output**: a list of (beat position; beat length) values

# %%
def beat_extraction(midi_file):
    # Tokenizer setup (must match what we trained with)
    config = TokenizerConfig(num_velocities=1, use_chords=False, use_programs=False)
    tokenizer = REMI(config)

    midi = Score(midi_file)
    tokens = tokenizer(midi)[0].tokens

    duration2length = {
        '0.2.8': 2,  # sixteenth note
        '0.4.8': 4,  # eighth note
        '1.0.8': 8,  # quarter note
        '2.0.8': 16, # half note
        '4.0.4': 32  # whole note
    }

    beat_position = 0
    beat_sequence = []

    i = 0
    while i < len(tokens) - 3:
        # Look for a sequence like: Position, Pitch, Velocity, Duration
        if tokens[i].startswith("Position_") and \
           tokens[i+1].startswith("Pitch_") and \
           tokens[i+2].startswith("Velocity_") and \
           tokens[i+3].startswith("Duration_"):

            pos = int(tokens[i].split("_")[1])
            dur_str = tokens[i+3].split("_")[1]

            # Convert duration to length using lookup
            if dur_str in duration2length:
                beat_len = duration2length[dur_str]
                beat_sequence.append((pos, beat_len))
            i += 4  # advance past this note
        else:
            i += 1  # move forward if format doesn't match

    return beat_sequence

# %% [markdown]
# 7. Implement a Markov chain that computes p(beat_length | previous_beat_length) based on the above function.
# 
# `beat_bigram_probability()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: two dictionaries:
# 
#   - `bigramBeatTransitions`: key: previous_beat_length, value: a list of beat_length, e.g. {4:[8, 2, ..], 8:[8, 4, ..], ...}
# 
#   - `bigramBeatTransitionProbabilities`: key - previous_beat_length, value - a list of probabilities for beat_length in the same order of `bigramBeatTransitions`, e.g. {4:[0.3, 0.2, ..], 8:[0.4, 0.4, ..], ...}

# %%
def beat_bigram_probability(midi_files):
    bigramBeatTransitions = defaultdict(list)
    bigramBeatTransitionProbabilities = defaultdict(list)
    
    # Count bigram transitions
    beatCounts = defaultdict(lambda: defaultdict(int))

    for file in midi_files:
        beats = beat_extraction(file)
        beat_lengths = [length for _, length in beats]

        for i in range(len(beat_lengths) - 1):
            prev_len = beat_lengths[i]
            curr_len = beat_lengths[i + 1]
            beatCounts[prev_len][curr_len] += 1

    # Convert to transition lists and probabilities
    for prev_len, next_len_dict in beatCounts.items():
        next_lengths = list(next_len_dict.keys())
        counts = list(next_len_dict.values())
        total = sum(counts)
        probs = [count / total for count in counts]

        bigramBeatTransitions[prev_len] = next_lengths
        bigramBeatTransitionProbabilities[prev_len] = probs
    
    return bigramBeatTransitions, bigramBeatTransitionProbabilities

# %% [markdown]
# 8. Implement a function to compute p(beat length | beat position), and compute the perplexity of your models from Q7 and Q8. For both models, we only consider the probabilities of predicting the sequence of **beat lengths**.
# 
# `beat_pos_bigram_probability()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: two dictionaries:
# 
#   - `bigramBeatPosTransitions`: key - beat_position, value - a list of beat_length
# 
#   - `bigramBeatPosTransitionProbabilities`: key - beat_position, value - a list of probabilities for beat_length in the same order of `bigramBeatPosTransitions`
# 
# `beat_bigram_perplexity()`
# - **Input**: a midi file
# 
# - **Output**: two perplexity values correspond to the models in Q7 and Q8, respectively

# %%
def beat_pos_bigram_probability(midi_files):
    bigramBeatPosTransitions = defaultdict(list)
    bigramBeatPosTransitionProbabilities = defaultdict(list)
    
    # Count (position → beat_length) transitions
    beatPosCounts = defaultdict(lambda: defaultdict(int))

    for file in midi_files:
        beats = beat_extraction(file)
        for pos, length in beats:
            beatPosCounts[pos][length] += 1

    # Convert to transition lists and probabilities
    for pos, length_dict in beatPosCounts.items():
        next_lengths = list(length_dict.keys())
        counts = list(length_dict.values())
        total = sum(counts)
        probs = [count / total for count in counts]

        bigramBeatPosTransitions[pos] = next_lengths
        bigramBeatPosTransitionProbabilities[pos] = probs
    
    return bigramBeatPosTransitions, bigramBeatPosTransitionProbabilities

# %%
def beat_bigram_perplexity(midi_file):
    from math import log, exp

    bigramBeatTransitions, bigramBeatTransitionProbabilities = beat_bigram_probability(midi_files)
    beatPosTransitions, beatPosTransitionProbabilities = beat_pos_bigram_probability(midi_files)

    beats = beat_extraction(midi_file)
    beat_lengths = [length for _, length in beats]
    beat_positions = [pos for pos, _ in beats]

    if not beat_lengths:
        return float('inf'), float('inf')

    log_probs_q7 = []
    log_probs_q8 = []

    # Q7: use unigram fallback for first beat (optional: could be uniform or estimated)
    first_len = beat_lengths[0]
    q7_init_prob = 1.0 / sum(len(v) for v in bigramBeatTransitions.values())
    log_probs_q7.append(log(q7_init_prob))

    # Q8: first beat length given position
    first_pos = beat_positions[0]
    if first_pos in beatPosTransitions and first_len in beatPosTransitions[first_pos]:
        idx = beatPosTransitions[first_pos].index(first_len)
        prob = beatPosTransitionProbabilities[first_pos][idx]
    else:
        prob = 1e-12
    log_probs_q8.append(log(prob))

    # Continue with rest of sequence
    for i in range(1, len(beat_lengths)):
        prev_len = beat_lengths[i - 1]
        curr_len = beat_lengths[i]
        curr_pos = beat_positions[i]

        # Q7: P(curr_len | prev_len)
        if prev_len in bigramBeatTransitions and curr_len in bigramBeatTransitions[prev_len]:
            idx = bigramBeatTransitions[prev_len].index(curr_len)
            prob = bigramBeatTransitionProbabilities[prev_len][idx]
        else:
            prob = 1e-12
        log_probs_q7.append(log(prob))

        # Q8: P(curr_len | position)
        if curr_pos in beatPosTransitions and curr_len in beatPosTransitions[curr_pos]:
            idx = beatPosTransitions[curr_pos].index(curr_len)
            prob = beatPosTransitionProbabilities[curr_pos][idx]
        else:
            prob = 1e-12
        log_probs_q8.append(log(prob))

    perplexity_q7 = exp(-sum(log_probs_q7) / len(log_probs_q7))
    perplexity_q8 = exp(-sum(log_probs_q8) / len(log_probs_q8))

    return perplexity_q7, perplexity_q8

# %% [markdown]
# 9. Implement a Markov chain that computes p(beat_length | previous_beat_length, beat_position), and report its perplexity. 
# 
# `beat_trigram_probability()`
# - **Input**: all midi files `midi_files`
# 
# - **Output**: two dictionaries:
# 
#   - `trigramBeatTransitions`: key: (previous_beat_length, beat_position), value: a list of beat_length
# 
#   - `trigramBeatTransitionProbabilities`: key: (previous_beat_length, beat_position), value: a list of probabilities for beat_length in the same order of `trigramBeatTransitions`
# 
# `beat_trigram_perplexity()`
# - **Input**: a midi file
# 
# - **Output**: perplexity value

# %%
def beat_trigram_probability(midi_files):
    trigramBeatTransitions = defaultdict(list)
    trigramBeatTransitionProbabilities = defaultdict(list)

    # Count (prev_len, position) → curr_len transitions
    trigramCounts = defaultdict(lambda: defaultdict(int))

    for file in midi_files:
        beats = beat_extraction(file)
        for i in range(1, len(beats)):
            prev_len = beats[i - 1][1]
            curr_pos = beats[i][0]
            curr_len = beats[i][1]
            key = (prev_len, curr_pos)
            trigramCounts[key][curr_len] += 1

    # Convert counts to probabilities
    for key, next_lengths_dict in trigramCounts.items():
        next_lengths = list(next_lengths_dict.keys())
        counts = list(next_lengths_dict.values())
        total = sum(counts)
        probs = [count / total for count in counts]

        trigramBeatTransitions[key] = next_lengths
        trigramBeatTransitionProbabilities[key] = probs
    
    return trigramBeatTransitions, trigramBeatTransitionProbabilities

# %%
def beat_trigram_perplexity(midi_file):
    from math import log, exp

    trigramTransitions, trigramProbs = beat_trigram_probability(midi_files)
    bigramTransitions, bigramProbs = beat_bigram_probability(midi_files)

    beats = beat_extraction(midi_file)
    beat_lengths = [length for _, length in beats]
    beat_positions = [pos for pos, _ in beats]

    if not beat_lengths:
        return float('inf')

    log_probs = []

    for i in range(len(beat_lengths)):
        if i == 0:
            # Unigram fallback: assume uniform over all seen beat lengths
            vocab_size = sum(len(v) for v in bigramTransitions.values())
            prob = 1.0 / vocab_size if vocab_size > 0 else 1e-12
        elif i == 1:
            # Bigram: P(l1 | l0)
            prev = beat_lengths[i - 1]
            curr = beat_lengths[i]
            if prev in bigramTransitions and curr in bigramTransitions[prev]:
                idx = bigramTransitions[prev].index(curr)
                prob = bigramProbs[prev][idx]
            else:
                prob = 1e-12
        else:
            # Trigram: P(l_i | l_{i-1}, pos_i)
            prev_len = beat_lengths[i - 1]
            curr_pos = beat_positions[i]
            curr_len = beat_lengths[i]
            key = (prev_len, curr_pos)
            if key in trigramTransitions and curr_len in trigramTransitions[key]:
                idx = trigramTransitions[key].index(curr_len)
                prob = trigramProbs[key][idx]
            else:
                prob = 1e-12

        log_probs.append(log(prob))

    perplexity = exp(-sum(log_probs) / len(log_probs))
    return perplexity

# %% [markdown]
# 10. Use the model from Q5 to generate N notes, and the model from Q8 to generate beat lengths for each note. Save the generated music as a midi file (see code from workbook1) as q10.mid. Remember to reset the beat position to 0 when reaching the end of a bar.
# 
# `music_generate`
# - **Input**: target length, e.g. 500
# 
# - **Output**: a midi file q10.mid
# 
# Note: the duration of one beat in MIDIUtil is 1, while in MidiTok is 8. Divide beat length by 8 if you use methods in MIDIUtil to save midi files.

# %%
def music_generate(length):
    # sample notes using Q5 trigram model
    unigramProbabilities = note_unigram_probability(midi_files)
    bigramTransitions, bigramTransitionProbabilities = note_bigram_probability(midi_files)
    trigramTransitions, trigramTransitionProbabilities = note_trigram_probability(midi_files)
    
    sampled_notes = []

    # Start with two seed notes
    notes = list(unigramProbabilities.keys())
    w1 = random.choices(population=notes, weights=unigramProbabilities.values())[0]
    w2 = sample_next_note(w1, bigramTransitions, bigramTransitionProbabilities)
    sampled_notes.extend([w1, w2])

    for _ in range(length - 2):
        key = (sampled_notes[-2], sampled_notes[-1])
        if key in trigramTransitions:
            sampled = random.choices(
                population=trigramTransitions[key],
                weights=trigramTransitionProbabilities[key]
            )[0]
        else:
            sampled = sample_next_note(sampled_notes[-1], bigramTransitions, bigramTransitionProbabilities)
        sampled_notes.append(sampled)

    # Sample beat lengths using Q8: beat_pos_bigram_probability
    beat_pos_transitions, beat_pos_probs = beat_pos_bigram_probability(midi_files)
    beat_lengths = []
    current_position = 0

    for _ in range(length):
        pos = current_position

        if pos in beat_pos_transitions:
            beat_len = random.choices(
                population=beat_pos_transitions[pos],
                weights=beat_pos_probs[pos]
            )[0]
        else:
            beat_len = random.choice([2, 4, 8, 16])  # fallback if unseen

        beat_lengths.append(beat_len)
        current_position += beat_len
        if current_position >= 32:
            current_position = 0

    # Write to MIDI
    midi = MIDIFile(1)
    track = 0
    time = 0.0
    midi.addTrackName(track, time, "Generated Track")
    midi.addTempo(track, time, 120)

    for pitch, beat_len in zip(sampled_notes, beat_lengths):
        duration = beat_len / 8.0  # MiDiUtil uses 1.0 per beat
        midi.addNote(track, channel=0, pitch=pitch, time=time, duration=duration, volume=100)
        time += duration

    with open("q10.mid", "wb") as f:
        midi.writeFile(f)

# %%
if __name__ == "__main__":
    # Only runs when called directly, not when imported by the autograder
    music_generate(500)


