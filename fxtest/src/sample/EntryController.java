package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.event.InputMethodEvent;
import java.io.IOException;
import java.util.Optional;

public class EntryController {
    private boolean is_elevators_set = false;
    @FXML
    private Button btn;
    @FXML
    private TextField tf;
    @FXML
    private TextField flc;
    @FXML
    private VBox elvs;
    @FXML
    private VBox elvboxes;

    @FXML
    private void click(ActionEvent event) {
        btn.setText("You've clicked!");
    }

    @FXML
    private void update(Event event) {
        if (!this.is_elevators_set && !tf.getText().isEmpty()) {
            int data = Integer.parseInt(tf.getText());
            this.is_elevators_set = true;

            this.tf.setEditable(false);
            for (int i = 0; i < data; i++) {
                TextField one = new TextField();
                one.setPromptText((i + 1) + " elevator: Enter current floor");
                TextField two = new TextField();
                two.setPromptText((i + 1) + " elevator: Max people count");
                VBox elv = new VBox(one, two);
                elvboxes.getChildren().add(elv);
            }

            ObservableList ze = Stage.getWindows();
            Stage x = (Stage) ze.stream().findFirst().get();
            x.show();
        }
    }

    @FXML
    private void collectData(Event event) {
        //empty check
        int empty_fields = 0;

        if (this.tf.getText().isEmpty()) {
            empty_fields++;
        }

        if (this.flc.getText().isEmpty()) {
            empty_fields++;
        }

        ObservableList<Node> z = this.elvboxes.getChildren();
        for (Node n : z) {
            for (Node t : ((VBox) n).getChildren()) {
                if (((TextField)t).getText().isEmpty()) {
                    empty_fields++;
                }
            }
        }

        Parent root = null;
        FXMLLoader f = null;
        if (empty_fields == 0) {
            //get stage
            ObservableList<Window> ze = Stage.getWindows();
            Stage x = (Stage) ze.stream().findFirst().get();
            try {
                f = new FXMLLoader(getClass().getResource("Main.fxml"));
                root = f.load();
            } catch (IOException ex) {
                System.out.println("Bad FXML");
            }

            int floor_count = Integer.parseInt(this.flc.getText());
            int elev_count = Integer.parseInt(this.tf.getText());
            int[] starts = new int[elev_count];
            int[] maxes = new int[elev_count];
            int counter = 0;
            for (Node n : z) {
                for (Node t : ((VBox) n).getChildren()) {
                    if (counter % 2 == 0) {
                        starts[counter / 2] = Integer.parseInt(((TextField)t).getText());
                    } else {
                        maxes[(counter - 1) / 2] = Integer.parseInt(((TextField)t).getText());
                    }
                    counter++;
                }
            }

            Controller controller = f.getController();
            controller.set_ready(floor_count, elev_count, starts, maxes);
            if (root != null) {
                Scene s = new Scene(root, 800, 600);
                s.getStylesheets().add(0, "styles/my.css");
                x.setScene(s);
                x.setTitle("Lift control page");
            }
            x.show();
        }
    }
}
