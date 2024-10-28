#include <stdio.h> // c
#include <stdlib.h> // do not add includes
int
main(void)
{
    char string[] =
"jli**lkjfasd&&jlf*asflasjle*jslcjsfs\"lkjdafdk&ls# #kfjlw*ejflfjsflsf\
erwerwrgvx*vn*dlkwjrsal\" sfsf&&afw  sdfs*wefwej3j#kjlw*elrjwljklj\
flk#j&r*u*af\"jakj**flasj&&fslfjk plo*kjuiywljrs# #lfjalj#*ef*yill*l&&jsfs \
flk#j\"jakj##fl*sjfs* fjk o##urwe&wljrs# #lfjalj jslfjal*re&wqs";


    char *astrptr = string;
    int cnt = 0;

    while (*astrptr != '\0'){
	// !!!! TODO complete this code to count the number of seperators
	// !!!! do not use any string library functions like 
	// !!!! strcmp, strlen, etc.
	if (*astrptr == '\0') {
		cnt++;
	}
    }
    printf("Number of field separators = %d\n", cnt);
    return EXIT_SUCCESS;

}
