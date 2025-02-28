def is_valid(num):
    if num < 0:
        #print("False")
        return False
#you cannot take a negative number of steps, therefore anything less than 0 returns false.
    m_list = []
    first = 0
    second = 1
    third = 1
    for idx in range(1000):
        m_list.append(third)
        first = second
        second = third
        third = first + second
#makes a list of numbers in the fibonacci sequence.
    if num in m_list:
        #print("True")
        return True
#checks if argument is valid.

#is_valid(-1)
#is_valid(1)
#is_valid(144)