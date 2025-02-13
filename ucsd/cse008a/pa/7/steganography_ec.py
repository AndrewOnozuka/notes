from CSE8AImage import *

def get_least_significant_n(num,n_bits):
    if n_bits == 1:
        return num & 0b00000001
    if n_bits == 2:
        return num & 0b00000011
    if n_bits == 3:
        return num & 0b00000111
    if n_bits == 4:
        return num & 0b00001111
    if n_bits == 5:
        return num & 0b00011111
    if n_bits == 6:
        return num & 0b00111111
    if n_bits == 7:
        return num & 0b01111111
# test cases
# print(get_least_significant_n(253,3))
# print(get_least_significant_n(255,7))

def get_most_significant_n(num,n_bits):
    return (num >> (8-n_bits))
# test cases
# print(get_most_significant_n(255,7))
# print(get_most_significant_n(255,6))

def embed_digits_n(context_val,message_val,n_bits):
    new_context_val = ((context_val >> n_bits) << n_bits)
    if message_val > (n_bits+1): # if message_val is greater than n_bits+1, we do an or with the most significant 2 digits
        return new_context_val | get_most_significant_n(message_val,n_bits)
    if message_val <= (n_bits+1): # if message_val is less than or equal to n_bits+1, we can simply or them both.
        return new_context_val | message_val

def hide_secret_message_nbits(context_img, message_img, n_bits):
    img = context_img
    img_with_message = []
    for row_idx in range(len(img)):
        row = []
        for col_idx in range(len(img[row_idx])):
            row.append(img[row_idx][col_idx])
        img_with_message.append(row)
    # lines 36-42 makes a new image so we do not change the original image
    for row in range(len(img_with_message)): # goes through the rows
        for col in range(len(img_with_message[row])): # goes through the columns
            (r,g,b) = img_with_message[row][col] # formatting below helped by TA Kewen Zhao
            img_with_message[row][col] = \
            (embed_digits_n(img_with_message[row][col][0],get_most_significant_n(message_img[row][col][0],n_bits),n_bits)\
            ,embed_digits_n(img_with_message[row][col][1],get_most_significant_n(message_img[row][col][1],n_bits),n_bits)\
            ,embed_digits_n(img_with_message[row][col][2],get_most_significant_n(message_img[row][col][2],n_bits),n_bits))
    return img_with_message #returns an img with the hidden message values embedded

def recover_secret_message_nbits(img_with_message, n_bits):
    img = img_with_message
    secret_message = []
    for row_idx in range(len(img)):
        row = []
        for col_idx in range(len(img[row_idx])):
            row.append(img[row_idx][col_idx])
        secret_message.append(row)
    # lines 54-60 makes a new image so we do not change the original image
    for row in range(len(secret_message)): # goes through the rows
        for col in range(len(secret_message[row])): # goes through the columns
            (r,g,b) = secret_message[row][col] # formatting below helped by TA Kewen Zhao
            secret_message[row][col] = \
            ((get_least_significant_n(secret_message[row][col][0],n_bits) << (8-n_bits))\
            ,(get_least_significant_n(secret_message[row][col][1],n_bits) << (8-n_bits))\
            ,(get_least_significant_n(secret_message[row][col][2],n_bits) << (8-n_bits)))
    return secret_message #returns hidden image
