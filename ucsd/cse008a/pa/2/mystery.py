def mystery(number):
    if number % 3 == 0 and number % 5 == 0 and number % 7 == 0:
        return "Paul is a great professor for CSE8A!"
    elif number % 3 == 0 and number % 5 == 0:
        return "CSE8A"
    elif number % 3 == 0 and number % 7 == 0:
        return "Professor"
    elif number % 5 == 0 and number % 7 == 0:
        return "Great"
    elif number % 3 == 0:
        return "CSE"
    elif number % 5 == 0:
        return "8A"
    elif number % 7 == 0:
        return "Paul"
    else:
        return ""