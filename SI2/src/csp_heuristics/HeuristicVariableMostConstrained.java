package csp_heuristics;

import java.util.ArrayList;
import java.util.Collections;

public class HeuristicVariableMostConstrained extends HeuristicVariable{

/*    @Override
    public int[] getNextCoords(int[][] board) {
        int[] result = {-1,-1};
        ArrayList<int[]> allEmpty = new ArrayList<>(board.length * board.length);
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                if (board[i][j] == 0){
                    allEmpty.add(new int[]{i,j});
                }
            }
        }
        System.out.println(allEmpty.size());
        if(allEmpty.isEmpty()) {
            return result;
        }
        else {
            Collections.shuffle(allEmpty);
            return allEmpty.get(0);
        }
    }*/

    @Override
    public int[] getNextCoords(int[][] board) {
        int[] result = new int[2];
        HeuristicDomain heuristicDomain = new HeuristicDomainOrdered();
        int minX = -1;
        int minY = -1;
        int minDomainSize = Integer.MAX_VALUE;

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                if (board[i][j] == 0){
                    int currentSize = heuristicDomain.getDomain(i, j, board).size();
                    if(currentSize<minDomainSize){
                        minDomainSize=currentSize;
                        minX = i;
                        minY = j;
                    }
                }
            }
        }
        result[0] = minX;
        result[1] = minY;
        return result;
    }
}
