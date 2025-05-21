# %%
# Probably more imports than are really necessary...
import os
import torch
import torchaudio
from torch.utils.data import Dataset, DataLoader, random_split
import torch.nn as nn
import torch.nn.functional as F
from torchaudio.transforms import MelSpectrogram, AmplitudeToDB
from tqdm import tqdm
import librosa
import numpy as np
import miditoolkit
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import f1_score, average_precision_score, accuracy_score
import random
from music21 import converter, chord
from sklearn.model_selection import train_test_split
from sklearn.ensemble import HistGradientBoostingClassifier
from sklearn.feature_extraction.text import TfidfVectorizer
from tqdm import tqdm
from sklearn.preprocessing import StandardScaler

# %% [markdown]
# ## Metrics

# %%
def accuracy1(groundtruth, predictions):
    correct = 0
    for k in groundtruth:
        if not (k in predictions):
            print("Missing " + str(k) + " from predictions")
            return 0
        if predictions[k] == groundtruth[k]:
            correct += 1
    return correct / len(groundtruth)

# %%
def accuracy2(groundtruth, predictions):
    correct = 0
    for k in groundtruth:
        if not (k in predictions):
            print("Missing " + str(k) + " from predictions")
            return 0
        if predictions[k] == groundtruth[k]:
            correct += 1
    return correct / len(groundtruth)

# %%
TAGS = ['rock', 'oldies', 'jazz', 'pop', 'dance',  'blues',  'punk', 'chill', 'electronic', 'country']

# %%
def accuracy3(groundtruth, predictions):
    preds, targets = [], []
    for k in groundtruth:
        if not (k in predictions):
            print("Missing " + str(k) + " from predictions")
            return 0
        prediction = [1 if tag in predictions[k] else 0 for tag in TAGS]
        target = [1 if tag in groundtruth[k] else 0 for tag in TAGS]
        preds.append(prediction)
        targets.append(target)
    
    mAP = average_precision_score(targets, preds, average='macro')
    return mAP

# %% [markdown]
# ## Task 1: Composer classification

# %%
dataroot1 = "student_files/task1_composer_classification/"

# %%
class model1():

    def __init__(self):
        self.vectorizer = TfidfVectorizer(max_features=100)

    def get_all_notes(self, midi_obj):
        notes = []
        for inst in midi_obj.instruments:
            notes.extend(inst.notes)
        return sorted(notes, key=lambda n: n.start)

    def get_chord_sequence(self, path):

        try:
            score = converter.parse(dataroot1 + '/' + path)
            chords = score.chordify().recurse().getElementsByClass('Chord')
            roman_progression = [chord.Chord(c.pitches).pitchedCommonName for c in chords]
            return ' '.join(roman_progression)
        
        except:
            return ""
        
    def get_pitch_features(self, notes):
        if not notes:
            return [0] * (6 + 12 + 12 + 144)

        total_time = notes[-1].end
        stats = [
            min(note.pitch for note in notes),
            max(note.pitch for note in notes),
            len(set(note.pitch for note in notes)),
            sum(note.pitch for note in notes) / len(notes),
            sum(note.end - note.start for note in notes) / len(notes),
            len(notes) / total_time if total_time > 0 else 0,
        ]
        pitch_hist = [0] * 12

        for note in notes:
            pitch_hist[note.pitch % 12] += 1

        total = sum(pitch_hist)
        pitch_hist = [x / total for x in pitch_hist] if total > 0 else pitch_hist
        interval_hist = [0] * 12

        if len(notes) >= 2:
            intervals = [abs(notes[i].pitch - notes[i-1].pitch) % 12 for i in range(1, len(notes))]

            for i in intervals:
                interval_hist[i] += 1

            total = sum(interval_hist)
            interval_hist = [x / total for x in interval_hist] if total > 0 else interval_hist

        bigram_hist = [0] * 144

        if len(notes) >= 3:

            bigrams = [(abs(notes[i].pitch - notes[i-1].pitch) % 12, abs(notes[i+1].pitch - notes[i].pitch) % 12) for i in range(len(notes) - 2)]

            for i1, i2 in bigrams:
                bigram_hist[i1 * 12 + i2] += 1

            total = sum(bigram_hist)
            bigram_hist = [x / total for x in bigram_hist] if total > 0 else bigram_hist

        return stats + pitch_hist + interval_hist + bigram_hist

    def get_meta(self, midi_obj, path):

        try:
            score = converter.parse(dataroot1 + '/' + path)
            key = score.analyze('key')
            tonic_class = key.tonic.pitchClass
            mode_val = 1 if key.mode == 'major' else 0

        except:
            tonic_class, mode_val = 0, 0

        if midi_obj.tempo_changes:
            tempo_micro = midi_obj.tempo_changes[0].tempo
            bpm = 60000000 / tempo_micro

        else:
            bpm = 120

        return [bpm, tonic_class, mode_val]

    def features(self, path, include_tfidf=False):

        midi_obj = miditoolkit.midi.parser.MidiFile(dataroot1 + '/' + path)
        notes = self.get_all_notes(midi_obj)
        chord_seq = self.get_chord_sequence(path)
        base_feats = self.get_pitch_features(notes) + self.get_meta(midi_obj, path)
        return (base_feats, chord_seq) if include_tfidf else base_feats

    def train(self, path):
        with open(path, 'r') as f:
            train_json = eval(f.read())

        paths = list(train_json.keys())
        labels = list(train_json.values())

        base_X = []
        chord_texts = []

        for p in tqdm(paths, desc="Extracting features"):
            bf, chord_str = self.features(p, include_tfidf=True)
            base_X.append(bf)
            chord_texts.append(chord_str)

        tfidf_X = self.vectorizer.fit_transform(chord_texts).toarray()
        X = [np.concatenate([b, t]) for b, t in zip(base_X, tfidf_X)]
        y = labels

        X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=0.2, random_state=42)

        self.model = HistGradientBoostingClassifier(
            max_iter=300,
            learning_rate=0.05,
            max_leaf_nodes=64,
            random_state=42
        )
        self.model.fit(X_train, y_train)
        val_preds = self.model.predict(X_val)
        print("Validation accuracy =", accuracy_score(y_val, val_preds))

    def predict(self, path, outpath=None):
        d = eval(open(path, 'r').read())
        predictions = {}

        for k in tqdm(d, desc="Predicting"):
            base_feats, chord_str = self.features(k, include_tfidf=True)
            tfidf_feats = self.vectorizer.transform([chord_str]).toarray()[0]
            x = np.concatenate([base_feats, tfidf_feats])
            pred = self.model.predict([x])
            predictions[k] = str(pred[0])

        if outpath:
            with open(outpath, "w") as z:
                z.write(str(predictions) + '\n')
        return predictions


# %% [markdown]
# ## Task 2: Sequence prediction

# %%
dataroot2 = "student_files/task2_next_sequence_prediction/"

# %%
class model2():
    def __init__(self, threshold=0.48):  # Allow tuning threshold
        self.threshold = threshold

    def extract_features(self, path):
        midi_path = dataroot2 + '/' + path
        midi_obj = miditoolkit.midi.parser.MidiFile(midi_path)

        # Return all zeros if MIDI is empty or broken
        if len(midi_obj.instruments) == 0 or len(midi_obj.instruments[0].notes) == 0:
            return [0] * (6 + 12 + 3 + 2)

        notes = sorted(midi_obj.instruments[0].notes, key=lambda n: n.start)
        total_time = midi_obj.max_tick / midi_obj.ticks_per_beat  # in beats

        # Base features
        features = [
            min(note.pitch for note in notes),
            max(note.pitch for note in notes),
            len(set(note.pitch for note in notes)),
            sum(note.pitch for note in notes) / len(notes),
            sum(note.end - note.start for note in notes) / len(notes),
            len(notes) / total_time if total_time > 0 else 0
        ]

        # Interval histogram
        intervals = [abs(notes[i].pitch - notes[i - 1].pitch) % 12 for i in range(1, len(notes))]
        interval_hist = [0] * 12
        for i in intervals:
            interval_hist[i] += 1
        total_int = sum(interval_hist)
        interval_hist = [x / total_int for x in interval_hist] if total_int > 0 else interval_hist

        # Rhythm features
        durations = [note.end - note.start for note in notes]
        rhythm_features = [
            np.mean(durations),
            np.std(durations),
            max(durations) / np.mean(durations) if np.mean(durations) > 0 else 0
        ]

        # Key signature features
        try:
            score = converter.parse(midi_path)
            key = score.analyze('key')
            key_features = [key.tonic.pitchClass, 1 if key.mode == 'major' else 0]
        except:
            key_features = [0, 0]

        return features + interval_hist + rhythm_features + key_features

    def combine_pair_features(self, f1, f2):
        f1 = np.array(f1)
        f2 = np.array(f2)
        return np.concatenate([
            f1,
            f2,
            np.abs(f1 - f2),
            f1 * f2,
            np.minimum(f1, f2),
            np.maximum(f1, f2),
            f1 / (f2 + 1e-6),
            f2 / (f1 + 1e-6)
        ]).tolist()

    def train(self, path):
        d = eval(open(path, 'r').read())
        X, y = [], []

        for (p1, p2), label in tqdm(d.items(), desc="Extracting train features"):
            f1 = self.extract_features(p1)
            f2 = self.extract_features(p2)
            combined = self.combine_pair_features(f1, f2)
            X.append(combined)
            y.append(label)

        self.model = lgb.LGBMClassifier(
            n_estimators=400,
            learning_rate=0.03,
            num_leaves=128,
            max_depth=10,
            reg_alpha=0.1,
            reg_lambda=0.1,
            colsample_bytree=0.9,
            subsample=0.8,
            random_state=42
        )
        self.model.fit(X, y)

    def predict(self, path, outpath=None):
        d = eval(open(path, 'r').read())
        predictions = {}

        for (p1, p2) in tqdm(d, desc="Predicting"):
            f1 = self.extract_features(p1)
            f2 = self.extract_features(p2)
            combined = self.combine_pair_features(f1, f2)
            prob = self.model.predict_proba([combined])[0][1]
            predictions[(p1, p2)] = prob > self.threshold

        if outpath:
            with open(outpath, "w") as z:
                z.write(str(predictions) + '\n')
        return predictions

# %% [markdown]
# ## Task 3: Audio classification

# %%
# Some constants (you can change any of these if useful)
SAMPLE_RATE = 16000
N_MELS = 64
N_CLASSES = 10
AUDIO_DURATION = 10 # seconds
BATCH_SIZE = 32

dataroot3 = "student_files/task3_audio_classification/"

def extract_waveform(path):
    waveform, sr = librosa.load(dataroot3 + '/' + path, sr=SAMPLE_RATE)
    waveform = np.array([waveform])
    if sr != SAMPLE_RATE:
        resample = torchaudio.transforms.Resample(orig_freq=sr, new_freq=SAMPLE_RATE)
        waveform = resample(waveform)
    # Pad so that everything is the right length
    target_len = SAMPLE_RATE * AUDIO_DURATION
    if waveform.shape[1] < target_len:
        pad_len = target_len - waveform.shape[1]
        waveform = F.pad(waveform, (0, pad_len))
    else:
        waveform = waveform[:, :target_len]
    waveform = torch.FloatTensor(waveform)
    return waveform

class AudioDataset(Dataset):
    def __init__(self, meta, preload = True):
        self.meta = meta
        ks = list(meta.keys())
        self.idToPath = dict(zip(range(len(ks)), ks))
        self.pathToFeat = {}

        self.mel = MelSpectrogram(sample_rate=SAMPLE_RATE, n_mels=N_MELS)
        self.db = AmplitudeToDB()
        
        self.preload = preload # Determines whether the features should be preloaded (uses more memory)
                               # or read from disk / computed each time (slow if your system is i/o-bound)
        if self.preload:
            for path in ks:
                waveform = extract_waveform(path)
                mel_spec = self.db(self.mel(waveform)).squeeze(0)
                self.pathToFeat[path] = mel_spec

    def __len__(self):
        return len(self.meta)
    
    def __getitem__(self, idx):
        # Faster version, preloads the features
        path = self.idToPath[idx]
        tags = self.meta[path]
        bin_label = torch.tensor([1 if tag in tags else 0 for tag in TAGS], dtype=torch.float32)

        if self.preload:
            mel_spec = self.pathToFeat[path]
        else:
            waveform = extract_waveform(path)
            mel_spec = self.db(self.mel(waveform)).squeeze(0)
        
        return mel_spec.unsqueeze(0), bin_label, path
    
class Loaders():
    def __init__(self, train_path, test_path, split_ratio=0.9, seed = 0):
        torch.manual_seed(seed)
        random.seed(seed)
        
        meta_train = eval(open(train_path, 'r').read())
        l_test = eval(open(test_path, 'r').read())
        meta_test = dict([(x,[]) for x in l_test]) # Need a dictionary for the above class
        
        all_train = AudioDataset(meta_train)
        test_set = AudioDataset(meta_test)
        
        # Split all_train into train + valid
        total_len = len(all_train)
        train_len = int(total_len * split_ratio)
        valid_len = total_len - train_len
        train_set, valid_set = random_split(all_train, [train_len, valid_len])
        
        self.loaderTrain = DataLoader(train_set, batch_size=BATCH_SIZE, shuffle=False, num_workers=0)
        self.loaderValid = DataLoader(valid_set, batch_size=BATCH_SIZE, shuffle=False, num_workers=0)
        self.loaderTest = DataLoader(test_set, batch_size=BATCH_SIZE, shuffle=False, num_workers=0)

class CNNClassifier(nn.Module):
    def __init__(self, n_classes=N_CLASSES):
        super(CNNClassifier, self).__init__()
        self.conv1 = nn.Conv2d(1, 16, 3, padding=1)
        self.conv2 = nn.Conv2d(16, 32, 3, padding=1)
        self.pool = nn.MaxPool2d(2, 2)
        self.dropout = nn.Dropout(0.3)
        self.fc1 = nn.Linear(32 * (N_MELS // 4) * (801 // 4), 256)
        self.fc2 = nn.Linear(256, n_classes)

    def forward(self, x):
        x = self.pool(F.relu(self.conv1(x)))  # (B, 16, mel/2, time/2)
        x = self.pool(F.relu(self.conv2(x)))  # (B, 32, mel/4, time/4)
        x = x.view(x.size(0), -1)
        x = self.dropout(F.relu(self.fc1(x)))
        return torch.sigmoid(self.fc2(x))  # multilabel → sigmoid
    
class Pipeline():
    def __init__(self, model, learning_rate, seed = 0):
        # These two lines will (mostly) make things deterministic.
        # You're welcome to modify them to try to get a better solution.
        torch.manual_seed(seed)
        random.seed(seed)

        self.device = torch.device("cpu") # Can change this if you have a GPU, but the autograder will use CPU
        self.model = model.to(self.device) #model.cuda() # Also uncomment these lines for GPU
        self.optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate)
        self.criterion = nn.BCELoss()

    def evaluate(self, loader, threshold=0.5, outpath=None):
        self.model.eval()
        preds, targets, paths = [], [], []
        with torch.no_grad():
            for x, y, ps in loader:
                x = x.to(self.device) #x.cuda()
                y = y.to(self.device) #y.cuda()
                outputs = self.model(x)
                preds.append(outputs.cpu())
                targets.append(y.cpu())
                paths += list(ps)
        
        preds = torch.cat(preds)
        targets = torch.cat(targets)
        preds_bin = (preds > threshold).float()
        
        predictions = {}
        for i in range(preds_bin.shape[0]):
            predictions[paths[i]] = [TAGS[j] for j in range(len(preds_bin[i])) if preds_bin[i][j]]
        
        mAP = None
        if outpath: # Save predictions
            with open(outpath, "w") as z:
                z.write(str(predictions) + '\n')
        else: # Only compute accuracy if we're *not* saving predictions, since we can't compute test accuracy
            mAP = average_precision_score(targets, preds, average='macro')
        return predictions, mAP

    def train(self, train_loader, val_loader, num_epochs):
        for epoch in range(num_epochs):
            self.model.train()
            running_loss = 0.0
            for x, y, path in tqdm(train_loader, desc=f"Epoch {epoch+1}"):
                x = x.to(self.device) #x.cuda()
                y = y.to(self.device) #y.cuda()
                self.optimizer.zero_grad()
                outputs = self.model(x)
                loss = self.criterion(outputs, y)
                loss.backward()
                self.optimizer.step()
                running_loss += loss.item()
            val_predictions, mAP = self.evaluate(val_loader)
            print(f"[Epoch {epoch+1}] Loss: {running_loss/len(train_loader):.4f} | Val mAP: {mAP:.4f}")

# %% [markdown]
# ## Run everything...

# %%
def run1():
    model = model1()
    model.train(dataroot1 + "/train.json")
    train_preds = model.predict(dataroot1 + "/train.json")
    test_preds = model.predict(dataroot1 + "/test.json", "predictions1.json")
    
    train_labels = eval(open(dataroot1 + "/train.json").read())
    acc1 = accuracy1(train_labels, train_preds)
    print("Task 1 training accuracy = " + str(acc1))

# %%
def run2():
    model = model2()
    model.train(dataroot2 + "/train.json")
    train_preds = model.predict(dataroot2 + "/train.json")
    test_preds = model.predict(dataroot2 + "/test.json", "predictions2.json")
    
    train_labels = eval(open(dataroot2 + "/train.json").read())
    acc2 = accuracy2(train_labels, train_preds)
    print("Task 2 training accuracy = " + str(acc2))

# %%
def run3():
    loaders = Loaders(dataroot3 + "/train.json", dataroot3 + "/test.json")
    model = CNNClassifier()
    pipeline = Pipeline(model, 3e-4)
    
    pipeline.train(loaders.loaderTrain, loaders.loaderValid, 15)
    train_preds, train_mAP = pipeline.evaluate(loaders.loaderTrain, 0.5)
    valid_preds, valid_mAP = pipeline.evaluate(loaders.loaderValid, 0.5)
    test_preds, _ = pipeline.evaluate(loaders.loaderTest, 0.5, "predictions3.json")
    
    all_train = eval(open(dataroot3 + "/train.json").read())
    for k in valid_preds:
        # We split our training set into train+valid
        # so need to remove validation instances from the training set for evaluation
        all_train.pop(k)
    acc3 = accuracy3(all_train, train_preds)
    print("Task 3 training mAP = " + str(acc3))

# %%
run1()

# %%
run2()

# %%
run3()

# %%



