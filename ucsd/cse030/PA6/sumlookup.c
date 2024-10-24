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

#ifndef MYSUMLOOKUP
TODO(USING THE SOLUTION sumlookup.c NOT MY CODE)
#else

/*
 * summlookup
 *     find the vehicle with a specified summons number
 * Input:
 *      summ: string of summons number
 * returns:   a pointer to the vehicle
 */
struct vehicle *
sumlookup(char *summ)
{
    unsigned long long summid;

    if (strtosumid(summ, &summid) != 0) {
        fprintf(stderr, "%s:sumlookup bad summons number:%s\n", argv0, summ);
        return NULL;
    }
  
    for (uint32_t i = 0; i < tabsz; i++) { // loop through htable
	    struct vehicle *ptr = *(htable + i);
	    while (ptr != NULL) { // loop through vehicles
		    struct ticket *thead = ptr->head;
		    while (thead != NULL) { // loop through tickets
			    if (thead->summons == summid) { // if summid matches
				    return ptr; // returns the pointer
			    }
			    thead = thead->next; // next
		    }
		    ptr = ptr->next; //next
	    }
    }
    return NULL;
} // DONE
#endif
