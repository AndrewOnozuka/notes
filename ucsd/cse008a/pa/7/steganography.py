from CSE8AImage import *

def get_least_significant2(num):
    return num & 0b00000011 # leaves only last 2 digits
#test cases
#print(get_least_significant2(253))
#print(get_least_significant2(255))

def get_most_significant2(num):
    return (num >> 6) # gives first 2 digits
#test cases
#print(get_most_significant2(200))
#print(get_most_significant2(150))

def embed_digits2(context_val, message_val):
    #print("--------")
    #print(context_val)
    #print(bin(context_val) + " -> context_val before shift")
    new_context_val = ((context_val >> 2) << 2) # shifts right 2 and back to clear last 2 digits
    #print(bin(new_context_val) + " -> context_val after shift")
    #print(message_val)
    #print(bin(message_val))
    if message_val > 3: # if message_val is greater than 3, we do an or with the most significant 2 digits
        return new_context_val | get_most_significant2(message_val)
    if message_val <= 3: # if message_val is less than or equal to 3, we can simply or them both.
        # if((new_context_val | message_val) > 255):
        #     print(context_val, message_val)
        return new_context_val | message_val
    #print(bin(get_most_significant2(message_val)))
    #print(format(message_val >> 6, '#010b'))
    #print(get_most_significant2(message_val) | 0b00000011)
    #print("--------")
#test cases
#print(embed_digits2(0,3)) # exp 3
#print(embed_digits2(255,0)) # exp 252
#print(embed_digits2(254,1)) # exp 253
#print(embed_digits2(1,2)) # exp 2
#print(embed_digits2(252,3))# exp 255
#print(embed_digits2(3,0)) # exp 0
#print(embed_digits2(255,1)) # exp 253
#print(embed_digits2(255,2)) # exp 254
#print(embed_digits2(242,248)) # exp 243

def hide_secret_message_2bits(context_img, message_img):
    #img_with_message = context_img
    img = context_img
    img_with_message = []
    for row_idx in range(len(img)):
        row = []
        for col_idx in range(len(img[row_idx])):
            row.append(img[row_idx][col_idx])
        img_with_message.append(row)
    # lines 46-52 makes a new image so we do not change the original image
    for row in range(len(img_with_message)): # goes through the rows
        for col in range(len(img_with_message[row])): # goes through the columns
            (r,g,b) = img_with_message[row][col] # formatting below helped by TA Kewen Zhao
            img_with_message[row][col] = \
            (embed_digits2(img_with_message[row][col][0],get_most_significant2(message_img[row][col][0]))\
            ,embed_digits2(img_with_message[row][col][1],get_most_significant2(message_img[row][col][1]))\
            ,embed_digits2(img_with_message[row][col][2],get_most_significant2(message_img[row][col][2])))
    return img_with_message #returns an img with the hidden message values embedded

def recover_secret_message_2bits(img_with_message):
    #secret_message = img_with_message
    img = img_with_message
    secret_message = []
    for row_idx in range(len(img)):
        row = []
        for col_idx in range(len(img[row_idx])):
            row.append(img[row_idx][col_idx])
        secret_message.append(row)
    # lines 65-71 makes a new image so we do not change the original image
    for row in range(len(secret_message)): # goes through the rows
        for col in range(len(secret_message[row])): # goes through the columns
            (r,g,b) = secret_message[row][col] # formatting below helped by TA Kewen Zhao
            secret_message[row][col] = \
            ((get_least_significant2(secret_message[row][col][0]) << 6)\
            ,(get_least_significant2(secret_message[row][col][1]) << 6)\
            ,(get_least_significant2(secret_message[row][col][2]) << 6))
    return secret_message #returns hidden image

# below used for testing
# m_m = load_img("M_M.bmp")
# cookie_monster = load_img("CookieMonster.bmp")
# img_with_message = hide_secret_message_2bits(m_m,cookie_monster)
# save_img(img_with_message,"M_MWithMessage.bmp")
# load_img_with_message = load_img("M_MWithMessage.bmp")
# secret_message = recover_secret_message_2bits(load_img_with_message)
# save_img(secret_message,"recMessage.bmp")
