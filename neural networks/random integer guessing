#include <stdio.h>
#include <stdlib.h>

int main()
{

    printf("please input an integer greater than 0\n");
    char *buffer = malloc(sizeof(char) * 256);

	fgets(buffer, 16, stdin);
	int target = atoi(buffer);
	printf("you input %d\n", target);
	if(target <= 0){
	    free(buffer);
        printf("it needs to be greater than 0\n");
        main();
	}
	printf("what is the maximum number?\n");
	fgets(buffer,16,stdin);
	int max = atoi(buffer);
	if(max < target){
        free(buffer);
        printf("the maximum is less than the target value.  Please re-input values\n");
        main();
	}
	printf("how many guesses do i get?\n");
	fgets(buffer,256,stdin);
	int guesses = atoi(buffer);


	//  random guessing
	printf("excellent.  Lets see the accuracy of always guessing at random\n");
	randguessing(guesses, max, target);


	//  machine learning guessing
	printf("now we can try with machine learning\n");
    machinelearning(guesses, max, target);

    //free the buffer before exiting
    free(buffer);
    return 0;
}



void randguessing(int guesses, int max, int target){
    int i;
	int success = 0;
    srand(time(NULL));
	for(i = 0; i < guesses; i ++){
        int compguess = rand() % max + 1;
        printf("guessing %d\n", compguess);
        if(compguess == target) success ++;
	}
	printf("random guessing was correct %.3f\n", (float)success/guesses);
}

machinelearning(int guesses, int max, int target){
    int i;
    int success = 0;
	int correctval = -1;
	int wrongvals[max];
	//  initialize wrong val array
	for(i = 0; i < max; i ++){
        wrongvals[i] = 0;
	}
    srand(time(NULL));
	//  begin guessing algorithm
	for(i = 0; i < guesses; i ++){
	    int compguess;

	    //  if the correct val has already been found
        if(correctval >= 0) compguess = correctval;

        //  random guessing that will make sure it  doesn't pull from the wrong ints
        else{

            compguess = rand() % max + 1;
            printf("educated guessing: %d.  Target is %d\n",compguess,target);
            int j = 0;

            //  scan for if the wrong val is in the wrongvals array
            for(j; j < max; j++){
                if(compguess == wrongvals[j]){
                    printf("redoing guess because %d = %d\n", compguess, wrongvals[j]);
                    compguess = rand() % max + 1;
                    printf("new educated guessing: %d.  Target is %d\n",compguess,target);
                    j = 0;
                }
            }
        }


        if(compguess == target){
            printf("correct compguess: %d\n",compguess);
            success ++;
            correctval = compguess;
        }
        else{
            printf("wrong compguess: %d\n",compguess);
        //  not working because compguess is some ludicrous high number
            wrongvals[compguess] = compguess;
        }
	}

	printf("known guessing was correct %.3f\n", (float)success/guesses);
}
