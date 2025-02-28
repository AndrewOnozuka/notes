def count_exact_match(text, query):
    count = 0
    if len(text) >= len(query):
        for a in range(len(query)):
            if query[a] == text[a]:
                count += 1
    if len(text) < len(query):
        for b in range(len(text)):
            if text[b] == query[b]:
                count += 1
    return count