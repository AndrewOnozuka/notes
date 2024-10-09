#ifndef MYDECRYPT_S
#else
// version 1.0 11/12/2022
    .arch armv6
    .arm
    .fpu vfp
    .syntax unified

    #include "cipher.h"

    .text     // start of text segment

    //////////////////////////////////////////////////
    // int decrypt(char *iobuf, char *bookbuf, int cnt)
    // decrypts iobuf using bookbuf; result in iobuf
    //////////////////////////////////////////////////
    .global decrypt
    .type   decrypt, %function
    .equ    FP_OFF, 28

decrypt:
    push    {r4-r9, fp, lr}
    add     fp, sp, FP_OFF
    // do not alter anything above this line
    // r0 contains char *iobuf
    // r1 contains char *bookbuf
    // r2 contains cnt
    // r3 for1 counter i
    // r4 for2 counter j
    // r5 *(iobuf + i)
    // r6 *(bookbuf + i)
    // r7 byte
    // r8 rbyte
    // r9 byte & 1

    cmp     r2, 0               // if buffer empty we are done
    ble     .Ldone
	
    mov	    r3, 0		// int i = 0

.Lfor1:
    cmp	    r3, r2		// i < cnt
    bge     .Ldone		// loop done

    ldrb    r5, [r0, r3]	// *iobuf
    ldrb    r6, [r1, r3]	// *bookbuf
    eor     r7, r5, r6		// byte = iobuf XOR bookbuf
    mov     r8, 0		// initialize rbyte
    mov     r4, 0		// int j = 0

.Lfor2:
    and     r9, r7, 1		// byte & 1 (arg1)
    lsl     r8, r8, 1		// rbyte << 1 (arg2)
    orr	    r8, r8, r9		// rbyte = arg1 | arg2
    lsr     r7, r7, 1		// byte >> 1
    add     r4, r4, 1		// j++
    cmp     r4, 8		// j < 8
    bne	    .Lfor2		// for2 for more bits

    strb    r8, [r0, r3]	// *(iobuf + i) = rbyte
    add	    r3, r3, 1		// i++
    b	    .Lfor1		// for1 for more bytes

    // do not alter anything below this line
.Ldone:
    mov     r0, r2              // return cnt processed
    sub     sp, fp, FP_OFF
    pop     {r4-r9, fp, lr}
    bx      lr

    .size decrypt, (. - decrypt)
    .section .note.GNU-stack,"",%progbits
.end
#endif
