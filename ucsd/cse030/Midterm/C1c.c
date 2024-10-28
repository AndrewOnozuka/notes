#include <stdio.h> // c
#include <stdlib.h>

enum typestate {START, COMMENT};
enum typestate goSTART(int c);
enum typestate goCOMMENT(int c);

/*
 * given an input c in the state START
 * perform the appropriate action and return the nextstate
 */
enum typestate
goSTART(int c)
{
    enum typestate retState;
    // !!!! TODO: FIX THIS LINE !!!!
     if (c ==  '>'){
	retState = COMMENT;
    }else{
	putchar(c);
	retState = START;
    }
    return retState;
}

/*
 * given an input c in the state COMMENT
 * perform the appropriate action and return the nextstate
 */
enum typestate
goCOMMENT(int c)
{
    // !!!! TODO: complete this code !!!
    enum typestate retState;
    if (c == '\n') {
	    putchar(c);
	    retState = START;
    } else {
	    retState = COMMENT;
    }
    return retState;
}


int
main(void)
{
    int c;
    enum typestate curState = START;
    while ((c = getchar()) != EOF){
	switch(curState){
	case START:
	    curState = goSTART(c);
	    break;
	case COMMENT:
	    curState = goCOMMENT(c);
	    break;
	default:
	    break;
	}
    }
    return EXIT_SUCCESS;
}
