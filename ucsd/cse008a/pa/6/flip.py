from CSE8AImage import *

def check_validity(image, col_index, row_index, region_height, region_width): 
    if ((col_index >= 0) and (row_index >= 0) and (region_height >= 0) and (region_width >= 0)) \
    and ((width(image) >= (col_index + region_width)) and (height(image) >= (row_index + region_height)) \
    and (width(image) > col_index) and (height(image) > row_index)):
        return True # if the given inputs are valid, flip vertical will be executed
    else: 
        return False # otherwise, the function will return False

def flip_vertical(image, col_index, row_index, region_height, region_width):
    # Check validity of input parameters
    if not check_validity(image, col_index, row_index, region_height, region_width):
        return False
    h = height(image) # or len(image)
    w = width(image) # or len(image[0])
    total = h * w #(used for testing)
    #print("height = ", h) #(used for testing)
    #print("width = ", w) #(used for testing)
    #print("total pixels = ", total) #(used for testing)
    for row in range(region_height//2):
        for col in range(region_width):
            temp = image[row_index + row][col_index + col]
            image[row_index + row][col_index + col] = image[region_height+row_index-row-1][col_index+col]
            image[region_height+row_index-row-1][col_index+col] = temp
            # horizontal flip below for reference
            # image[row_index + row][col_index + col] = image[row_index + row][region_width-col_index-col-1]
            # image[row_index + row][region_width-col_index-col-1] = temp
    return True

# submission inputs would not run properly with the below lines active
# image = load_img("input.png")
# save_img(image,"flipped.png")

# testing
# flip_vertical(image,100,60,120,120)
# image = create_img(255,255,(200,200,200))
# check_validity(image, len(image[0]), 0,0,0)
