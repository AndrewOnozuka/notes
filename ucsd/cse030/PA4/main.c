/*
 * Programming assignment 4 
 * Written by: Andrew Onozuka 
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include "token.h"

/*
 * main
 */ 
int 
main(int argc, char **argv)
{
    char *ptable[MAXCOL];       /* array of pointers to column starts */
    char buf[BUFSZ];            /* input buffer */       
    unsigned long linecnt = 0;  /* input line number (record number) counter */
    unsigned long dropcnt = 0;  /* number of input lines dropped counter */
    int cols = 0;               /* number of columns in the input */
    char *ptr;                  /* pointer for use by strtol() */

    /*
     * parse the argv options
     * check argc value first
     * then check for -c# and -c #
     */
    if (argc == 2 && strncmp(*(argv + 1), "-c", 2) == 0) {
	   cols = (int) strtol(((*(argv + 1) + 2)), &ptr, 10);
    } else if (argc == 3 && strncmp(*(argv + 1), "-c", 2) == 0) {
	   cols = (int) strtol((*(argv + 2)), &ptr, 10);
    } else {
	    fprintf(stderr, "Usage: %s -c #\n", *argv);
	    return EXIT_FAILURE;
    }
    /*
     * make sure to set errno to 0 before calling strtol
     * errno is a global variable declared in errno.h
     */
    errno = 0;
    /*
     * using strtol() below
     * Get the number of data fields (columns) from argv
     * handle -c# AND -c # variants
     * check number of columns is from MINCOL to MAXCOL
     */
    if ((*ptr != '\0') || errno != 0) {
	fprintf(stderr, "%s: improper column value\n", *argv);
	return EXIT_FAILURE;
    }
    if (cols < MINCOL || cols > MAXCOL) {
	fprintf(stderr, "%s: column value %d, out of range, min%d max is %d\n",
			 *argv, cols, MINCOL, MAXCOL);
	return EXIT_FAILURE;
    }
    /*
     * read the input one line at a time, break into tokens and write out the
     * selected columns in a "debugging" format
     */
    while (fgets(buf, BUFSZ, stdin) != NULL) {
        linecnt++;             /* count the records */

        /*
         * break the input into columns (data fields)
         * use INDELIM as the delimiter (see token.h)
         */
        if (token(buf, INDELIM, cols, ptable, linecnt, *argv) != 0)
            dropcnt++;          /* count the dropped rows */
        else {
            /*
             * token returned ok, print out columns the recode one at a time
             */
            for (int i = 0; i < cols; i++)
                printf("Line[%lu],Column[%d]:%s\n",
			       	linecnt, i + 1, *(ptable + i));
       }
    }

    /*
     * write summary
     */
    fprintf(stderr, "%s: %lu records input, %lu dropped\n", *argv,
            linecnt, dropcnt);
    if (dropcnt > 0)
        return EXIT_FAILURE;
    return EXIT_SUCCESS;
}
