package csp_heuristics;

public class HeuristicVariableOrdered extends HeuristicVariable {
    @Override
    public int[] getNextCoords(int[][] board) {
        int[] result = {-1,-1};
        boolean found = false;
        for (int i = 0; i < board.length; i++)
        {
            for (var j = 0; j < board.length; j++)
            {
                if (board[i][j] != 0) continue;

                result[0] = i;
                result[1] = j;

                found = true;
                break;
            }

            if (found) break;
        }
        return result;
    }
}
