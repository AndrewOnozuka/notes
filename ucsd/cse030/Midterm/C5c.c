#include <stdio.h> // c
#include <stdlib.h>
#define STABLESZ 4
int
main()
{
    char **strTable;
    strTable = malloc(sizeof(char *) * STABLESZ);

    char **strTablePtr = strTable;
    
    *strTablePtr++ = "What do personal ";
    *strTablePtr++ = "injury attourneys wear to court?";
    *strTablePtr++ = "Lawsuits";
    *strTablePtr = NULL;   // table ends in NULL

    //
    // print the strings that strTable points to
    //
    for (strTablePtr = strTable; *strTablePtr != NULL; strTablePtr++){
	printf("%s\n", *strTablePtr);
    }

    // There is a memory leak.
    // !!!! TODO :Fix it here!!!!

    return EXIT_SUCCESS;

}
