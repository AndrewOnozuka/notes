/*
 * State handler routines for PA
 * one function for each state of the DFA
 *
 * Author: Andrew Onozuka 
 */

#include <stdio.h>
#include "states.h"

/*
 * global flag set by state handlers if they see an error
 * 0 is no error
 * 1 is at least 1 error
 */
int saw_error = 0;

/*  replace this comment with your state handling functions below */
enum typestate startSTATE(int c)
{
	if (c == '\"') {
		putchar(c);
		return DBLQUOTE;
	}
	else if (c == '\'') {
		putchar(c);
		return QUOTE;
	}
	else if (c == '/') {
		return SLASH;
	}
	putchar(c);
	return START;
}
enum typestate quoteSTATE(int c, int startline)
{
	if (c == '\\') {
		putchar(c);
		return IGNOREQUOTE;
	}
	else if (c == '\'') {
		putchar(c);
		return START;
	}
	else if (c == '\n') {
		putchar(c);
		saw_error = 1;
		fprintf(stderr, "Error: line %i: unterminated quote(')\n", startline);
		return START;
	}
	putchar(c);
	return QUOTE;
}
enum typestate slashSTATE(int c)
{
	if (c == '*') {
		putchar(' ');
		return SLASHSTAR;
	}
	else if (c == '/') {
		putchar(' ');
		return DBLSLASH;
	}
	putchar('/');
	putchar(c);
	return START;
}
enum typestate dblslashSTATE(int c) {
	if (c == '\n') {
		putchar(c);
		return START;
	}
	return DBLSLASH;
}
enum typestate starslashSTATE(int c) {
	if (c == '\n') {
		putchar('\n');
		return SLASHSTAR;
	}
	else if (c == '/') {
		return START;
	}
	else if (c == '*') {
		return STARSLASH;
	}
	return SLASHSTAR;
}
enum typestate slashstarSTATE(int c) {
	if (c == '\n') {
		putchar(c);
		return SLASHSTAR;
	}
	else if (c == '*') {
		return STARSLASH;
	}
	return SLASHSTAR;
}
enum typestate dblquoteSTATE(int c, int startline) {
	if (c == '\"') {
		putchar(c);
		return START;
	}
	else if (c == '\\') {
		putchar(c);
		return IGNOREDBLQUOTE;
	}
	else if (c == '\n') {
		putchar(c);
		saw_error = 1;
		fprintf(stderr, "Error: line %i: unterminated quote(\")\n", startline);
		return START;
	}
	putchar(c);
	return DBLQUOTE;
}
enum typestate ignorequoteSTATE(int c) {
	putchar(c);
	return QUOTE;
}
enum typestate ignoredblquoteSTATE(int c) {
	putchar(c);
	return DBLQUOTE;
}
