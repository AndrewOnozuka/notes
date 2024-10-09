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

#ifndef MYDELTICKET
TODO(USING THE SOLUTION delticket.c NOT MY CODE)
#else

/*
 * delticket
 *      remove ticket (summons) from the database
 *      look up the summons for a vehicle and pay it (remove the ticket) 
 *
 *      Find the vehicle by the state and plate. if not found return -1
 *
 *      convert the summ string to a long long int using
 *      using strtosumid. This long long int is how the summons number
 *      is stored in the database in struct ticket.
 *      After being converted you can use it find a summons id match
 *      in the linked list of tickets attached to the strcut vehicle.      
 *
 *      After you have the vehicle, find the summons on the ticket chain
 *      that matches the converted summons string (summid in the example)
 *      example:
 *        unsigned long long summid
 *        if (strtosumid(summ, &summid) != 0)
 *           return -1;
 *        now search the ticket chain to find that summons
 *        struct ticket *tptr;
 *        if (tptr->summons == summid)
 *          found it
 *           
 *      If found, delete it from the ticket chain. If the vehicle
 *      no longer has any tickets, then remove the vehicle from the hash chain
 *      Otherwise you must update the tot_fine and cnt_tickets fields in
 *      the struct vehicle so the are accurate after removing the summons
 *      Make sure to delete all space that malloc()'d 
 *
 * Inputs:
 *  plate   plate id string to be found
 *  state   state id string to be found
 *  summ    summon id string to be found
 *
 * returns 0 if ok -1 if not found or error
 */

int
delticket(char *plate, char *state, char *summ)
{
    unsigned long long summid;

    /*
     * convert the summons string to a number
     */
    if (strtosumid(summ, &summid) != 0)
        return -1;

    /*
     * first find the vehicle
     */
    uint32_t hashidx = hash(plate) % tabsz; // index for hashing
    struct vehicle *vcurr = htable[hashidx]; // current vehicle
    struct vehicle *vprev = vcurr; // previous vehicle
    /* 
     * did we find the vehicle?
     */
    while (vcurr != NULL) { // check if plate AND state match
	    if (strcmp(vcurr->plate, plate) == 0 &&
		strcmp(vcurr->state, state) == 0) {
		    break;
	    } // above is the same loop found in vehlookup.c
	    vprev = vcurr;
	    vcurr = vcurr->next;
    }
    if (vcurr == NULL) {
	    return -1;
    }
    /*
     * find the ticket
     */
    struct ticket *tcurr = vcurr->head;
    struct ticket *tprev = tcurr;
    // loop through tickets
    while (tcurr != NULL) {
	    if (tcurr->summons == summid) {
		    break;
	    }
	    tprev = tcurr;
	    tcurr = tcurr->next;
    }
    /*
     * if ticket not found we are done
     */
    if (tcurr == NULL) {
	    return -1;
    }
    /*
     * update the summary fields and remove the ticket
     */
    struct ticket *freetkt = tcurr; // for freeing tickets
    // loop through tickets to free
    if (freetkt == NULL) {
	    return -1;
    } else if (freetkt == vcurr->head) {
	    vcurr->head = freetkt->next;
    } else {
	    tprev->next = freetkt->next;
    } // updates fields
    vcurr->cnt_ticket -= 1; // decrement ticket count
    vcurr->tot_fine -= fineTab[freetkt->code].fine; // pay fine
    free(freetkt); // free ticket
    freetkt = NULL; // set freed ticket to NULL
    /*
     * was this the last ticket for the vehicle?
     * if so, remove the vehicle from the hash chain
     */
    if (vcurr->cnt_ticket == 0) { // if last ticket
	    struct vehicle *freeveh = vcurr;
	    // loop through vehicles
	    if (vcurr == htable[hashidx]) {
		    htable[hashidx] = vcurr->next;
	    } else {
		    vprev->next = vcurr->next;
	    }
	    free(freeveh->plate); // free plate
	    free(freeveh->state); // free state
	    free(freeveh); // free vehicle
    }
    return 0;
} // DONE
#endif
