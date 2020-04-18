package csp_methods;

import csp_heuristics.*;
import csp_utility.Utility;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class SolutionMethodForwardChecking extends SolutionMethod{

    private HeuristicVariable heuristicVariable;
    private HeuristicDomain heuristicDomain;

    private int solvedCount=1;
    private int solvedIndex;
    private int[][] solvedBoard;
    private int iterations=0;
    private int turnbacks=0;
    private int iterationsTillFirst;
    private int turnbacksTillFirst;

    private LocalTime timeStart;
    private LocalTime timeFinish;
    private LocalTime timeFirstSolution;

    public SolutionMethodForwardChecking(HeuristicVariable heuristicVariable, HeuristicDomain heuristicDomain){
        this.heuristicVariable = heuristicVariable;
        this.heuristicDomain = heuristicDomain;

    }

    @Override
    public void solve(int[][] board, int solvedIndex) {
        this.solvedBoard = board;
        this.solvedIndex = solvedIndex;

        this.timeStart = LocalTime.now();
        solveSudoku();
        this.timeFinish = LocalTime.now();
        Duration duration2 = Duration.between(timeStart, timeFinish);
        System.out.println("Start-Finish: " + duration2.toMillis());
        System.out.println("Solutions: " + (solvedCount - 1));
        System.out.println("Iterations: " + iterations);
        System.out.println("Turnbacks: " + turnbacks);
        try {
            Duration duration1 = Duration.between(timeStart, timeFirstSolution);
            System.out.println("Start-First: " + duration1.toMillis());
            System.out.println("Iterations-First: " + iterationsTillFirst);
            System.out.println("Turnbacks-First: " + turnbacksTillFirst);
            Utility.saveTXT(solvedIndex, getMethodName(), getHeuristicName(heuristicVariable), getHeuristicName(heuristicDomain), duration1.toMillis(), iterationsTillFirst, turnbacksTillFirst, iterations, turnbacks, duration2.toMillis(), (solvedCount - 1));
        }
        catch(Exception e){
            Utility.saveTXT(solvedIndex, getMethodName(), getHeuristicName(heuristicVariable), getHeuristicName(heuristicDomain), iterations, turnbacks, duration2.toMillis(), (solvedCount-1));
        }
    }
    public boolean solveSudoku(){
        //VARIABLE HEURISTIC
        int[] coords = heuristicVariable.getNextCoords(solvedBoard);
        int x=coords[0];
        int y=coords[1];
        if (x < 0){
            if(solvedCount == 1){
                timeFirstSolution = LocalTime.now();
                iterationsTillFirst = iterations;
                turnbacksTillFirst = turnbacks;
            }
            Utility.saveTXT(solvedBoard, solvedIndex, "solution" + solvedCount, getMethodName(), getHeuristicName(heuristicVariable), getHeuristicName(heuristicDomain));
            solvedCount++;
            return false;//aby znaleźć wszystkie
        }

        //DOMAIN HEURISTIC
        ArrayList<Integer> domain = heuristicDomain.getDomain(x,y,solvedBoard);
        for(int value : domain)
        {
            solvedBoard[x][y] = value;
            iterations++;
            if(checkBoardValid()) {
                solveSudoku();
            }
            solvedBoard[x][y] = 0;

        }
        turnbacks++;
        return false;
    }

    private boolean checkBoardValid(){
        for(int i=0 ; i<solvedBoard.length; i++){
            for(int j=0 ; j<solvedBoard.length; j++){
                if(solvedBoard[i][j] == 0 && heuristicDomain.getDomain(i,j,solvedBoard).size() == 0) return false;
            }
        }
        return true;
    }

    private String getMethodName(){
        return "ForwardChecking";
    }

    private String getHeuristicName(HeuristicDomain heuristicDomain){
        if(heuristicDomain.getClass() == HeuristicDomainOrdered.class){
            return "Ordered";
        }
        else if(heuristicDomain.getClass() == HeuristicDomainRandom.class){
            return "Random";
        }
        return "-";
    }

    private String getHeuristicName(HeuristicVariable heuristicVariable){
        if(heuristicVariable.getClass() == HeuristicVariableOrdered.class){
            return "Ordered";
        }
        else if(heuristicVariable.getClass() == HeuristicVariableMostConstrained.class){
            return "MostConstrained";
        }
        return "-";
    }
}
