package Dev_will_work.hse.concurrency_graphics;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Controller {
    private int[] starts;
    private int[] max_peoples;
    int elevators_count;

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
    private void exit(ActionEvent event) {
        ObservableList<Window> ze = Stage.getWindows();
        Stage x = (Stage) ze.stream().findFirst().get();
        x.close();
    }

    public void setStarts(int starts, int ind) {
        if (this.starts != null) {
            this.starts[ind] = starts;
        }
    }

    @FXML
    private void update_elevators(Event event) {
        ObservableList<Window> ze = Stage.getWindows();
        Stage x = (Stage) ze.stream().findFirst().get();
        int counter = 1;
        boolean now_visible = false;
        for (Node n : this.elevators.getChildren()) {
            now_visible = false;
            if (this.starts != null) {
                for (int i : this.starts) {
                    if (counter == i) {
                        ((AnchorPane) n).getChildren().get(0).setVisible(true);
                        now_visible = true;
                    }
                }
                if (!now_visible) {
                    ((AnchorPane) n).getChildren().get(0).setVisible(false);
                }
                counter++;
            }
        }
        this.strings.getChildren().clear();
        this.values.getChildren().clear();
        this.weight_values.getChildren().clear();
        this.weight_labels.getChildren().clear();
        for (int i = 0; i < this.elevators_count;i++) {
            Label l = new Label("Лифт №" + (i+1));
            Label v = new Label(String.valueOf(this.starts[i]));
            Label lw = new Label("Лифт №" + (i+1));
            Label vw = new Label(String.valueOf(this.max_peoples[i]));
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
        x.show();
    }

    public void set_ready(int floors_count, int elevators_count, int[] starts, int[] max_peoples) {
        this.starts = starts;
        this.max_peoples = max_peoples;
        this.elevators_count = elevators_count;
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
            Rectangle r = new Rectangle(60, 77);
            r.setFill(Color.DODGERBLUE);
            r.setStrokeWidth(1);
            r.setStroke(Color.BLACK);
            r.setVisible(false);
            AnchorPane.setTopAnchor(r, 50.0);
            AnchorPane a = new AnchorPane(r);
            a.setPrefWidth(149);
            a.setPrefHeight(130);
            this.elevators.getChildren().add(a);
        }

        int counter = 1;
        for (Node a : this.elevators.getChildren()) {
            for (int i = 0; i < starts.length;i++) {
                if (counter == starts[i]) {
                    ((AnchorPane)a).getChildren().get(0).setVisible(true);
                }
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
        //new Thread(()->this.update_elevators()).start();
        x.show();
    }
}
