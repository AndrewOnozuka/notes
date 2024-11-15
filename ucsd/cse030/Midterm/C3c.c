#include <stdlib.h> // c
#include <stdio.h>
#include <string.h>
int
main(int argc, char **argv){
    int num = 0;   // count of matches
    if (argc < 2){
	return EXIT_FAILURE;
    }else{
	char **argp = argv + 2;   // first possible match
	char **endargv = argv + argc - 1;  // last possible match
	while (argp <= endargv){
	    // !!!! TODO complete this code !!!!
	    //
	    // you may use strcmp to compare strings
	    // use pointer arithmetic, do not use square bracket indices []
		if (strcmp(*(argv + 1), **argp)) {
				num++
				}
	
	}

	printf("the string %s occurs %d more time(s) \n", *(argv+1), num);
    }
    return EXIT_SUCCESS;
}
