/**
*   MD5sum testing
*   Max Kelly
*
*   for more explanation on MD5:
*   http://www.ccodechamp.com/c-program-of-md5-encryption-algorithm-c-codechamp/
*   https://tools.ietf.org/html/rfc1321
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


//  come back and figure this out
typedef union uwb{
    unsigned word;
    unsigned char bits[4];
} MD5union;

typedef unsigned DigestArray[4];  //  an array to hold 4 hashed bytes


/*
*   We will make our functions to help "digest" the inputs.
*   These functions are the core of the MD5 hash and can be found on any website that describes the hash
*/
unsigned digestFunc0(unsigned abcd[]){
    return (abcd[1] & abcd[2]) | (~abcd[3] & abcd[2]);
}
unsigned digestFunc1(unsigned abcd[]){
    return ( abcd[3] & abcd[1]) | (~abcd[3] & abcd[2]);
}
unsigned digestFunc2(unsigned abcd[]){
    return abcd[1] ^ abcd[2] ^ abcd[3];
}
unsigned digestFunc3(unsigned abcd[]){
    return abcd[2] ^ (abcd[1] |~ abcd[3]);
}

typedef unsigned (*DigestFunction) (unsigned a[]);

/**
*   This function will help perform the calculations for MD5.  It will store all numbers and make it so that even if the input is less than 64 bits, that it will be filled with a bunch of trailing 0s
*/
unsigned *calctable(unsigned *k){
    double s, pwr;
    int i;

    pwr = pow(2, 32);
    for(i = 0; i < 64; i ++){
        s = fabs(sin(1 + i));  //  take the absolute val of the sin
        k[i] = (unsigned) (s * pwr);
    }
    return k;
}

/**
*   this function will rotate the current values
*/
unsigned rotateLeft(unsigned r, short N){
    unsigned mask = (1 << N) - 1;
    return ((r >> (32 - N)) & mask) | ((r << N) & ~mask);
}

unsigned *md5(const char *msg, int msglen){
    /*
    *   The holder array will store the hashed letters A, B, C, D of the MD format.  They are placeholders and completely arbitrary
    *   Notice how the bytes are stored in reverse numerical order?  So instead of A being 01 23 45 67, it is 67452301
    *   This gives it the appearance of seemingly arbitrary values
    *   By looking at the paperwork for the algorithm, as well as stackoverflow, you will find that the numbers below represent h0-h3, or A-D
    *   These numbers will be used to "digest" (buffer) an input of arbitrary length and produce a 128 bit output
    *   Upon using more of the hash algorithm, you will discover what these are used for
    *
    */
    static DigestArray h0 = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};

    /*
    *   This will help store all the digest functions.  These will include all bit shifts to be done in order to properly digest the input
    *
    *
    */
    static DigestFunction fcns[] = {
        &digestFunc0,
        &digestFunc1,
        &digestFunc2,
        &digestFunc3
    };

    /*
    *   Each of these arrays are to help compute the hash.  The values in them are somewhat arbitrary.
    *   While there are guidelines to what the numbers below should optimally be, we can, however improve them by introducing more prime values.
    *   They will be computed in the functions above to complete the hash
    *   The values used for the arrays can be found at http://www.ccodechamp.com/c-program-of-md5-encryption-algorithm-c-codechamp/
    */
    static short M[] = {1, 5, 3, 7};
    static short O[] = {0, 1, 5, 0};
    static short rotation0[] = {7, 12, 17, 22};
    static short rotation1[] = {5, 9, 14, 20};
    static short rotation2[] = {4, 11, 16, 23};
    static short rotation3[] = {6, 10, 15, 21};
//    static short *rotArray[4] = (rotation0, rotation1, rotation2, rotation3);
    static short *rotArray[4];
    rotArray[0] = *rotation0;
    rotArray[1] = *rotation1;
    rotArray[2] = *rotation2;
    rotArray[3] = *rotation3;
    static short kspace[64];
    static unsigned *k;

    static DigestArray h;
    DigestArray abcd;
    DigestFunction fcn;
    short m, o, g;
    unsigned f;
    short rotation;

    //  The unions will help convert the 16 bit values into 64 bit values
    union{
        unsigned wrote[16];
        char bits[64];
    } mm;
    int os = 0;
    int grp, grps, q, p;
    unsigned char *msg2;

    //  If there is nothing left in the array, then use the calctable function to fill in values
    if(k == NULL){
        k = calctable(kspace);
    }

    for(q = 0; q < 4; q ++) h[q] = h0[q]; // be sure to initialize this variable in the for loop
    {
        grps = 1 + (msglen + 8) / 64;
        msg2 = malloc(64 * grps);
        memcpy(msg2, msg, msglen);
        msg2[msglen] = (unsigned char) 0x80;
        q = msglen + 1;
        while(q < 64 * grps){
            msg2[q] = 0;
            q ++;
        }
        {
            //  convert values from the msg copy to the value output by the MD5 hash
            MD5union converter;
            converter.word = 8 * msglen;
            q -= 8;
            memcpy(msg2 + q, &converter.word, 4);

        }
    }

    // loop for performing the algorithm
    for(grp = 0; grp < grps; grp ++){
        memcpy(mm.bits, msg2 + os, 64);
        for(q = 0; q < 4; q ++){
            abcd[q] = h[q];
        }
        for(p = 0; p < 4; p ++){
            fcn = fcns[p];
            rotation = rotArray[p];
            printf("val of rotation: %hu", rotation);
            m = M[p];
            o = O[p];
            for(q = 0; q < 16; q ++){
                g = (m*q + o) % 16;
                /*
                *   the abcd array will have the first value to be hashed.
                *   The fcn will contain the current level of digest function and will be passed the array to hash
                *   The rotation will be the amount that the value is "rotated" on based on the arbitrary values from above
                *   The purpose of this is to convert all the hash values and then cycle through the array values in abcd
                */
                f = abcd[1] + rotateLeft(abcd[0] + fcn(abcd) + k[q + 16*p] + mm.wrote[g], rotation);
                abcd[0] = abcd[3];
                abcd[3] = abcd[2];
                abcd[2] = abcd[1];
                abcd[1] = f;
            }
        }
        for(p = 0; p < 4; p ++){
            h[p] += abcd[p];
        }
        os += 64;
    }
    return h;
}


/**
*   Message Digest v5 algorithm by Max Kelly
*   inspiration from:  http://www.ccodechamp.com/c-program-of-md5-encryption-algorithm-c-codechamp/
*/
int main(int argc, char *argv[])
{

    int j, k;
    const char *msg = "this is a stupid game.  please do not play me anymore.  i do not appreciate it";
    printf("This will make an MD5 hash\n");
    printf("orig msg: %s", msg);
    // this function will return the hash
    unsigned *encrypt = md5(msg, strlen(msg));
    MD5union output;
    printf("\n\n\nThe MD5 code generated by the msg is: \n");
    printf("\\t= 0x");
    for(j = 0; j < 4; j ++){  // get the 4 bytes for each one
        output.word = encrypt[j];
        for(k = 0; k < 4; k ++){
            printf("%02x",output.bits[k]);
        }
    }

    printf("\n");
    printf("The msg should be successfully encrypted with MD5\n");
    system("pause");  //  uncomment this when ready to throw on GitHub
    return 0;
}
