// version 1.0 11/12/2022
#include <stdio.h>
#include "cipher.h"

#ifdef MYDECRYPT_C
TODO("using Cdecrypt.c");

int
decrypt(char *iobuf, char *bookbuf, int cnt)
{
    for (int i = 0; i < cnt; i++) { // process bytes
	    // initialize byte with XOR
	    unsigned int byte = ((*(iobuf + i))^(*(bookbuf + i)));
	    unsigned int rbyte; // initialize reversed byte
	    for (int j = 0; j < 8; j++) { // loop through bits
		    rbyte = (byte & 1) | (rbyte << 1); // reverse bits
		    byte = byte >> 1; // move to next byte
	    }
	    *(iobuf + i) = rbyte; // stores reversed byte
    }
    return cnt; // return
} // DONE

#elif defined MYDECRYPT_S
TODO("using decrypt.S");
#else
TODO("using solution decrypt");
#endif
