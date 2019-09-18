package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Estimate the value of the percolation threshold via Monte Carlo simulation.
 * @author Jennifer Chen
 */
public class PercolationStats {
    /** Result of percolation experiments. */
    private double[] results;
    /** Z value for 95% confidence. */
    private static final double Z = 1.96;

    /**
     * Perform T independent experiments on an N-by-N grid.
     * @param N the side length of the grid area
     * @param T the number of experiments
     * @param pf used to instantiate a new Percolation class
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <=0 || T <= 0) {
            throw new IllegalArgumentException("input N & T should bigger than 0");
        }
        results = new double[T];
        Percolation p;
        for (int t = 0; t < T; t++) {
            p = pf.make(N);
            results[t] = percolationExperiment(p, N);
        }
    }

    /**
     * Perform a single experiment on estimating the percolation threshold.
     * @param p the Percolation instance for the experiment
     * @param N the side Length of the Percolation grid area
     * @return the experiment result (value of percolation threshold)
     */
    private double percolationExperiment(Percolation p, int N) {
        while (!p.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            if (!p.isOpen(row, col)) {
                p.open(row, col);
            }
        }
        return (double) p.numberOfOpenSites() / (N * N);
    }

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return mean() - Z * stddev() / Math.sqrt(results.length);
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return mean() - Z * stddev() / Math.sqrt(results.length);
    }

    /**
     * Use for unit testing.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(20, 1000, pf);
        System.out.printf("mean: %f\n", ps.mean());
        System.out.printf("stddiv: %f\n", ps.stddev());
        System.out.printf("confidenceHigh: %f\n", ps.confidenceHigh());
        System.out.printf("confidenceLow: %f\n", ps.confidenceLow());
    }
}
