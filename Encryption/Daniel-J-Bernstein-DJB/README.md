DJB
--
<p>The Daniel J Bernstein hash algorithms were created by, you guessed it, Daniel J Bernstein.  They are not meant as encryption algorithms, but are useful for hashing due to their low collision rate.  The reason why they are ill suited for security purposes is because the way that BJD encrypts input is too fast.  Compared to algorithms like MD5 or SHA256, the DJB algorithms compute a hash exponentially faster.  Once I have computed time differentials for each of the hashes, I will upload them.  For now I will simply provide the link below for current references on speed and collisions until I have uploaded metrics comparing each algorithm.</p>

DJB2 v. DJB2a
--
The key difference in how these two algorithms work is very small, but effective.  Instead of multiplying, there is an XOR amongst the bits.  This causes the DJB2A algorithm to have considerably fewer collisions, and also take longer to compute than the original.


Link for current statistics:
<a href="http://softwareengineering.stackexchange.com/questions/49550/which-hashing-algorithm-is-best-for-uniqueness-and-speed"> StackOverflow </a>
