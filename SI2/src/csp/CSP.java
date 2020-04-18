package csp;

import csp_methods.SolutionMethod;
import csp_utility.Utility;

import java.util.ArrayList;

public class CSP {
    private ArrayList<String> boards;
    private int[][] originalBoard;
    private int solvedIndex;
    private int[][] solvedBoard;

    private SolutionMethod solutionMethod;


    public CSP(SolutionMethod solutionMethod){
        boards = Utility.readCSV();
        this.solutionMethod = solutionMethod;
    }

    public void selectBoard(int index){
        originalBoard = new int[9][9];
        char[] cells = boards.get(index-1).toCharArray();
        int x=0;
        int y=0;
        for(char c : cells){
            if(Utility.tryParseInt(c)){
                originalBoard[x][y] = Character.getNumericValue(c);
            }
            y++;
            if(y>8){
                y=0;
                x++;
            }
        }
        solvedIndex = index;
        solvedBoard = Utility.copyBoard(originalBoard);
    }

    public void solve(){
        solutionMethod.solve(solvedBoard, solvedIndex);
    }

    public int[][] getOriginalBoard() {
        return originalBoard;
    }

    public void setOriginalBoard(int[][] originalBoard) {
        this.originalBoard = originalBoard;
    }

    public int getSolvedIndex() {
        return solvedIndex;
    }

    public void setSolvedIndex(int solvedIndex) {
        this.solvedIndex = solvedIndex;
    }

    public int[][] getSolvedBoard() {
        return solvedBoard;
    }

    public void setSolvedBoard(int[][] solvedBoard) {
        this.solvedBoard = solvedBoard;
    }
}
