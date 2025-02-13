def calculate_rating(raw_percentage_score): #converts the given % to rating
    raw_percentage_score = float(raw_percentage_score.strip("%")) #used to subtract % and convert str into float
    if 90 <= raw_percentage_score <= 100:
        return "Excellent"
    if 80 <= raw_percentage_score < 90:
        return "Good"
    if 70 <= raw_percentage_score <= 80:
        return "Fair"
    if 50 <= raw_percentage_score <= 70:
        return "Bad"
    else:
        return "Fail"
#testcases -> added % at the end and made into string
#print(calculate_rating("100.00%"))
#print(calculate_rating("84.62%"))
#print(calculate_rating("90.14%"))
#print(calculate_rating("89.99%"))
#print(calculate_rating("49.99%"))
#print(calculate_rating("100.01%"))

def calculate_all_ratings(score_list, candidate_info):
    for row in range(len(score_list)):
        if score_list[row][0] == "Coding Challenge":
            if candidate_info[1] == True: #special rule
                score_list[row] = (score_list[row][0],"Excellent")
            else:
                score_list[row] = (score_list[row][0],calculate_rating(score_list[row][1]))
        else:
            score_list[row] = (score_list[row][0],calculate_rating(score_list[row][1]))
    return score_list #makes a new list of tuples
#testcase
#calculate_all_ratings(score_list, candidate_info)

def determine_hiring_decision(score_list, candidate_info):
    total_hiring_score = 0
    for row in range(len(score_list)): #adds up ratings into one score
        if score_list[row][1] == "Excellent":
            total_hiring_score += 5
        if score_list[row][1] == "Good":
            total_hiring_score += 4
        if score_list[row][1] == "Fair":
            total_hiring_score += 3
        if score_list[row][1] == "Bad":
            total_hiring_score += 1
        if score_list[row][1] == "Fail":
            total_hiring_score += 0
    if total_hiring_score >= 21: #uses the score to evaluate hire status
        candidate_info[2] = "Strong Hire"
    if 20 >= total_hiring_score >= 16:
        candidate_info[2] = "Normal Hire"
    if total_hiring_score <= 15:
        candidate_info[2] = "No Hire"
    if candidate_info[0] == "Full-time": #special rule
        for row in range(len(score_list)):
            if score_list[row][1] == "Fail": #candidates do not get hired if they failed any area
                candidate_info[2] = "No Hire"
#testcase
#determine_hiring_decision(score_list, candidate_info)

def determine_salary(candidate_info): #determines salary based on hire status
    if candidate_info[2] == "No Hire":
        return None
    if candidate_info[0] == "Intern":
        candidate_info[3] = 5000
    if candidate_info[0] == "Full-time":
        if candidate_info[2] == "Strong Hire":
            candidate_info[3] = 10000
        if candidate_info[2] == "Normal Hire":
            candidate_info[3] = 9000

def update_candidate_info(score_list, candidate_info): #calls all necessary functions
    calculate_all_ratings(score_list, candidate_info)
    determine_hiring_decision(score_list, candidate_info)
    if candidate_info[2] == "Strong Hire" or candidate_info[2] == "Normal Hire":
        determine_salary(candidate_info)