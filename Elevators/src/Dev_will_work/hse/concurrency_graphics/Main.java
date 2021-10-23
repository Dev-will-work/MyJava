package Dev_will_work.hse.concurrency_graphics;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * this class implements work with emulated system of a building <br>
 * with floors and elevators on it <br>
 * which move in multithreaded mode
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

//        // create building
//        Building b = null;
//        try {
//            b = new Building(4);
//        } catch (WrongFloorCountException ex) {
//            ex.printStackTrace();
//        }
//
//        //create and add elevators
//        Elevator e = null;
//        Elevator e1 = null;
//        try {
//            e = new Elevator(1, 5, b);
//            e1 = new Elevator(2, 4, b);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        b.addElevator(e);
//        b.addElevator(e1);
//
//        //create and add requests (instead in the cycle)
////        Request r = null;
////        Request r1 = null;
////        try {
////            r = new Request(3, "up");
////            r1  = new Request(3, "up");
////        } catch(Exception ex) {
////            System.out.println("Wrong starting floor");
////        }
////        b.all_requests.add(r);
////        b.all_requests.add(r1);
//
//       Executor executor = Executors.newFixedThreadPool(b.elevators.size());
//
//       //just for iteration count
//        int counter = 1;
//        while (true) {
//            b.print(17, 1);
//
//            for (int i = 0; i < b.elevators.size(); i++) {
//                executor.execute(b.elevators.get(i));
//            }
//
//            //infinite request stream
//            b.requestStream();
//            timer(1);
//            clearScreen(25);
//            counter++;
//        }
    }

    static void timer(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception except) {
            System.out.println("Interrupted");
        }
    }

    static void clearScreen(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }

    public void cycle(int counter, Building b, Executor executor, Controller controller) {
        while (true) {
            b.print(17, 1);

            for (int i = 0; i < b.elevators.size(); i++) {
                executor.execute(b.elevators.get(i));
            }

            //infinite request stream
            b.requestStream();
            timer(1);
            clearScreen(25);
            for (int i = 0; i < b.elevators.size();i++) {
                controller.setStarts(b.elevators.get(i).currentFloor, i);
            }
            counter++;
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader f = new FXMLLoader(getClass().getResource("EntryScene.fxml"));
        FXMLLoader f1 = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent q = null;
        try {
            q = f1.load();

        } catch(IOException ex) {
            System.out.println("Bad load");
        }
        Parent newroot = f.load();
        //get controller
        Controller controller = f1.getController();
        EntryController cont = f.getController();
        //cont.cont_link = controller;
        cont.fx_link = f1;
        cont.rt = q;

        primaryStage.setTitle("Set configuration");
        Scene s = new Scene(newroot, 700, 500);
        s.getStylesheets().add(0, "styles/my-button.css");
        primaryStage.setScene(s);
        primaryStage.show();

        // create building
        Building b = null;
        try {
            b = new Building(4);
        } catch (WrongFloorCountException ex) {
            ex.printStackTrace();
        }

        //create and add elevators
        Elevator e = null;
        Elevator e1 = null;
        try {
            e = new Elevator(1, 5, b);
            e1 = new Elevator(2, 4, b);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        b.addElevator(e);
        b.addElevator(e1);

        //create and add requests (instead in the cycle)
//        Request r = null;
//        Request r1 = null;
//        try {
//            r = new Request(3, "up");
//            r1  = new Request(3, "up");
//        } catch(Exception ex) {
//            System.out.println("Wrong starting floor");
//        }
//        b.all_requests.add(r);
//        b.all_requests.add(r1);

        Executor executor = Executors.newFixedThreadPool(b.elevators.size());

        //just for iteration count
        int counter = 1;

        Building finalB = b;
        new Thread(()->cycle(counter, finalB, executor, controller)).start();

    }
}
