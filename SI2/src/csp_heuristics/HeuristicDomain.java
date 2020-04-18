package csp_heuristics;

import java.util.ArrayList;

public abstract class HeuristicDomain {
    public abstract ArrayList<Integer> getDomain(int x, int y, int[][] board);
}
