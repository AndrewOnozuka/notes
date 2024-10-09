# Reading Quiz LE02

1. Imagine you have the following code snippet (C++):
```
int main() {
    vector<int> varA;
    vector<int> varB(42);
    vector<int> * varC = new vector<int>();
    vector<int> * varD = new vector<int>(42);
    vector<string> varE;
    vector<string> varF(42);
    vector<string> * varG = new vector<string>();
    vector<string> * varH = new vector<string>(42);
}
```
Which of the variables will need to be explicitly destroyed using the delete keyword? (Select all that apply)

- [ ] varA
- [ ] varB
- [x] varC
- [x] varD
- [ ] varE
- [ ] varF
- [x] varG
- [x] varH

2. Imagine you run the following code snippet (C++):
```
void zero(int x) {
    x = 0;
}

int main() {
    int myInt = 42;
    zero(myInt);
    cout << myInt;
}
```
What will be printed?

**42**

3. Imagine you run the following code snippet (C++):
```
void zero(int & x) {
    x = 0;
}

int main() {
    int myInt = 42;
    zero(myInt);
    cout << myInt;
}
```
What will be printed?

**0**

4. Imagine you run the following code snippet (C++):
```
void zero(int* x) {
    *x = 0;
}

int main() {
    int myInt = 42;
    zero(&myInt);
    cout << myInt;
}
```
What will be printed?

**0**

5. You are given the following code in a file called main.cpp:
```
#include <cstdlib>
#include <iostream>
using namespace std;

bool is_even(unsigned int n) {
    return !(n % 2);
}

int main(int argc, char* argv[]) {
    if(argc != 2) {
        cerr << "USAGE: " << argv[0] << " <positive_integer>" << endl;
        return 1;
    }
    if(is_even(atoi(argv[1]))) {
        cout << "even" << endl;
    } else {
        cout << "odd" << endl;
    }
    return 0;
}
```
You compile your program using the following command:
```
g++ -g -o main main.cpp
```
You then run gdb using the following command:
```
gdb main
```
What gdb command would you need to call in order to set a breakpoint on line 14?

**break main.cpp:14**

