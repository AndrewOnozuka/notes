
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "token.h"

/*
 * token
 * 
 * usage:   input buffer in dsv format, builds table of pointers to each field
 * args:
 *  buf     DSV data record stored in an array of characters, '\0' terminated
 *  delim   the field delimiter             
 *  cnt     number of data fields that a proper data record has
 *  ptable  points at array of pointers each element points at a data field
 *  lineno  line number of this record for printing error messages
 *  argv0   name of program used when printing errors
 * returns:
 *          0 if no errors founds
 *         -1 otherwise
 */
int
token(char *buf, char delim, int cnt, char **ptable, unsigned long lineno,
            char *argv0)
{
	char **endptr = ptable + cnt;
	*ptable = buf;
	// loops through to check for the two types of fields
	// attempted to count the quotes at first but was failing
	// decided to increment buf twice instead
	while (ptable < endptr && *buf != '\0') {
		*ptable++ = buf;
		// checks for data fields starting with a quote
		if (*buf == '\"') {
			while (*buf != '\0') {
				buf++;
				// tried to make more efficient but didn't pass tests
				if (*buf == '\"' &&
				(*(buf + 1) == delim || *(buf + 1) == '\n')) {
					*(buf + 1) = '\0'; // sets null
					buf++;
					buf++; // skips twice
					break;
				} else if (*buf == '\"' && (*(buf + 1) == '\"')) {
					buf++;
					continue;
				} else if (*buf == '\"') {
			fprintf(stderr, "%s: drop line %lu, quoted field not terminated\n",
					argv0, lineno);
					return -1; // err
				} else if (*buf == '\n') {
			fprintf(stderr, "%s: drop line %lu, quoted field not terminated\n",
					argv0, lineno);
					return -1; // err
				}
			}
			continue;
		}
		// checks for data fields without a quote
		// same code as PA4 but changed order due to errors
		while (*buf != '\0') {
			if (*buf == delim || *buf == '\n') {
				*buf = '\0';
				buf++;
				break;
			} else if (*buf == '\"') {
			fprintf(stderr, "%s: drop line %lu, \" in unquoted field\n",
				argv0, lineno);
				return -1;
			}
			buf++;
		}
	}
	if (ptable < endptr && *buf == '\0') {
		fprintf(stderr, "%s: drop line %lu, too few columns\n", argv0, lineno);
		return -1;
	} else if (ptable == endptr && *buf != '\0') {
		fprintf(stderr, "%s: drop line %lu, too many columns\n", argv0, lineno);
		return -1;
	}
	return 0;
}
