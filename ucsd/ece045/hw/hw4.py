import numpy as np
import matplotlib.pyplot as plt
from scipy.signal import convolve
import os

# Create a directory for plots if it doesn't exist
save_dir = "plots"
os.makedirs(save_dir, exist_ok=True)

# Define the unit step function
def u(t):
    return np.where(t >= 0, 1, 0)

# Define the rect function
def rect(t):
    return np.where(abs(t) <= 0.5, 1, 0)

# Define time vector
t = np.linspace(-5, 5, 1000)

# Define functions
x1 = np.exp(-2 * t) * u(t)
h1 = np.exp(-2 * t) * u(t)

x2 = np.exp(-2 * t) * u(t)
h2 = np.exp(-2 * (t - 1)) * u(t - 1)

x3 = rect(t - 0.5)
h3 = rect(2 * t)

x5 = np.exp(-2 * t) * u(t)
h5 = rect(t)

x6 = np.exp(-2 * t) * u(t)
h6 = np.exp(-2 * t) * u(t) + rect(t)

x7 = 1 / (t**2 + 1) * u(-t)
h7 = u(t - 1)

# Compute convolutions
dt = t[1] - t[0]  # Time step
conv1 = convolve(x1, h1, mode='same') * dt
conv2 = convolve(x2, h2, mode='same') * dt
conv3 = convolve(x3, h3, mode='same') * dt
conv5 = convolve(x5, h5, mode='same') * dt
conv6 = convolve(x6, h6, mode='same') * dt
conv7 = convolve(x7, h7, mode='same') * dt

# Function to plot and save each convolution with the new filenames
def plot_and_save(t, conv, filename, title):
    filepath = os.path.join(save_dir, filename)
    plt.figure()
    plt.plot(t, conv, label=title)
    plt.xlabel("t")
    plt.ylabel("Amplitude")
    plt.legend()
    plt.grid()
    plt.savefig(filepath, dpi=300)
    plt.close()

# Generate plots with new filenames
plot_and_save(t, conv1, "ece45hw4p1_a.png", "Convolution (a)")
plot_and_save(t, conv2, "ece45hw4p1_b.png", "Convolution (b)")
plot_and_save(t, conv3, "ece45hw4p1_c.png", "Convolution (c)")
plot_and_save(t, conv5, "ece45hw4p1_e.png", "Convolution (e)")
plot_and_save(t, conv6, "ece45hw4p1_f.png", "Convolution (f)")
plot_and_save(t, conv7, "ece45hw4p1_g.png", "Convolution (g)")

print("Plots saved as PNGs in the 'plots' directory.")

# Define rect function explicitly for Python
def rect(t):
    return np.where(np.abs(t) <= 0.5, 1, 0)

# Define functions for Part 2
x2 = rect(t - 0.5)

# Compute convolutions for Part 2
conv2a = convolve(x2, x2, mode='same') * dt  # (a) x * x
conv2b = convolve(x2, x2[::-1], mode='same') * dt  # (b) x * x(-t)
conv2c = convolve(x2, rect(t - 1), mode='same') * dt  # (c) x * x(t - 1)
conv2d = rect(t - 2.5)  # (d) x * δ(t - 2) = x(t - 2)
conv2e = convolve(conv2a, rect(t + 1), mode='same') * dt  # (e) (x * x) * δ(t - 2) * x(t + 1)
conv2f = conv2a + convolve(x2, rect(t - 2), mode='same') * dt  # (f) (x + x(t - 2)) * x

# Save plots for Part 2
plot_and_save(t, conv2a, "ece45hw4p2_a.png", "Convolution 2(a)")
plot_and_save(t, conv2b, "ece45hw4p2_b.png", "Convolution 2(b) (Autocorrelation)")
plot_and_save(t, conv2c, "ece45hw4p2_c.png", "Convolution 2(c)")
plot_and_save(t, conv2d, "ece45hw4p2_d.png", "Convolution 2(d)")
plot_and_save(t, conv2e, "ece45hw4p2_e.png", "Convolution 2(e)")
plot_and_save(t, conv2f, "ece45hw4p2_f.png", "Convolution 2(f)")