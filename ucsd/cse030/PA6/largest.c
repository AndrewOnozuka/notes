#include <limits.h>
#include <stdint.h>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include "parking.h"

#ifndef MYLARGEST
TODO(USING THE SOLUTION largest.c NOT MY CODE)
#else

/*
 * largest
 *     find the vehicle with the largest number of
 *     tickets and largest fine in database
 *     and print it
 */
void
largest(void)
{
    struct vehicle *count = NULL; // vehicle with largest number of tickets
    struct vehicle *fine = NULL;  // vehicle with largest total fine

    unsigned int maxfine = 0; // initialize largest fine
    unsigned int maxtix = 0; // initialize greatest num tickets

    for (uint32_t i = 0; i < tabsz; i++) { // loop through htable
	    if (*(htable + i) != NULL) {
		    struct vehicle *ptr = *(htable + i);
		    while (ptr != NULL) { // loop through
			    if (ptr->tot_fine >=  maxfine) {
				    maxfine = ptr->tot_fine; // set maxfine
				    fine = ptr; // set pointer to fine
			    }
			    if (ptr->cnt_ticket >=  maxtix) {
				    maxtix = ptr->cnt_ticket; // set maxtix
				    count = ptr; // set pointer to count
			    }
			    ptr = ptr->next; // next
		    }
	    }
    }

    if ((count == NULL) || (fine == NULL)) {
        printf("Empty database\n");
        return;
    }
    printf("Most tickets Plate: %s, State: %s tickets: %u, total fine: $%u\n",
            count->plate, count->state, count->cnt_ticket, count->tot_fine);
    printf("Largest fine Plate: %s, State: %s tickets: %u, total fine: $%u\n",
            fine->plate, fine->state, fine->cnt_ticket, fine->tot_fine);
    return;
} // DONE
#endif
