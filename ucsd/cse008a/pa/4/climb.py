def valid_range(a,b):
    if 0 <= a <= b <= 44:
        return True
#if a and b are both in between the valid range, the function returns true.
    else:
        return False
#test cases
#valid_range(0,3)
#valid_range(15,43)
#valid_range(0,45)
#valid_range(0,47)
#valid_range(-5,5)
#valid_range(25, 10)

def climb(a,b):
    m_list = []
#i used m as that was the variable given in the problem to represent the number of different ways.
    first = 0
    second = 1
    third = 1
    for idx in range(45):
        m_list.append(third)
        first = second
        second = third
        third = first + second
#fibonacci sequence as covered in lecture.
    return m_list[a:b+1]
#this returns the different number of steps one can take to reach the top.
