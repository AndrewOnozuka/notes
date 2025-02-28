% hw2_ece_45_w25_v1.m
%
% Script to produce the required outputs for the most amazing course in the
% ECE curriculum worldwide, ECE 45 
%
% Created by Student Y Y
%
% Change history
%   January 8, 2025 - initial version
%
% Pending items to finish
%   N/A
%

%% Problem 1

% Use this template below to do some plotting

figure(1000)
t=linspace(-3,4,500); % this creates a vector sampled at small intervals between -3 and 4

ct_fun = ct_function(t);
plot(t,ct_fun,'-',...
'LineWidth', 3);
xlabel('t');
ylabel('x(t)');
set(gca,'FontSize',14);
title('An awesome continuous-time signal')
 
figure(1001)
% Put figure for 1(a) here

figure(1002)
% Put figure for 1(b) here

figure(1003)
% Put figure for 1(c) here

figure(1004)
% Put figure for 1(d) here

figure(1005)
% Put figure for 1(e) here

figure(1006)
% Put figure for 1(f) here

figure(1007)
% Put figure for 1(g) here






%% Put my functions here
function my_ct_fun = ct_function(t)
% using a for loop, which is not efficient in MATLAB but OK for a quick
% calculation
    for c = 1:length(t)
        switch true % this is a hack to get switch to work for intervals, see help on switch
            case t(c)<-1
                my_ct_fun(c) = 0;
            case t(c)>=-1 && t(c)<0
                my_ct_fun(c) = 1;
            case t(c)>=0 && t(c)<1
                my_ct_fun(c) = 0;
            case (t(c)>=1)&&(t(c)<2)
                my_ct_fun(c) = -1;
            case (t(c)>=2)&&(t(c)<3)
                my_ct_fun(c) = t(c)-1;
           case t(c)>=3
                my_ct_fun(c) = 0;
        end
    end % for c
end % fucntion (edited) 

