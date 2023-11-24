package csp;

import csp_heuristics.*;
import csp_methods.SolutionMethod;
import csp_methods.SolutionMethodBacktracking;
import csp_methods.SolutionMethodForwardChecking;
import csp_utility.Utility;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Constraint Solving Problem");

        VBox vb = new VBox(15);
        vb.setPadding(new Insets(15,15,15,15));
        Text tWelcome = new Text("Welcome to Sudoku Solver!");
        tWelcome.setStyle("-fx-font: 18 arial; -fx-font-weight: bold;");

        HBox hb1 = new HBox(20);
        Label lSudokuNumber = new Label("Enter sudoku number:");
        lSudokuNumber.setStyle("-fx-font: 14 arial;");
        TextField tfSudokuNumber = new TextField("1");
        tfSudokuNumber.setPrefWidth(30);
        hb1.getChildren().addAll(lSudokuNumber, tfSudokuNumber);
        hb1.setAlignment(Pos.CENTER_LEFT);

        HBox hb2 = new HBox(20);
        Label lHeuristicVariable = new Label("Choose variable heuristic:");
        lHeuristicVariable.setStyle("-fx-font: 14 arial;");
        ChoiceBox<String> cbHeuristicV = new ChoiceBox<>();
        cbHeuristicV.setPrefWidth(110);
        cbHeuristicV.getItems().addAll("Ordered", "Most constrained");
        cbHeuristicV.setValue("Ordered");
        hb2.getChildren().addAll(lHeuristicVariable, cbHeuristicV);
        hb2.setAlignment(Pos.CENTER_LEFT);

        HBox hb3 = new HBox(36);
        Label lHeuristicDomain = new Label("Choose value heuristic:");
        lHeuristicDomain.setStyle("-fx-font: 14 arial;");
        ChoiceBox<String> cbHeuristicD = new ChoiceBox<>();
        cbHeuristicD.setPrefWidth(110);
        cbHeuristicD.getItems().addAll("Ordered", "Random");
        cbHeuristicD.setValue("Ordered");
        hb3.getChildren().addAll(lHeuristicDomain, cbHeuristicD);
        hb3.setAlignment(Pos.CENTER_LEFT);

        HBox hb4 = new HBox(27);
        Label lMethod = new Label("Choose solution method:");
        lMethod.setStyle("-fx-font: 14 arial;");
        ChoiceBox<String> cbMethod = new ChoiceBox<>();
        cbMethod.setPrefWidth(110);
        cbMethod.getItems().addAll("Backtracking", "Forward checking");
        cbMethod.setValue("Backtracking");
        hb4.getChildren().addAll(lMethod, cbMethod);
        hb4.setAlignment(Pos.CENTER_LEFT);

        Button bSolve = new Button("Solve");
        bSolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    if(Integer.parseInt(tfSudokuNumber.getText()) < 1 || Integer.parseInt(tfSudokuNumber.getText()) > 46){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Input Error");
                        alert.setHeaderText("Sudoku doesn't exist!");
                        alert.setContentText("Please choose the number from [1,46].");
                        alert.showAndWait();
                    }
                    solve(Integer.parseInt(tfSudokuNumber.getText()), cbMethod.getValue(), cbHeuristicV.getValue(), cbHeuristicD.getValue());
                }
                catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Wrong input!");
                    alert.setContentText("Please choose the number from [1,46].");
                    alert.showAndWait();
                }

            }
        });

        Label lResults = new Label("Results");
        lResults.setStyle("-fx-font: 14 arial; -fx-font-weight: bold;");
        lResults.setPadding(new Insets(30,0,0,0));
        
        HBox hb5 = new HBox(20);

        ToggleGroup tg = new ToggleGroup();

        RadioButton r1 = new RadioButton("Backtracking");
        RadioButton r2 = new RadioButton("ForwardChecking");

        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        
        tg.selectToggle(r1);

        hb5.getChildren().addAll(r1, r2);

        HBox hb6 = new HBox(20);
        Label lSolutionNumber = new Label("Enter solution number:");
        lSolutionNumber.setStyle("-fx-font: 14 arial;");
        TextField tfSolutionNumber = new TextField("1");
        tfSolutionNumber.setPrefWidth(30);
        Button bShow = new Button("Show");
        bShow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File("results/board" + tfSudokuNumber.getText() + "_solution" + tfSolutionNumber.getText() + "_" + ((RadioButton)tg.getSelectedToggle()).getText() + ".txt"));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText("File doesn't exist! Please try again.");
                    alert.showAndWait();
                }
            }
        });
        hb6.getChildren().addAll(lSolutionNumber, tfSolutionNumber, bShow);
        hb6.setAlignment(Pos.CENTER_LEFT);

        Button bResults = new Button("Show results");
        bResults.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File("results/board" + tfSudokuNumber.getText() + "_resultInfo_" + ((RadioButton)tg.getSelectedToggle()).getText() + ".txt"));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText("File doesn't exist! Please try again.");
                    alert.showAndWait();
                }
            }
        });
        
        Button bOriginal = new Button("Show original");
        bOriginal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File("results/board" + tfSudokuNumber.getText() + "_original.txt"));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText("File doesn't exist! Please try again.");
                    alert.showAndWait();
                }
            }
        });

        vb.getChildren().addAll(tWelcome, hb1, hb2, hb3, hb4, bSolve, lResults, hb5, hb6, bResults, bOriginal);

        primaryStage.setScene(new Scene(vb, 350, 500));
        primaryStage.show();
    }

    private void solve(int boardNumber, String method, String heuristicV, String heuristicD) {
        HeuristicVariable heuristicVariable = null;
        if(heuristicV == "Ordered") {
            heuristicVariable = new HeuristicVariableOrdered();
        }
        else if(heuristicV == "Most constrained"){
            heuristicVariable = new HeuristicVariableMostConstrained();
        }

        HeuristicDomain heuristicDomain = null;
        if(heuristicD == "Ordered") {
            heuristicDomain = new HeuristicDomainOrdered();
        }
        else if(heuristicD == "Random"){
            heuristicDomain = new HeuristicDomainRandom();
        }

        SolutionMethod solutionMethod = null;
        if(method == "Backtracking"){
            solutionMethod = new SolutionMethodBacktracking(heuristicVariable, heuristicDomain);
        }
        else if(method == "Forward checking"){
            solutionMethod = new SolutionMethodForwardChecking(heuristicVariable, heuristicDomain);
            method = "ForwardChecking";
        }

        CSP csp = new CSP(solutionMethod);
        csp.selectBoard(boardNumber);
        Utility.saveTXT(csp.getOriginalBoard(), csp.getSolvedIndex(), "original", method, heuristicV, heuristicD);
        csp.solve();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
