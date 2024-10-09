/*
 * CSE Programming assignment 3 
 * Author: Andrew Onozuka 
 *
 * What this program does:
 *      remove c comments from a source file and write to stdout
 *      comments are replaced with a single space
 *      handles single and double quoted sequences
 *      all newlines are preserved
 *
 * error handling:
 *      prints starting line for unterminated comments or quote sequences
 *      to stderr
 *
 * return either:
 *      EXIT_SUCCESS if no errors
 *      EXIT_FAILURE if errors
 *      
 */

#include <stdio.h>
#include <stdlib.h>
#include "states.h"
#define CNT 1   /* start count lines in input at CNT */

int
main(void)
{
    int c;              /* input char  */
    int linecnt = 1;  /* counts line in input */
    int startline = 1;/* starting line number for comment/quote */

    enum typestate state = START;  /* initial state of DFA */
    /*
     * process char at a time until EOF
     */
    while ((c = getchar())!= EOF)
    {
		switch (state)
		{
		case START:
			state = startSTATE(c);
			startline = linecnt;
			break;
		case QUOTE:
			state = quoteSTATE(c, startline);
			break;
		case SLASH:
			state = slashSTATE(c);
			startline = linecnt;
			break;
		case DBLQUOTE:
			state = dblquoteSTATE(c, startline);
			break;
		case DBLSLASH:
			state = dblslashSTATE(c);
			break;
		case SLASHSTAR:
			state = slashstarSTATE(c);
			break;
		case STARSLASH:
			state = starslashSTATE(c);
			break;
		case IGNOREQUOTE:
			state = ignorequoteSTATE(c);
			break;
		case IGNOREDBLQUOTE:
			state = ignoredblquoteSTATE(c);
			break;
		default:
			fprintf(stderr, "Error: DFA state not handled\n");
			return EXIT_FAILURE;
			break;
		}
		if (c == '\n') {
			linecnt++;
		}
	}
    if (state == QUOTE || state == IGNOREQUOTE) {
	fprintf(stderr, "Error: line %i: unterminated quote(')\n", startline);
	return EXIT_FAILURE;
    }
    if (state == DBLQUOTE || state == IGNOREDBLQUOTE) {
	fprintf(stderr, "Error: line %i: unterminated quote(\")\n", startline);
	return EXIT_FAILURE;
    }
    if (state == DBLSLASH || state == STARSLASH || state == SLASHSTAR) {
	fprintf(stderr, "Error: line %i: unterminated comment\n", startline);
	return EXIT_FAILURE;
    }
    if (saw_error != 0) {
	return EXIT_FAILURE;
    }
    if (state == SLASH) {
	 putchar('/');
   	 return EXIT_SUCCESS;
    }
    return EXIT_SUCCESS;
    /*
     * All done. No explicit end state used here.
     * Check for error conditions based on the state we ended in
     */
}
