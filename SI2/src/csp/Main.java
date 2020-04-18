package csp;

import csp_heuristics.*;
import csp_methods.SolutionMethod;
import csp_methods.SolutionMethodBacktracking;
import csp_methods.SolutionMethodForwardChecking;
import csp_utility.Utility;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
        hb1.setPadding(new Insets(20,0,0,0));
        Label lSudokuNumber = new Label("Enter sudoku number:");
        lSudokuNumber.setStyle("-fx-font: 14 arial;");
        TextField tfSudokuNumber = new TextField("1");
        tfSudokuNumber.setPrefWidth(30);
        hb1.getChildren().addAll(lSudokuNumber, tfSudokuNumber);

        HBox hb2 = new HBox(20);
        hb2.setPadding(new Insets(20,0,0,0));
        Label lHeuristicVariable = new Label("Choose variable heuristic:");
        lHeuristicVariable.setStyle("-fx-font: 14 arial;");
        ChoiceBox<String> cbHeuristicV = new ChoiceBox();
        cbHeuristicV.setPrefWidth(110);
        cbHeuristicV.getItems().addAll("Ordered", "Most constrained");
        cbHeuristicV.setValue("Ordered");
        hb2.getChildren().addAll(lHeuristicVariable, cbHeuristicV);

        HBox hb3 = new HBox(36);
        hb3.setPadding(new Insets(20,0,0,0));
        Label lHeuristicDomain = new Label("Choose value heuristic:");
        lHeuristicDomain.setStyle("-fx-font: 14 arial;");
        ChoiceBox<String> cbHeuristicD = new ChoiceBox();
        cbHeuristicD.setPrefWidth(110);
        cbHeuristicD.getItems().addAll("Ordered", "Random");
        cbHeuristicD.setValue("Ordered");
        hb3.getChildren().addAll(lHeuristicDomain, cbHeuristicD);

        HBox hb4 = new HBox(27);
        hb4.setPadding(new Insets(20,0,0,0));
        Label lMethod = new Label("Choose solution method:");
        lMethod.setStyle("-fx-font: 14 arial;");
        ChoiceBox<String> cbMethod = new ChoiceBox();
        cbMethod.setPrefWidth(110);
        cbMethod.getItems().addAll("Backtracking", "Forward checking");
        cbMethod.setValue("Backtracking");
        hb4.getChildren().addAll(lMethod, cbMethod);

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
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Wrong input!");
                    alert.setContentText("Please choose the number from [1,46].");
                    alert.showAndWait();
                }

            }
        });

        Label lResults = new Label("Show results:");
        lResults.setStyle("-fx-font: 14 arial; -fx-font-weight: bold;");
        lResults.setPadding(new Insets(30,0,0,0));

        Label lChosen = new Label("-");

        HBox hb5 = new HBox(20);

        ToggleGroup tg = new ToggleGroup();

        RadioButton r1 = new RadioButton("Backtracking");
        RadioButton r2 = new RadioButton("ForwardChecking");

        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);

        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton rb = (RadioButton) tg.getSelectedToggle();
                if(rb!=null){
                    String s = rb.getText();
                    lChosen.setText(s);
                }
            }
        });

        hb5.getChildren().addAll(r1, r2);

        Button bOriginal = new Button("Show original");
        bOriginal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File("results/board" + tfSudokuNumber.getText() + "_original_" + lChosen.getText() + ".txt"));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText("File doesn't exist! Please try again.");
                    alert.showAndWait();
                }
            }
        });

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
                    Desktop.getDesktop().open(new File("results/board" + tfSudokuNumber.getText() + "_solution" + tfSolutionNumber.getText() + "_" + lChosen.getText() + ".txt"));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText("File doesn't exist! Please try again.");
                    alert.showAndWait();
                }
            }
        });
        hb6.getChildren().addAll(lSolutionNumber, tfSolutionNumber, bShow);

        Button bResults = new Button("Show results");
        bResults.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File("results/board" + tfSudokuNumber.getText() + "_resultInfo_" + lChosen.getText() + ".txt"));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText("File doesn't exist! Please try again.");
                    alert.showAndWait();
                }
            }
        });

        vb.getChildren().addAll(tWelcome, hb1, hb2, hb3, hb4, bSolve, lResults, hb5, lChosen, bOriginal, hb6, bResults);

        primaryStage.setScene(new Scene(vb, 350, 590));
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
