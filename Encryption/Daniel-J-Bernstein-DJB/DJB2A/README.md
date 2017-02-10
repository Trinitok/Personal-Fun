DJB2
--
Created and named after Dr. Daniel Bernstein.  This is a universal hashing algorithm that is meant to be picked from a random family of hashes in order to avoid collisions.  It does this by using Linear Congruential Generation in order to produce unique hashes for a given input.  There are several more out there very similar to this version that use different values in their implementation.  The consistent algorithm is written in C code below

```c
    unsigned long hash(unsigned char *str)
    {
        unsigned long hash = 5381;
        int c;

        while (c = *str++)
            hash = ((hash << 5) + hash) + c; /* hash * 33 + c */

        return hash;
    }
```

What is interesting about this implementation is the use of the numbers 5381 and 33.  The 33 is, as described by Dr. Bernstein, more important because of its properties.  The number 5381 is a bit more arbitrary, but still serves a purpose in supplying a suitable hash.  As explained by [this link](http://stackoverflow.com/questions/10696223/reason-for-5381-number-in-djb-hash-function/13809282#13809282), the numbers merely result in the fewest collisions.

Pros
--
- Very fast
- Few collisions
- good for multiple system simulations (games for example)
- Low memory/system requirements

Cons
--
- Not suitable for crypto analysis
- Good, not great, "randomness"

DJB2 vs DJB2A
--
- DJB2A is faster and will have fewer collisions

Examples
--
- Java - String.java uses something similar in their hashCode method (line 1495) 
http://www.docjar.com/html/api/java/lang/String.java.html

References
--
http://stackoverflow.com/questions/10696223/reason-for-5381-number-in-djb-hash-function/13809282#13809282
http://blog.intelligencecomputing.io/software-develop/6806/repost-which-hashing-algorithm-is-best-for-uniqueness-and-speed
http://www.cse.yorku.ca/~oz/hash.html
