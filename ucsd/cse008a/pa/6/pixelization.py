from CSE8AImage import *

def pixelization(image, square_size, column_percentage):
    h = height(image) # or len(image)
    w = width(image) # or len(image[0])
    for row in range(h):
        for col in range(int(w*column_percentage/100)):
            temp = (0,0,0)
            r_avg = 0
            g_avg = 0
            b_avg = 0
            r_avg += image[row][w-col-1][0]
            g_avg += image[row][w-col-1][1]
            b_avg += image[row][w-col-1][2]

            (r,g,b) = image[row][col]
            #new_rgb(image[row][col])
            image[row][w-col-1] = (image[row][w-col-1][0],image[row][w-col-1][1],image[row][w-col-1][2])

#def new_rgb(pixel_color):
    #(r,g,b) = (color[0],color[1],color[2])
    #(color[0],color[1],color[2]) = 

#image = load_img("input.png")
#pixelization(image,10,60)
#save_img(image, 'pixelized.png')
