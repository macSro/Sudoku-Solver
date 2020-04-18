package csp_methods;

import csp_heuristics.HeuristicDomain;
import csp_heuristics.HeuristicVariable;

public abstract class SolutionMethod {
    public abstract void solve(int[][] board, int solvedIndex);
}
