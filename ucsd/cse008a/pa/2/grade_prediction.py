def grade_prediction(average_minutes_late,total_grade):
    if total_grade < 0:
        return "no prediction"
    if average_minutes_late >= 5:
        return "F"
    if average_minutes_late < 5 and 0 <= total_grade < 60:
        return "F"
    if average_minutes_late <= 0 and 60 <= total_grade < 70:
        return "D"
    if average_minutes_late <= 0 and 70 <= total_grade <80:
        return "C"
    if average_minutes_late <= 0 and 80 <= total_grade <90:
        return "B"
    if average_minutes_late <= 0 and 90 <= total_grade <96:
        return "A"
    if average_minutes_late <= 0 and 96 <= total_grade:
        return "A+"
    return