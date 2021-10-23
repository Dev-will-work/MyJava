package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Controller {
    boolean is_update_set = false;
    private int[] starts;
    private int[] max_peoples;

    @FXML
    private Button btn;

    @FXML
    private VBox house;

    @FXML
    private VBox elevators;

    @FXML
    private VBox strings;

    @FXML
    private VBox values;

    @FXML
    private VBox weight_values;

    @FXML
    private VBox weight_labels;

    @FXML
    private void click(ActionEvent event) {
        btn.setText("You've clicked!");
    }

    @FXML
    private void exit(ActionEvent event) {
        ObservableList<Window> ze = Stage.getWindows();
        Stage x = (Stage) ze.stream().findFirst().get();
        x.close();
    }

    @FXML
    private void update_elevators(ActionEvent event) {
        if (!is_update_set) {
            is_update_set = true;
            for (Node n : this.elevators.getChildren()) {

            }
        }
    }

    public void set_ready(int floors_count, int elevators_count, int[] starts, int[] max_peoples) {
        ObservableList<Window> ze = Stage.getWindows();
        Stage x = (Stage) ze.stream().findFirst().get();
        for (int i = 0; i < floors_count; i++) {
            Rectangle r = new Rectangle(249, 131);
            r.setFill(Color.DODGERBLUE);
            r.setStrokeWidth(1);
            r.setStroke(Color.BLACK);
            AnchorPane a = new AnchorPane(r);
            a.setPrefWidth(249);
            a.setPrefHeight(114);
            this.house.getChildren().add(a);
        }
        for (int i = 0; i < floors_count; i++) {
            AnchorPane a = new AnchorPane();
            a.setPrefWidth(149);
            a.setPrefHeight(130);
            this.elevators.getChildren().add(a);
        }

        int counter = 1;
        int arr_counter = 0;
        for (Node a : this.elevators.getChildren()) {
            Rectangle r = new Rectangle(60, 77);
            r.setFill(Color.DODGERBLUE);
            r.setStrokeWidth(1);
            r.setStroke(Color.BLACK);
            AnchorPane.setTopAnchor(r, 50.0);
            if (arr_counter < starts.length && counter == starts[arr_counter]) {
                ((AnchorPane)a).getChildren().add(r);
                arr_counter++;
            }
            counter++;
        }

        for (int i = 0; i < elevators_count;i++) {
            Label l = new Label("Лифт №" + (i+1));
            Label v = new Label(String.valueOf(starts[i]));
            Label lw = new Label("Лифт №" + (i+1));
            Label vw = new Label(String.valueOf(max_peoples[i]));
            AnchorPane a = new AnchorPane(l);
            AnchorPane b = new AnchorPane(v);
            AnchorPane weighted_a = new AnchorPane(lw);
            AnchorPane weighted_b = new AnchorPane(vw);
            AnchorPane.clearConstraints(l);
            AnchorPane.clearConstraints(v);
            AnchorPane.clearConstraints(lw);
            AnchorPane.clearConstraints(vw);
            this.strings.getChildren().add(a);
            this.values.getChildren().add(b);
            this.weight_labels.getChildren().add(weighted_a);
            this.weight_values.getChildren().add(weighted_b);
        }
        //this.weight_values.getChildren().clear();

        x.show();
    }
}
