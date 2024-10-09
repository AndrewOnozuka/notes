#ifndef STATES_H
#define STATES_H
/* 
 * Header file for PA3
 * Author: Andrew Onozuka 
 */

/*
 * Complete the type definition for DFA states, you do not need an end state
 */
enum typestate {START, QUOTE, SLASH, DBLSLASH, STARSLASH, DBLQUOTE, IGNOREQUOTE, IGNOREDBLQUOTE, SLASHSTAR};
/*
 *  function prototypes for each state handler of the DFA
 *  Hint you will have to pass the single quote and double quote states
 *       the input plus the starting line number to print the error message
 *       for unterminated literals that see a newline before the ending quote
 *       all other states just need to be passed the input
 */
enum typestate startSTATE(int);
enum typestate quoteSTATE(int, int);
enum typestate slashSTATE(int);
enum typestate dblslashSTATE(int);
enum typestate starslashSTATE(int);
enum typestate slashstarSTATE(int);
enum typestate dblquoteSTATE(int, int);
enum typestate ignorequoteSTATE(int);
enum typestate ignoredblquoteSTATE(int);
/*
 * global variable set if a state handler sees an error
 * 0 is no error
 * 1 is at least one error
 */
extern int saw_error;

#endif
