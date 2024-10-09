    // version 1.1 10/29/2022 //
#ifndef MYMAIN_S
#else
    .arch   armv6
    .arm
    .fpu    vfp
    .syntax unified

    // imports
    #include "main.h"
    #include "rdbuf.h"
    #include "setup.h"
    #include "cipher.h"
    .extern fwrite
    .extern stdin
    .extern stdout
    .extern stderr

    .section .rodata
    // do not change these error messages
.Lbdrd:  .string "%s: read failed\n"
.Lbdwr:  .string "%s: write failed\n"

    .text

    //
    // encrypter [-d | -e] -b bookfile
    //
    // int main(int argc, char **argv)
    // int main(r0=argc, r1=argv)

    .global main
    .type   main, %function

    //
    // stack frame layout
    //
    // TODO PA9: complete the following stack frame design
    // You can change or delete any comments in this file
    //
    .equ    FP_OFF,     32          	// fp offset in main stack frame
    .equ    FPBOOK,     FP_OFF + 4      // FILE * to book file
    .equ    IOBUF,	FPBOOK + 128	// buffer for input file
    .equ    MODE,       IOBUF + 4    	// decrypt or encrypt mode
    .equ    BOOKBUF,    MODE + 128	// buffer for book file
    .equ    ARGV0,      BOOKBUF + 4	// save argv[0] for error printing
    //
    // add your local variables here if you need them:
    // NOTICE: Adjust PAD so FRMADD + FP_OFF + 4 divides by 8 EVENLY!
    //
    .equ    PAD,       ARGV0 + 4	// Stack frame PAD
    .equ    OARG5,     PAD + 4		// output arg5 must be at bottom
    .equ    FRMADD,    OARG5-FP_OFF	// space in frame for locals+passed args

    // TODO PA9: list how you are using the preserved registers
    // preserved register use
    // r4 bookbuf
    // r5 argv
    // r6 mode flag
    // r7 cnt
    // r8 OARG5
    // r9 
    // r10

main:
    push    {r4-r10, fp, lr}        	// push all the usable preserved registers
    add     fp, sp, FP_OFF          	// set frame pointer to frame base
    ldr     r3, =FRMADD             	// get space needed for locals+passed args
    sub     sp, sp, r3              	// allocate space for locals and passed args 
     
    // save argv[0] in argv0
    ldr	    r5, [r1]			// load argv 
    str	    r5, [fp, -ARGV0]		// argv0 = *argv

    // at the entry to main() argc is in r0 and argv is in r1.
    // call int setup(r0=argc, r1=argv, r2=*mode, r3=*FPBOOK)
    // r2 and r3 are output parameters. Get the addresses of where mode
    // and FPBOOK are located on main()'s stack and put these in r2 and r3
    // so setup can fill them in (passing a pointer to a local variable in r2,r3)
    ldr	    r2, =MODE
    sub	    r2, fp, r2			// r2 = *mode
    ldr	    r3, =FPBOOK
    sub	    r3, fp, r3			// r3 = *FPBOOK
    bl	    setup
    ldr	    r6, = MODE
    ldr	    r6, [fp, -r6]		// mode flag

    // if setup fails, put EXIT_FAILURE in r0 and then branch to .Lexit
    cmp	    r0, EXIT_FAIL
    bne	    .Lloop
    mov	    r0, EXIT_FAILURE
    b	    .Lexit
    
    // set up for main loop
    // save the variables you need in the preserved registers as you will be making
    // function calls in the loop body below and r0-r3 will be used for that

.Lloop:
    // this is a loop that runs until rdbuf indicates the EOF on stdin
    // was reached or there was an error.
    
    // int rdbuf(r0=stdin, r1=fpbook, r2=BUFSZ, r3=iobuf,OARG5=bookbuf)
    // NOTICE that the 5th arg is on the stack so you need to str the 
    // address of bookbuf at offset OARG5 on the stack
    ldr	    r0, =stdin
    ldr	    r0, [r0]			// r0 = stdin
    ldr	    r1, =FPBOOK
    ldr	    r1, [fp, -r1]		// r1 = fpbook
    ldr	    r2, =BUFSZ			// r2 = BUFSZ
    ldr	    r3, =IOBUF
    sub	    r3, fp, IOBUF		// r3 = iobuf

    // NOTICE that the 5th arg is on the stack so you need to str the
    // address of bookbuf at offset OARG5 on the stack
    ldr	    r4, =BOOKBUF
    sub	    r4, fp, r4
    ldr	    r8, =OARG5
    sub	    r8, fp, r8
    str	    r4, [r8]
    bl	    rdbuf

    // if r0 on return from rdbuf has a 0 in r0 we are done, 
    // branch to .Ldone (EXIT_SUCCESS is a 0)
    // if return less than 0, error print to stderr .Lbdrd, 
    // Put EXIT_FAILURE in r0 and go to .Ldone
    cmp	    r0, 0			// compare r0 return
    bgt	    .Lcont			// continue
    beq	    .Ldone			// exit success if r0 has 0
    blt	    .Lrderr			// if return less than 0

.Lrderr:
    ldr	    r1, =.Lbdrd
    ldr	    r0, =stderr
    ldr	    r0, [r0]
    mov	    r2, r5
    bl	    fprintf			// load args to call fprintf
    b	    .Ldone

    // check the mode flag and call either encrypt or decrypt
    // int XXcrypt(r0=char *iobuf, r1=char *bookbuf, r2=int cnt)
    // encrypt or decrypt 
    // returns cnt
.Lcont:
    mov	    r2, r0			// r2 = cnt
    ldr	    r0, =IOBUF
    sub	    r0, fp, r0			// r0 = char *iobuf
    ldr	    r1, =BOOKBUF
    sub	    r1, fp, r1			// r1 = char *bookbuf
    cmp	    r6, ENCRYPT_MODE		// check to decrypt or encrypt
    bne	    .Ldecrypt
    bl	    encrypt			// encrypt
    b	    .Lwrite			// branch to write

.Ldecrypt:
    bl	    decrypt			// decrypt then fall through
    
    // make sure to save cnt so we can check that fwrite wrote cnt characters
    // fwrite returns number of characters it wrote.
    // fwrite(r0=iobuf, r1=1, r2=cnt, r3=*stdout)
.Lwrite:
    mov	    r7, r0			// save cnt
    cmp	    r0, EXIT_FAIL
    beq	    .Ldone
    ldr	    r0, =IOBUF
    sub	    r0, fp, r0			// r0 = iobuf
    mov	    r1, 1			// r1 = 1
    mov	    r2, r7			// r2 = cnt
    ldr	    r3, =stdout
    ldr	    r3, [r3]			// r3 = *stdout
    bl	    fwrite

    // if cnt characters written go to .Lloop (write is ok)
    // othewise error, print to stderr .Lbdwr, put EXIT_FAILURE in r0
    // and fall through to .Ldone
    cmp	    r0, r7			// compare to cnt
    beq	    .Lloop
    ldr	    r1, =.Lbdwr
    ldr	    r0, =stderr
    ldr	    r0, [r0]
    mov	    r2, r5			// get saved argv0
    bl	    fprintf
    mov	    r0, EXIT_FAILURE
    
.Ldone:
    // normal exit, call fclose of fpbook and fall through
    // to .Lexit
    ldr	    r0, =FPBOOK
    ldr	    r0, [fp, -r0]
    bl	    fclose			// load fpbook to call fclose on it

.Lexit:
    sub     sp, fp, FP_OFF
    pop     {r4-r10, fp, lr}
    bx      lr

    .size   main, (. - main)
    .section .note.GNU-stack,"",%progbits
.end // DONE
#endif
