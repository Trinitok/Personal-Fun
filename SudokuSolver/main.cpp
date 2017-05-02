#include <assert.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>

// define how many dimensions there are in the sudoku game
#define SUBDIMENSION 3
// define the minimum number
#define MIN_VAL 1
// define the maximum number
#define MAX_NUM 9
//  how many total numbers are in the game
#define TOTAL_NUMS 9
#define ARRAY_SIZE (MIN_VAL + TOTAL_NUMS)


//  have an array of values of a particular row or column
typedef int values[ARRAY_SIZE];

//  set the initial values to 0
void initvalues(values *nums){
    int i;

    for(i = MIN_VAL; i <= MAX_NUM; i ++){
        (*nums)[i]=0;
    }
}

//  set a value to being used
void usevalues(values *nums, int num){

    (*nums)[num] = 1;

}

//  reset to 0 in case of mistake
void restorevalues(values *nums, int num){

    (*nums)[num] = 0;
}

//  cell structure consisting of row and column values
struct cell {
    int hasvalue;
    int value;

    values *rowvalues;
    values *colvalues;
    values *squarevalues;
};


struct board {
    int unsetcells;

    //  create a board of ARRAY_SIZE x ARRAY_SIZE
    struct cell cells[ARRAY_SIZE][ARRAY_SIZE];

    //  each of the parts of the board for redundancy
    values rows[ARRAY_SIZE];
    values columns[ARRAY_SIZE];
    values squares[ARRAY_SIZE];
};

//  gets the square for the particular row and column
int square(int row, int col){
    return (((row - 1) / SUBDIMENSION) * SUBDIMENSION) +
        ((col - 1) / SUBDIMENSION) + 1;
}


//  creates the game board
void initgame(struct board *b){
    int i;
    int j;

    b->unsetcells = TOTAL_NUMS * TOTAL_NUMS;

    for (i = MIN_VAL; i <= MAX_NUM; ++i) {
        initvalues(b->rows + i);
        initvalues(b->columns + i);
        initvalues(b->squares + i);

        for (j = MIN_VAL; j <= MAX_NUM; ++j) {
            b->cells[i][j].hasvalue = 0;
            b->cells[i][j].value = 0;
            b->cells[i][j].rowvalues = b->rows + i;
            b->cells[i][j].colvalues = b->columns + j;
            b->cells[i][j].squarevalues = b->squares + square(i, j);
        }
    }
}


//  finds which values are still missing from an atleast until the max num
int findcommonfree(values *r, values *c, values *s, int atleast){

    int i;
    for (i = atleast; i <= MAX_NUM; ++i)
        if ((! (*r)[i]) && (! (*c)[i]) && (! (*s)[i]))
            return i;
    return (-1);
}


//  sets a square on the board to the val
void setsquare(struct board *b, int row, int col, int val){

    b->unsetcells -= 1;
    b->cells[row][col].hasvalue = 1;
    b->cells[row][col].value = val;
    usevalues(b->cells[row][col].rowvalues, val);
    usevalues(b->cells[row][col].colvalues, val);
    usevalues(b->cells[row][col].squarevalues, val);
}

//  reset the square
void unsetcell(struct board *b, int row, int col, int val){

    b->unsetcells += 1;
    b->cells[row][col].hasvalue = 0;
    b->cells[row][col].value = 0;
    restorevalues(b->cells[row][col].rowvalues, val);
    restorevalues(b->cells[row][col].colvalues, val);
    restorevalues(b->cells[row][col].squarevalues, val);
}

//  checks to see if there is a value at a certain position of the board
int isset(struct board *b, int row, int col){

    return (b->cells[row][col].hasvalue);
}

/*
 * Calculates the number following a given one circularly.
 */
int following(int val){

    return ((val - MIN_VAL + 1) % TOTAL_NUMS + MIN_VAL);
}


int nextcell(int *row, int *col){

    if ((*row) == MAX_NUM && (*col) == MAX_NUM)
        return 0;

    *col = following(*col);
    if ((*col) == MIN_VAL)
        (*row) = following(*row);
    return 1;
}

//  prints out the game board
void printgame(struct board *b){
    int i;
    int j;

    for (i = MIN_VAL; i <= MAX_NUM; ++i) {
        for (j = MIN_VAL; j <= MAX_NUM; ++j)
            printf(" %d", b->cells[i][j].value);
        printf("\n");
    }
}

//  the main algorithm used to solve the game
int solvegame(struct board *b, int row, int col){
    int prev;
    int val;


    //  the game can already be solved
    if (b->unsetcells == 0) {
        printgame(b);
        return 1;
    }

    // Find the next unset square
    while (isset(b, row, col) && nextcell(&row, &col));

    // This should never happen.
    if (isset(b, row, col))
        return 1;

    // Try every possible cell value until the board can be solved.
    prev = MIN_VAL;
    while (1) {
        val = findcommonfree(b->cells[row][col].rowvalues,
                       b->cells[row][col].colvalues,
                       b->cells[row][col].squarevalues,
                       prev);
        if (val == -1)
            break;

        setsquare(b, row, col, val);
        if (solvegame(b, row, col))
            return 1;
        unsetcell(b, row, col, val);

        prev = val+1;
    }

    return 0;
}


void readfile(FILE *f, struct board *b)
{
    int row;
    int col;
    int c;

    assert(f != NULL && b != NULL);

    row = MIN_VAL;
    col = MIN_VAL;

    while (! feof(f)) {
        c = fgetc(f);
        //  check for if the value is a number or a .
        if ((isdigit(c) && c != '0') || c == '.') {
            if (c != '.')
                setsquare(b, row, col, (c - '0'));
            if (! nextcell(&row, &col))
                break;
        }
    }
}



int main(int argc, char *argv[])
{
    FILE *f;
    struct board b;
    int ret;


    //  handle error cases for when invalid input
    if (argc > 2) {
        fprintf(stderr, "too many arguments.  just give me the file with the game.  nothing else\n");

        return 1;
    }

    if (argc == 2) {
        f = fopen(argv[1], "r");
        if (f == NULL) {
            fprintf(stderr, "could not open \"%s\"\n", argv[1]);

            return 2;
        }
    }
    else {
        fprintf(stderr, "too few arguments.  give me a file with the current board state\n");

        return 3;
    }

    //  get the initial structs being used to solve game
    initgame(&b);

    //  read the file and then solve
    readfile(f, &b);
    ret = solvegame(&b, MIN_VAL, MIN_VAL);

    //  close file stream
    fclose(f);
    if (! ret)
         fprintf(stderr, "ERROR: board could not be solved\n");

    if(ret == 0) return 0; else return 4;


}
