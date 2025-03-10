"""
Logistic regression model
"""

import numpy as np
import math


class Logistic(object):
    def __init__(self, n_class: int, lr: float, epochs: int, weight_decay: float):
        """Initialize a new classifier.

        Parameters:
            lr: the learning rate
            epochs: the number of epochs to train for
        """
        self.w = None
        self.lr = lr
        self.epochs = epochs
        self.n_class = n_class
        self.threshold = 0.5  # To threshold the sigmoid
        self.weight_decay = weight_decay

    def sigmoid(self, z: np.ndarray) -> np.ndarray:
        """Sigmoid function.

        Parameters:
            z: the input

        Returns:
            the sigmoid of the input
        """
        # TODO: implement me
        return 1 / (1 + np.exp(-z))

    def train(self, X_train: np.ndarray, y_train: np.ndarray, weights: np.ndarray) -> np.ndarray:
        """Train the classifier.

        Use the logistic regression update rule as introduced in lecture.
        Train a logistic regression classifier for each class i to predict the probability that y=i

        Parameters:
            X_train: a numpy array of shape (N, D) containing training data;
                N examples with D dimensions
            y_train: a numpy array of shape (N,) containing training labels
        """

        N, D = X_train.shape
        self.w = weights

        # TODO: implement me
        y_one_hot = np.eye(self.n_class)[y_train]
#         print("y_one_hot shape:", y_one_hot.shape)
#         print(y_one_hot[0:5, :])
#         print("X_train shape:", X_train.shape)
#         print("self.w shape:", self.w.shape)

        for epoch in range(self.epochs):
            z = -np.dot(y_one_hot, np.dot(self.w, X_train.T))
            diag = np.diag(self.sigmoid(z))
            grad = self.weight_decay * self.w - (np.dot(diag * y_one_hot.T, X_train) / N)
            self.w -= self.lr * grad
            
            #             scores = self.sigmoid(z)[np.arrange(N), np.arrange(N)]
#             scores = self.sigmoid(np.dot(y_train_encoded, np.dot(X_train, self.w.transpose().transpose())))
#             grad = -(1/N) * np.dot(scores, np.dot(X_train, self.w.transpose())).transpose() + self.weight_decay * self.w
#             grad = -(1/N) * np.dot(X_train.T, scores).transpose() + self.weight_decay * self.w
#             self.w -= self.lr * grad

        return self.w

    def predict(self, X_test: np.ndarray) -> np.ndarray:
        """Use the trained weights to predict labels for test data points.

        Parameters:
            X_test: a numpy array of shape (N, D) containing testing data;
                N examples with D dimensions

        Returns:
            predicted labels for the data in X_test; a 1-dimensional array of
                length N, where each element is an integer giving the predicted
                class.
        """
        # TODO: implement me
        scores = np.dot(X_test, self.w.transpose())
        predictions = np.argmax(scores, axis=1)
        return predictions
