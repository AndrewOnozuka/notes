#include <limits.h>
#include <stdint.h>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include "parking.h"
#include "hashdb.h"

#ifndef MYVEHLOOKUP
TODO(USING THE SOLUTION vehlookup.c NOT MY CODE)
#else

/*
 * vehiclelookup
 *          look for vehicle in the database
 *          vehicle must match both plate and state strings
 * args
 *  plate   plate id string to be found
 *  state   state id string to be found
 *
 * returns  pointer to vehicle if found NULL otherwise
 */

struct vehicle *
vehiclelookup(char *plate, char *state)
{
    uint32_t hashidx = hash(plate) % tabsz; // index for hashing
    struct vehicle *head = htable[hashidx]; // linked list
    
    while (head != NULL) { // check if plate AND state match
	    if (strcmp(head->plate, plate) == 0 &&
		strcmp(head->state, state) == 0) {
		    return head; // return pointer
	    }
	    head = head->next; // next
    }
    return NULL; // if no vehicle found
} // DONE
#endif
