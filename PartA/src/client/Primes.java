package client;


import compute.Task;
import java.io.Serializable;
import java.util.ArrayList;

public class Primes implements Task<ArrayList<Integer>>, Serializable {

    private static final long serialVersionUID = 228L;

    /** min and max to calculate primes between */
	private final int min;
	private final int max;

    /**
     * Construct a task to calculate primes within
     * given range
     */
    public Primes(int min, int max) {
		this.min = min;
		this.max = max;
    }

    /**
     * Calculate primes
     */
    public ArrayList<Integer> execute() {
        return computePrimes(min, max);
    }

    /**
     * Loop through all values in range, for each,
	 * check if it's divisible by any of its values
	 * below its half
     */
    public static ArrayList<Integer> computePrimes(int min, int max) {
		ArrayList<Integer> result = new ArrayList<>();
		not:
		for (int i = min; i <= max; i++) {
			if (i <= 1) {
				continue;
			} else {
				for (int j = 2; j <= (i/2 + 1); j++) {
					if (i % j == 0)
						continue not;
				}
				result.add(i);
			}
		}
        return result;
    }
}
