#include <iostream>
#include <string>
using namespace std;

/**
 * Ask user to enter their name, and then greet them
 */
/* YOUR CODE HERE */
main() {
    string name;
    std::cout << "Enter name:";
    getline(cin, name);
    std::cout << "Hello, " + name + "!" << endl;
    std::cout << "Welcome to CSE 100!" << endl;
}
