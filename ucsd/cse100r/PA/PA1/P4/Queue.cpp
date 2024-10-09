#include "Queue.h"

/**
 * Implement Queue constructor
 */
Queue::Queue() { // initialize empty linked list
    head = tail = nullptr;
    numElements = 0;
}

/**
 * Implement the Queue destructor
 */
Queue::~Queue() {
    clear(); 
}

/**
 * Implement size() correctly
 */
unsigned int Queue::size() {
    return numElements;
}

/**
 * Implement clear() correctly without memory leaks
 */
void Queue::clear() {
    while (numElements > 0) {       // call until clear
        pop();
    }
}

/**
 * Implement push() correctly
 */
void Queue::push(string s) {
    Node* newElem = new Node(s);    // creates new node
    if (numElements == 0) {         // if list empty
        head = newElem;             // becomes head node
        tail = head;                // bc only 1 element
    } else {                        // if list not empty
        tail->next = newElem;       // node after tail
        tail = tail->next;          // updates tail
    }
    numElements++;                  // increments numElements
}

/**
 * Implement pop() correctly without memory leaks
 */
string Queue::pop() {
    string store = head->data;      // stores the data of head
    Node* front = head;             // temp front for ptr
    head = head->next;              // updates head
    delete front;                   // delete for memory
    numElements--;                  // decrements numElements
    return store;                   // return data as string
}