def sum_every_kth(start, end, k):
    if k >= start and start <= end:
        return None
    if k <= start and start <= end:
        return None
    total = 0
    for number in range(start, end, k):
        total += number
    return total