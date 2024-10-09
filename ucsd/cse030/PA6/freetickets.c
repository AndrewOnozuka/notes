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

#ifndef MYFREETICKETS
TODO(USING THE SOLUTION freetickets.c NOT MY CODE)
#else

/*
 * freetickets
 *      tears down the database freeing all allocated memory
 *      count the number of tickets freed in the database and
 *      print it out before returning
 *
 *      unsigned long cnt = 0UL
 *      printf("Total tickets freed: %lu\n", cnt);
 */
void
freetickets(void)
{
    int empty = 1; // set to zero when there is at least one vehicle in the database
    unsigned long cnt = 0UL; // number of tickets freed
    /*
     * walk down each chain
     */
    for (uint32_t i = 0; i < tabsz; i++) { // loop through htable
	    if (*(htable + i) != NULL) {
		    struct vehicle *vptr = *(htable + i); // vehicle pointer
	   	    struct vehicle *vtemp = *(htable + i); // vehicle temp
	   	    while (vptr != NULL) { // loop through
			    /*
		    	     * remove all the tickets for all vehicles
		    	     * on this chain
		   	     */ 
			    empty = 0; // if vptr not NULL, set to 0
		   	    struct ticket *tptr = vptr->head; // ticket pointer
		   	    struct ticket *ttemp = vptr->head; // ticket temp
		   	    while (tptr != NULL) { // loop through
			    	    tptr = tptr->next; // next
			    	    free(ttemp); // free temp
			    	    ttemp = NULL; // set to NULL
			  	    ttemp = tptr; // set tptr back to ttemp
				    cnt++; // increments tickets freed
			    }
			    /* 
		    	     * free the vehicle
		    	     */ 
			    vptr = vptr->next; // next
		    	    free(vtemp->plate); // free plate
		  	    free(vtemp->state); // free state
			    free(vtemp); // free temp
			    vtemp = NULL; // set to NULL
			    vtemp = vptr; // set vptr back to vtemp
		    }
		    /*
	     	     * chain is removed, update the htable entry
	 	     */
		    *(htable + i) = NULL; // updates htable
	    }
    }

    if (empty)
        printf("Empty Database\n");
    printf("Total tickets freed: %lu\n", cnt);
    return;
} // DONE
#endif
