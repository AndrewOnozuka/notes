def first_occurrence(nums,threshold):
    if len(nums) < 1:
        return -1
    for a in range(len(nums)):
        if nums[a] >= threshold:
            return a
        elif max(nums) < threshold:
            return -1