clc; clear; close all;

% Define parameters
T = pi; % Period
omega0 = 2 * pi / T; % Fundamental frequency
t = linspace(-3*T, 3*T, 1000); % Time vector over 3 periods
N_values = [0, 3, 10, 25, 50, 100]; % Different values of N to visualize

% Define the original periodic function
f_t = mod(t + T/2, T) < T/2; % Logical condition for f(x)
f_t = 3*pi * f_t; % Assign values based on given function

% Create figure for subplots
figure;
for i = 1:length(N_values)
    N = N_values(i);
    F0 = 3*pi/2; % DC Component
    f_approx = F0 * ones(size(t)); % Initialize with DC component

    % Compute Fourier series summation
    for n = 1:2:N % Only odd terms contribute
        Fn = -3 / (1j * n);
        f_approx = f_approx + Fn * exp(1j * n * omega0 * t) + conj(Fn) * exp(-1j * n * omega0 * t);
    end

    % Plot the Fourier approximation and the original function
    subplot(2,3,i); % 2 rows, 3 columns
    plot(t, real(f_approx), 'r', 'LineWidth', 2); hold on;
    stairs(t, f_t, 'b', 'LineWidth', 2);
    xlabel('t'); ylabel('f(t)');
    title(['N = ', num2str(N)]);
    legend('Fourier Approximation', 'Original Function');
    grid on;
end

% % Add an empty subplot for better alignment
% subplot(2,3,6); axis off; title('Fourier Series Convergence');
