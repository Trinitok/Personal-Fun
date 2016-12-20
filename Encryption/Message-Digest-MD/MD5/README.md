MD5Sum is a 128-bit hashing algorithm that was used up until 2012 to encode files.  Like other MD series, it can be used to verify the integrity of a file.  This only works for unintentional corruption, such as when moving a file between directories.  Third party sources might have been hacked and the integrity of the file will remain the same.

pros
--
-  File verification
-  Defense against preimage attacks

cons
--
-  Extreme vulnerabilities to collisions
-  One way algorithm.  No way to really "decrypt" due to pigeonholing principle.  There are 2^128 possible combinations, and an infinite (approximately) amount of string inputs.  Since they are all being compressed, it is impossible to accurately get the exact return value in SOME cases because larger string inputs will lose data when being compressed.


Comparison to MD4
--
Examples
--
<a href="http://www.yahoo.com"> Yahoo </a> still uses MD5 to hash passwords
References
--
https://tools.ietf.org/html/rfc1321
