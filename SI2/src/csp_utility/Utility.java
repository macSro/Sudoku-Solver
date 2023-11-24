package csp_utility;

import java.io.*;
import java.util.ArrayList;

public class Utility {

    private final static int BOARDS = 46;
    private final static String PATH = "data/Sudoku.csv";

    public static ArrayList<String> readCSV(){
        ArrayList<String> boards = new ArrayList<>(BOARDS);
        try{
            File file = new File(PATH);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            String[] split;
            br.readLine();
            while((line = br.readLine()) != null){
                split = line.split(";");
                boards.add(split[2]);
            }
            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return boards;
    }

    public static void saveTXT(int[][] board, int index, String type, String method, String heuristicV, String heuristicD){
        try{
        	String pathPostfix = !type.equals("original") ? "_" + method : "";
            File file = new File("results/board" + index + "_" + type + pathPostfix + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("Sudoku board number " + index + " (" + type + ")");
            bw.newLine();
            if(!type.equals("original")) {
                bw.write("Method: " + method);
                bw.newLine();
                bw.write("Variable heuristic: " + heuristicV);
                bw.newLine();
                bw.write("Domain heuristic: " + heuristicD);
                bw.newLine();
            }
            bw.write("----------------------");
            bw.newLine();
            bw.newLine();

            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    bw.write(board[i][j] + " ");
                    if((j+1)%3==0 && j!=8){
                        bw.write("| ");
                    }
                }
                bw.newLine();
                if((i+1)%3==0 && i!=8){
                    bw.write("---------------------");
                    bw.newLine();
                }
            }
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void saveTXT(int index, String method, String heuristicV, String heuristicD, long timeFirst, int iterationsTilFirst, int turnbacksTillFirst, int iterations, int turnbacks, long duration, int solutions){
        try{
            File file = new File("results/board" + index + "_resultInfo_" + method + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("Sudoku board number " + index + " results information");
            bw.newLine();
            bw.write("Method: " + method);
            bw.newLine();
            bw.write("Variable heuristic: " + heuristicV);
            bw.newLine();
            bw.write("Domain heuristic: " + heuristicD);
            bw.newLine();
            bw.write("------------------------------------------");
            bw.newLine();
            bw.newLine();

            bw.write("Duration till first solution: " + timeFirst + "ms");
            bw.newLine();
            bw.write("Iterations till first solution: " + iterationsTilFirst);
            bw.newLine();
            bw.write("Turnbacks till first solution: " + turnbacksTillFirst);
            bw.newLine();
            bw.newLine();
            bw.write("Iterations in total: " + iterations);
            bw.newLine();
            bw.write("Turnbacks in total: " + turnbacks);
            bw.newLine();
            bw.write("Total duration: " + duration + "ms");
            bw.newLine();
            bw.write("Number of solutions found: " + solutions);
            bw.newLine();

            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void saveTXT(int index, String method, String heuristicV, String heuristicD, int iterations, int turnbacks, long duration, int solutions){
        try{
            File file = new File("results/board" + index + "_resultInfo_" + method + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("Sudoku board number " + index + " results information");
            bw.newLine();
            bw.write("Method: " + method);
            bw.newLine();
            bw.write("Variable heuristic: " + heuristicV);
            bw.newLine();
            bw.write("Domain heuristic: " + heuristicD);
            bw.newLine();
            bw.write("------------------------------------------");
            bw.newLine();
            bw.newLine();

            bw.write("Iterations in total: " + iterations);
            bw.newLine();
            bw.write("Turnbacks in total: " + turnbacks);
            bw.newLine();
            bw.write("Total duration: " + duration + "ms");
            bw.newLine();
            bw.write("Number of solutions found: " + solutions);
            bw.newLine();

            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean tryParseInt(char c){
        if(Character.getNumericValue(c) > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public static int[][] copyBoard(int[][] origin){
        int[][] copy = new int[origin.length][origin.length];
        for(int i=0; i<origin.length; i++){
            for(int j=0; j<origin.length; j++){
                copy[i][j] = origin[i][j];
            }
        }
        return copy;
    }

}
