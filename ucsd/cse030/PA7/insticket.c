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

#ifndef MYINSTICKET
TODO(USING THE SOLUTION insticket.c NOT MY CODE)
#else

/*
 * insertticket
 *      add a ticket (summons) to the database
 *
 *      YOU CANNOT USE CALLOC(), only malloc() is allowed.
 *
 *      if the vehicle (plate state) is not in the database then
 *      a new struct vehicle entry must be made for it in the hash table.
 *      New vehicle entries are made at the the FRONT of the collison chain
 *
 *      If the vehicle is already in the hash chain, you only need to add 
 *      a new ticket to the ticket chain for this summons.
 *
 *      Tickets are added to the ticket chain linked to the struct vehicle
 *      at the END of the ticket chain.
 *
 *      Also you must update the tot_fine and cnt_tickets in the struct vehicle
 *      to account for the new summons(ticket) just added
 *  fineTab pointer fine table. maps code number to text description & fine cost 
 *          you use this table to get the amount of the fine when calculating
 *          the tot_fine field in struct vehicle.
 *          example:
 *              struct vehicle *vhpt;
 *              vhpt->tot_fine += fineTab[code].fine;
 *  summ    summons id string to be added
 *          this string needs to be converted to a number to be stored in the
 *          database. This saves space and is faster to compare than a string         
 *          Conversion function is supplied. use example:
 *               unsigned long long summid;
 *               if (strtosumid(summ, &summid, argv) != 0)
 *                  error handling
 *               new_ticket->summons = summid;
 *  plate   plate id string to be added
 *  state   state id string to be added
 *  date    date of summons string
 *          this string needs to be converted to a number to be stored in the
 *          database. This saves space and is faster to compare than a string
 *          The encoding into a number uses Linux time format.
 *          Conversion function is supplied. use example:
 *              time_t dateval;
 *              if (strtoDate(date, &dateval, argv) != 0)
 *                  error handling
 *              new_ticket->date = dateval;
 *  code    summons code integer value used an an index into the fines table
 *
 * returns 0 if ok -1 for all errors
 */

int
insertticket(char *summ, char *plate, char *state, char *date, int code)
{
    unsigned long long summid;
    time_t dateval;

    if (strtosumid(summ, &summid) != 0)
        return -1;

    if (strtoDate(date, &dateval) != 0)
        return -1;

    uint32_t hashidx = hash(plate) % tabsz; // index for hashing

    struct vehicle *vlookup = vehiclelookup(plate, state); // call vehlookup
    struct ticket *newtkt = malloc(sizeof(struct ticket)); // new ticket

    if (newtkt == NULL) { // if memory allocated is NULL
	    fprintf(stderr, "%s: unable to allocate ticket for summons %s\n",
		    argv0, summ); // error message
	    return -1;
    }
    // sets fields of new ticket
    newtkt->code = code; // sets code
    newtkt->date = dateval; // sets date value
    newtkt->summons = summid; // sets summons id
    newtkt->next = NULL; // sets next to NULL

    if (vlookup == NULL) { // if vehlookup returns NULL
	    vlookup = malloc(sizeof(struct vehicle)); // allocate memory
	    if (vlookup == NULL) { // if still NULL
		    fprintf(stderr, "%s: unable to allocate vehicle for summons %s\n",
			    argv0, summ); // error message
		    free(newtkt); // free new ticket
		    newtkt = NULL; // set to NULL
		    return -1;
	    }
	    // checking for duplicate states and plates
	    if ((vlookup->plate = strdup(plate)) == NULL) {
		    fprintf(stderr, "%s: unable to allocate plate for summons %s\n",
			    argv0, summ); // error message
		    free(newtkt); // free new ticket
		    free(vlookup); // free vehicle
		    return -1;
	    }
	    if ((vlookup->state = strdup(state)) == NULL) {
		    fprintf(stderr, "%s: unable to allocate state for summons %s\n",
			    argv0, summ); // error message
		    free(vlookup->plate); // free plate
		    free(newtkt); // free new ticket
		    free(vlookup); // free vehicle
		    return -1;
	    } // inserting vehicle
	    vlookup->cnt_ticket = 1; // sets ticket count
	    vlookup->tot_fine = fineTab[code].fine; // sets total fine
	    vlookup->head = newtkt;
	    vlookup->next = htable[hashidx];
	    htable[hashidx] = vlookup;
    } else { // vehicle found -> add ticket
	    struct ticket *tcurr = vlookup->head; // current ticket
   	    struct ticket *tprev = tcurr; // previous ticket
   	    while (tcurr != NULL) { // loop through ticket list to end
		    if (tcurr->summons == summid) { // check for duplicate summid
			    fprintf(stderr, "%s: duplicate summons %llu\n",
				    argv0, summid); // error message
			    return -1;
		    }
		    tprev = tcurr;
	   	    tcurr = tcurr->next;
	    }
	    if (tprev == NULL) { // adding ticket to end of list
		    vlookup->head = newtkt;
	    } else {
		    tprev->next = newtkt;
	    }
	    vlookup->cnt_ticket += 1; // increments ticket cnt
	    vlookup->tot_fine += fineTab[code].fine; // adds to total fine
    }
    return 0;
} // DONE
#endif
