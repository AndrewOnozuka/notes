from CSE8AImage import *
import math

def change_background(image, new_background, replace_color):
    image = load_img("PA6_profile.png")
    #print("PA6_profile loaded") (used for testing)
    new_background = load_img("PA6_background.png")
    #print("PA6_background loaded") (used for testing)
    h = height(image) # or len(image)
    w = width(image) # or len(image[0])
    #total = h * w (used for testing)
    #print("height = ", h) (used for testing)
    #print("width = ", w) (used for testing)
    #print("total pixels = ", total) (used for testing)
    for row in range(h):
        for col in range(w):
            (r,g,b) = image[row][col]
            if color_distance(image[row][col],replace_color) < 100: # checks to see if pixel should be replaced or not
                image[row][col] = new_background[row][col] #replaces pixel of image with background
    save_img(image,"creative.png") # saves new image

def color_distance(color1, color2):
    distance_sq = (((color1[0] - color2[0]) ** 2) + ((color1[1] - color2[1]) ** 2) + ((color1[2] - color2[2]) ** 2))
    distance = distance_sq ** 0.5 # the sqrt function was giving me issues so I just did the regular math
    return distance # defining threshold 

# call change_background function
change_background("PA6_profile.png","PA6_background.png",(0,150,100)) #this was the best value I could get before I started disappearing
# call color_distance function (for testing)
# color_distance((100,100,100),(0,150,100))

# original instructions
# load the source image and new background image
# change the background
# save the image to a new file

