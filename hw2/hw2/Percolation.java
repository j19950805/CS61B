package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Model a percolation system using an N-by-N grid of sites.
 * @author Jennifer Chen
 */
public class Percolation {
    /** the grid of sites representing a percolation system. */
    private int[][] grid;
    /** the number of open sites. */
    private int openSiteNum;
    /** the side length of the grid area. */
    private int sideLength;
    /** UnionFind instance correspond to grid,
     *  used to update connection among grid. */
    private WeightedQuickUnionUF uf;
    /** UnionFind instance with only virtual top. */
    private WeightedQuickUnionUF ufTop;
    /** The ufIndex of the virtual top that all top sites is
     * connect to it. */
    private int topIndex;
    /** The ufIndex of the virtual bottom that all bottom
     * sites is connected to it. */
    private int bottomIndex;

    /**
     * Create N-by-N grid, with all sites initially blocked.
     * @param N the side length of the grid area
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should bigger than 0");
        }
        grid = new int[N][N];
        sideLength = N;
        openSiteNum = 0;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufTop = new WeightedQuickUnionUF(N * N + 1);
        topIndex = N * N;
        bottomIndex = N * N + 1;
        for (int i = 0; i < N; i++) {
            uf.union(i, topIndex);
            ufTop.union(i, topIndex);
        }
        for (int i = ufIndex(N - 1, 0); i < N * N; i++) {
            uf.union(i, bottomIndex);
        }
    }

    /**
     *  Convert row & col index to UnionFind index.
     * @param row the row index of the site
     * @param col the column index of the site
     * @return the correspond uf index of the site
     */
    private int ufIndex(int row, int col) {
        return row * sideLength + col;
    }

    /**
     * Check if the input rol & col index are valid.
     * @param row the row index of the site
     * @param col the column index of the site
     */
    private void checkIndex(int row, int col) {
        if (row < 0 || row >= sideLength || col < 0 || col >= sideLength) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }
    }

    /**
     * open the site (row, col) if it is not open already.
     * @param row the row index of the site
     * @param col the column index of the site
     */
    public void open(int row, int col) {
        checkIndex(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = 1;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] dir: directions) {
            if (row + dir[0] < 0 || row + dir[0] >= sideLength
                || col + dir[1] < 0 || col + dir[1] >= sideLength) {
                continue;
            }
            if (grid[row + dir[0]][col + dir[1]] == 1) {
                uf.union(ufIndex(row, col),
                         ufIndex(row + dir[0], col + dir[1]));
                ufTop.union(ufIndex(row, col),
                            ufIndex(row + dir[0], col + dir[1]));
            }
        }
        openSiteNum += 1;
    }

    /**
     * @param row the row index of the site
     * @param col the column index of the site
     * @return whether the site (row, col) is open
     */
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return grid[row][col] == 1;
    }

    /**
     * @param row the row index of the site
     * @param col the column index of the site
     * @return whether the site (row, col) is full
     */
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return isOpen(row, col) && ufTop.connected(ufIndex(row, col), topIndex);
    }

    /**
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openSiteNum;
    }

    /**
     * @return whether the system percolate
     */
    public boolean percolates() {
        return uf.connected(topIndex, bottomIndex);
    }

    /**
     * Use for unit testing.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(0, 0);
        p.open(2, 0);
        boolean test1 = p.isFull(0, 0);
        boolean test2 = p.isOpen(2, 0) && !p.isFull(2, 0);
        boolean test3 = !p.percolates();
        for (int i = 0; i < 10; i++) {
            p.open(i, 2);
        }
        boolean test4 = p.percolates();
        boolean test5 = p.numberOfOpenSites() == 12;
        System.out.printf("%b, %b, %b, %b, %b",
                test1, test2, test3, test4, test5);
    }

}
