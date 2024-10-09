// version 1.0 11/12/2022
#include <stdio.h>
#include "cipher.h"

#ifdef MYENCRYPT_C
TODO("using Cencrypt.c");

int
encrypt(char *iobuf, char *bookbuf, int cnt)
{
    for (int i = 0; i < cnt; i++) { // process bytes
        // initialize with iobuf
	    unsigned int byte = *(iobuf + i);
	    unsigned int rbyte; // initialize reversed byte
	    for (int j = 0; j < 8; j++) { // loop through bits
		    rbyte = (byte & 1) | (rbyte << 1); // reverse bits
		    byte = byte >> 1; // move to next byte
	    } // stores XOR of reversed byte and byte from bookbuf
	    *(iobuf + i) = (rbyte^(*(bookbuf + i)));
    }
    return cnt; // return
} // DONE

#elif defined MYENCRYPT_S
TODO("using encrypt.S");
#else
TODO("using solution encrypt");
#endif
