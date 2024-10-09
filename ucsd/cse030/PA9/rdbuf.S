#ifndef MYRDBUF_S
#else
// version 1.1 11/29/2022 //
    .arch   armv6
    .arm
    .fpu    vfp
    .syntax unified

    // global constants
    #include "cipher.h"

    .extern fread
    .extern feof
    .extern ferror
    .text

    // int rdbuf(r0=FILE *in, r1=FILE *fpbook, r2=int cnt, r3=char *iobuf, 
    //            on the stack at ARG5 offset=char *bookbuf)
    // returns number of bytes read EXIT_FAIL otherwise
    // you can change or remove any comments in this file

    .type   rdbuf, %function
    .global rdbuf
    .equ    ARG5,  4 
    .equ    FP_OFF, 28
 
rdbuf:
    push    {r4-r9, fp, lr}
    add     fp, sp, FP_OFF

    // save the register args (r0-r3) into preserved registers
    // as there are many function calls
    mov	    r4, r0		// save r0 = FILE *in
    mov	    r5, r1		// save r1 = FILE *fpbook
    mov	    r6, r2		// save r2 = int cnt
    mov	    r7, r3		// save r3 = char *iobuf

    // check for end of file feof(in)
    bl	    feof		// feof(in)
    cmp	    r0, 0		// compare
    beq	    .Lferror

    // if r0 is not zero at EOF, set r0 to 0 and return
    mov	    r0, 0		// set r0 to 0
    b	    .Ldone		// return

    // otherwise check for read errors
.Lferror:
    mov	    r0, r4
    // check for error ferror(in)
    bl	    ferror		// ferror(in)
    cmp	    r0, 0		// compare
    // if r0 is zero, no errors, do the reads
    beq	    .Lfread
    // if r0 is not zero, set r0 to EXIT_FAIL and return
    mov	    r0, EXIT_FAIL	// set r0 to EXIT_FAIL
    b	    .Ldone		// return

    // bytes = fread(r0=iobuf, r1=1, r2=cnt, r3=in)
.Lfread:
    mov	    r0, r7		// r0 = iobuf
    mov	    r1, 1		// r1 = 1
    mov	    r2, r6		// r2 = cnt
    mov	    r3, r4		// r3 = in
    bl	    fread		// fread(iobuf, 1, cnt, in)
    cmp	    r0, 0		// compare
    // save bytes so we can check that we read same bytes from bookfile
    bgt	    .Lsave		// save bytes
    // if r0 <= 0 return with r0 = 0
    mov	    r0, 0
    b	    .Ldone

    // r0 = fread(r0=bookbuf, r1=1, r2=bytes, r3=fpbook)
.Lsave:
    mov	    r8, r0		// save bytes
    // the pointer to bookbuf is passed on the stack so
    // you need to do a ldr from ARG5 offset to get the address
    // to pass to the fread
    ldr	    r0, [fp, ARG5]	// pass address to fread
    mov	    r1, 1		// r1 = 1
    mov	    r2, r8		// r2 = bytes
    mov	    r3, r5		// r3 = fpbook
    bl	    fread		// fread(bookbuf, 1, bytes, book)
    // if r0 on the return from fread is not equal to bytes
    cmp	    r0, r8		// compare the bytes
    // (bytes is the value returned in r0 from the fread into iobuf above),
    // then return EXIT fail
    bne	    .Lelse
    // otherwise set r0 = bytes and return
    mov	    r0, r8
    b	    .Ldone

.Lelse:
    mov	    r0, EXIT_FAIL

.Ldone:
    sub     sp, fp, FP_OFF
    pop     {r4-r9, fp, lr}
    bx      lr

    .size   rdbuf, (. - rdbuf)
    .section .note.GNU-stack,"",%progbits
.end  // DONE
#endif
