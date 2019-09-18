public class BubbleGrid {
    private int[][] grid;
    private int colNum;
    private int rowNum;
    private UnionFind uf;
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        rowNum = grid.length;
        colNum = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int l = darts.length;
        int[][] resumeBubbles = new int[l][];
        for (int i = 0; i < l; i++) {
            if (hasBubble(darts[i][0], darts[i][1])) {
                grid[darts[i][0]][darts[i][1]] = 0;
                resumeBubbles[l - i - 1] = darts[i];
            }
        }

        uf = new UnionFind(rowNum * colNum + 1);
        for (int c = 0; c < colNum; c++) {
            if (hasBubble(0, c)) {
                uf.union(0, unionIndex(0, c));
            }
        }

        for (int r = 1; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                connectBubbles(r, c);
            }
        }

        int[] result = new int[l];
        int nextBubblesNum = uf.sizeOf(0) - 1;
        int bubblesNum;
        for (int i = 0; i < l; i++) {
            if (resumeBubbles[i] == null) {
                continue;
            }
            grid[resumeBubbles[i][0]][resumeBubbles[i][1]] = 1;
            connectBubbles(resumeBubbles[i][0], resumeBubbles[i][1]);
            bubblesNum = uf.sizeOf(0) - 1;
            result[l - i - 1] = bubblesNum - nextBubblesNum - 1;
            nextBubblesNum = bubblesNum;
        }
        return result;
    }

    private void connectBubbles(int row, int col) {
        if (!hasBubble(row, col)) {
            return;
        }

        for (int[] d: directions) {
            int dRow = row + d[0];
            int dCol = col + d[1];
            if (dRow < 0 || dCol < 0 || dRow >= rowNum || dCol >= colNum) {
                continue;
            }
            if (hasBubble(dRow, dCol)) {
                uf.union(unionIndex(row, col), unionIndex(dRow, dCol));
            }
        }
    }

    private boolean hasBubble(int row, int col) {
        return grid[row][col] == 1;
    }

    private int unionIndex(int row, int col) {
        return row * colNum + col + 1;
    }
}
