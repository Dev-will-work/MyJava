package Dev_will_work.hse.concurrency_graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

class WrongFloorCountException extends Exception {}

/**
 * this class represents the building with floors and elevators
 */
public class Building {
    final int floors_count;
    final ArrayList<Elevator> elevators = new ArrayList<Elevator>();

    final List<Request> all_requests = new CopyOnWriteArrayList<Request>();

    /**
     *  constructs building with given number of floors
     * @param floors_count positive number, representing count of floors
     * @throws WrongFloorCountException if count of floors < 0
     */
    Building(int floors_count) throws WrongFloorCountException {
        if (floors_count > 0) {
            this.floors_count = floors_count;
        } else {
            throw new WrongFloorCountException();
        }
    }

    /**
     *adds given elevator to calling building
     * @param e elevator to add in this building
     */
    void addElevator(Elevator e) {
        this.elevators.add(e);
    }

    /**
     *
     * @param r request to add in the shared request pool
     * @throws WrongFloorException floor number doesn't exist
     */
    void addRequest(Request r) throws WrongFloorException {
        if (r.from <= this.floors_count) {
            this.all_requests.add(r);
        } else {
            throw new WrongFloorException();
        }
    }

    /**
     * wrapper for print without parameters()<br>
     * paints the whole building in the pseudographics <br>
     * according to some parameters
     */
    void print() {
        print(17, 1);
    }

    /**
     * wrapper for print with exact width in symbols()<br>
     * paints the whole building in the pseudographics<br>
     * according to some parameters
     */
    void print(int width) {
        print(width, 1);
    }

    /**
     * paints the whole building in the pseudographics<br>
     * according to some parameters
     * @param width amount of characters<br>
     *             representing building's width
     * @param floorHeightScale 2x amount of characters <br>
     *                         representing height of each floor
     */
    void print(int width, int floorHeightScale) {
        if (width <= 0) {
            width = 17;
        }

        if (floorHeightScale < 0) {
            floorHeightScale = 1;
        }

        final int roofShift = (width - 2) / 2;
        final int widthWithoutWalls = width - 2;
        final int floorNameShift = (width - 9) / 2;

        if (width % 2 == 1) {
            System.out.println(" ".repeat(roofShift + 1) + "*");
            for (int i = 0; i < roofShift; i++) {
                System.out.println(" ".repeat(roofShift - i) +
                        "/" + " ".repeat(2 * i + 1) + "\\");
            }
        } else {
            for (int i = 0; i < roofShift; i++) {
                System.out.println(" ".repeat(roofShift - i) +
                        "/" + " ".repeat(2 * i) + "\\");
            }
        }

        for (int i = 0; i < this.floors_count; i++) {
            final int floorNumber = this.floors_count - i;
            System.out.println("|" + "_".repeat(widthWithoutWalls) + "|");
            for (int j = 0; j < floorHeightScale;j++) {
                System.out.println("|" + " ".repeat(widthWithoutWalls) + "|");
            }

            if (width % 2 == 1) {
                System.out.print("|" + " ".repeat(floorNameShift) +
                        "floor " + floorNumber +
                        " ".repeat(floorNameShift) + "|");
            } else {
                System.out.print("|" + " ".repeat(floorNameShift) +
                        "floor " + floorNumber +
                        " ".repeat(floorNameShift + 1) + "|");
            }

            for (Elevator elevator : this.elevators) {
                if (elevator.currentFloor == floorNumber) {
                    elevator.print();
                }
            }

            System.out.println();

            for (int j = 0; j < floorHeightScale;j++) {
                System.out.println("|" + " ".repeat(widthWithoutWalls) + "|");
            }
            System.out.println("|" + "_".repeat(widthWithoutWalls) + "|");
        }

        for (int i = 0; i < this.elevators.size();i++) {
            System.out.print("Current request №" + (i + 1) + ": ");
            if (this.elevators.get(i).current_request == null) {
                System.out.print("null");
            } else {
                System.out.print(this.elevators.get(i).current_request);
                //this.elevators.get(i).current_request.print();
            }
            System.out.println();
        }
    }

    /**
     * creates new request for the elevator according<br>
     * on the count of floors in building
     * @return created request
     */
    Request generateRequest() {
        final int from = (int)(1 + Math.random() * this.floors_count);
        final String direction;
        if (from == this.floors_count) {
            direction = "down";
        } else if (from == 1) {
            direction = "up";
        } else {
            int coin = (int) (1 + Math.random() * 2);
            if (coin % 2 == 0) direction = "up";
            else direction = "down";
        }
        Request req = null;
        try {
            req = new Request(from, direction);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return req;
    }

    /**
     * utility function to check unique requests
     * @param req request to check in shared list
     * @return true if shared list contains request otherwise false
     */
    boolean isRequestIn(Request req) {
        for (Request r : this.all_requests) {
            if (r == req) return true;
        }
        return false;
    }

    /**
     * creates new request in shared pool every time it is called
     */
    void requestStream() {
        Random rand = new Random();
        if (this.all_requests.size() < 10) {
            for (int i = 0; i < this.elevators.size(); i++) {
                try {
                    if (rand.nextInt(this.elevators.size()) == 0) {
                        this.addRequest(this.generateRequest());
                    }

                } catch (WrongFloorException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

//        System.out.println("        *");
//        System.out.println("       / \\");
//        System.out.println("      /   \\");
//        System.out.println("     /     \\");
//        System.out.println("    /       \\");
//        System.out.println("   /         \\");
//        System.out.println("  /           \\");
//        System.out.println(" /_____________\\");
//            System.out.println("|_______________|");
//            System.out.println("|               |");
//            System.out.println("|    floor 5    |");
//            System.out.println("|               |");
//            System.out.println("|_______________|");
