#include <stdio.h>
#include "wrtrow.h"

/*
 * wrtrow
 *
 * usage:   given an array of pointers to columns and an array of columns to
 *          ouput and a count of output columns
 *          output those columns specified in the output array (contents are
 *          index numbers of the input array)
 * args:
 *  ptable  points at array of pointers each element points at a data field
 *  coltab  points array of ints each element is an index in ptable in print order
 *  outcols number of elements in array coltab
 *  outdelim the output field delimiter to use
 */
void
wrtrow(char **ptable, int *coltab, int outcols, char outdelim)
{
	// stores pointer of coltab
	int *pcoltab = coltab;
	for (int i = 0; i < outcols; i++) {
		if (i == outcols - 1) {
			printf("%s\n", *(ptable + *pcoltab));
		} else {
			printf("%s%c", *(ptable + *pcoltab), outdelim);
		}
		pcoltab++;
	}
}
