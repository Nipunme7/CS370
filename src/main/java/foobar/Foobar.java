package foobar;

/**
 * Sample Java project
 * 
 * @author [your name]
 * @version 2025.2
 */
public class Foobar {
    /**
     * Constructor
     */
    public Foobar() {
    };

    /**
     * Print numbers between 1 and n replacing some of them as follows:
     * multiples of 3 - with foo
     * multiples of 5 - with bar
     * multiples of 3 and 5 - with foobar
     * 
     * @return sum of all numbers (i.e. those not replaced) as a checksum
     */
    public int check(int n) {
        int sum = 0;
        
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                // Multiple of both 3 and 5
                System.out.println("foobar");
            } else if (i % 3 == 0) {
                // Multiple of 3
                System.out.println("foo");
            } else if (i % 5 == 0) {
                // Multiple of 5
                System.out.println("bar");
            } else {
                // Not a multiple of 3 or 5, print the number and add to sum
                System.out.println(i);
                sum += i;
            }
        }
        
        return sum;
    }
}
