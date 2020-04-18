package csp_heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeuristicDomainOrdered extends HeuristicDomain {
    @Override
    public ArrayList<Integer> getDomain(int x, int y, int[][] board) {
        ArrayList<Integer> domain = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));

        for(int col=0; col<board.length; col++) domain.remove(Integer.valueOf(board[x][col]));

        for(int row=0; row<board.length; row++) domain.remove(Integer.valueOf(board[row][y]));

        int row_start = x - x%3;
        int col_start = y - y%3;
        for(int i=row_start; i<row_start+3; i++) {
            for (int j=col_start; j<col_start+3; j++) {
                domain.remove(Integer.valueOf(board[i][j]));
            }
        }

        Collections.sort(domain);
        return domain;
    }

}
