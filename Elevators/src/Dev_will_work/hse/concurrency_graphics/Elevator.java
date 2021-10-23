package Dev_will_work.hse.concurrency_graphics;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class WrongFloorException extends Exception {}
class WrongPeopleCountException extends Exception {}
class EmptyBuildingException extends Exception {}

/**
 * this class implements elevator model and <br>
 * state machine to determine its behaviour
 */
public class Elevator implements Runnable {
    final int maxPeopleCount;
    final Building link;
    List<Request> requests = new CopyOnWriteArrayList<Request>();
    final LinkedList<Request> for_removing = new LinkedList<Request>();

    int currentFloor;
    int peopleCount;
    boolean idle = true;
    boolean searchRequest = false;
    boolean took_person = false;
    int startedRequest = 0;
    boolean finishedRequest = false;
    Request current_request;

    /**
     *constructor method for the elevator
     * @param currentFloor floor number, on which the elevator starts its work
     * @param maxPeopleCount amount of requests that can be started <br>
     *                       at the moment
     * @param link reference on the whole building's values
     * @throws EmptyBuildingException if link equals null
     * @throws WrongFloorException if the starting floor is not exists
     * @throws WrongPeopleCountException if max people count lesser than 0
     */
    Elevator(int currentFloor, int maxPeopleCount, Building link) throws
            EmptyBuildingException, WrongFloorException,
            WrongPeopleCountException {
        if (link == null) {
            throw new EmptyBuildingException();
        } else {
            this.link = link;
        }

        if (currentFloor > 0 && currentFloor <= this.link.floors_count) {
            this.currentFloor = currentFloor;
        } else {
            throw new WrongFloorException();
        }

        if (maxPeopleCount > 0) {
            this.maxPeopleCount = maxPeopleCount;
        } else {
            throw new WrongPeopleCountException();
        }
    }

    /**
     * method, that runs instructions in concurrent mode
     */
    public void run() {
        this.update();
    }

    /**
     * gets current elevator state
     * @return string representation of current state
     */
    String getCurrentState() {
        final String state;
        if (this.took_person) {
            state = "took_person";
        } else if (this.idle) {
            state = "idle";
        } else if (this.finishedRequest) {
            state = "finishedRequest";
        } else if (this.searchRequest) {
            state = "searchRequest";
        } else if (this.startedRequest > 0) {
            state = "startedRequest";
        } else {
            System.out.println("EMPTY STATE!!!!!");
            System.out.println("Current floor: " + this.currentFloor);
            System.out.println("Current request: " + this.current_request);
            System.out.println("started count: " + this.startedRequest);
            System.out.println("max people count: " + this.maxPeopleCount);
            System.out.println("current people count: " + this.peopleCount);
            System.out.println("Requests: ");
            System.out.println(this.requests.size());
            for (Request r : this.requests) {
                r.print();
                System.out.println();
            }
            System.out.println("idle: " + this.idle);
            System.out.println("took: " + this.took_person);
            System.out.println("finished: " + this.finishedRequest);
            System.out.println("search: " + this.searchRequest);
            System.exit(155);
            state = "empty";
        }
        return state;
    }

    /**
     * utility function to check unique requests
     * @param req request to check in removing list
     * @return true if removing list contains request otherwise false
     */
    boolean isInRemoving(Request req) {
        for (Request r : this.for_removing) {
            if (r == req) return true;
        }
        return false;
    }

    /**
     * utility cast method for prints
     * @param b boolean, which needs to be cast
     * @return char '0' or '1', depending on the boolean value
     */
    char BooleanToChar(boolean b) {
        if (b) {
            return '1';
        } else {
            return '0';
        }
    }


    /**
     * prints elevator data and requests according to its state
     */
    void print() {
        final String state = getCurrentState();

        //start of elevator image
        System.out.print("[" + this.peopleCount + " из " +
                this.maxPeopleCount + " людей " + state);

        // important DEBUG!
        // prints collection of requests of the current elevator
//        for (Request r : this.requests) {
//            System.out.print(" (");
//            r.print();
//            System.out.print(")");
//        }

        // important DEBUG!
        //prints field of elevator's states like 0 0 2 0 1
//        System.out.print(" " + BooleanToChar(this.idle) + " " +
//                BooleanToChar(this.searchRequest) + " " +
//                BooleanToChar(this.took_person) + " " +
//                this.startedRequest + " " +
//                BooleanToChar(this.finishedRequest));

        //end of the elevator's image according to its state
        if (this.requests == null || this.requests.isEmpty() || this.idle
                || this.took_person || this.finishedRequest) {
            System.out.print("] ");
        } else if (this.searchRequest) {
            if (this.current_request != null) {
                if (this.currentFloor < this.current_request.from) {
                    System.out.print(" ↑" + "] ");
                } else if (this.currentFloor > this.current_request.from) {
                    System.out.print(" ↓" + "] ");
                } else {
                    System.out.print("] ");
                }
            }
        } else {
            if (this.current_request != null) {
                if (this.current_request.to == this.currentFloor) {
                    System.out.print("] ");
                } else if (this.current_request.direction.equals("up")) {
                    System.out.print(" ↑" + "] ");
                } else {
                    System.out.print(" ↓" + "] ");
                }
            }
        }
    }

    /**
     * implements actions on the idle state<br>
     * checks elevator requests, if there are one -><br>
     * if it starts from current floor then took_person state<br>
     * else searchRequest state and searches it
     */
    void idle() {
        //Если есть заявки
        if (!this.requests.isEmpty()) {
            //на этом этаже
            for (Request r : this.requests) {
                if (r.from == this.currentFloor) {
                    this.took_person = true;
                    break;
                }
            }
            //не на этом
            if (!this.took_person) {
                //nearest request to move at
                boolean created = false;
                if (this.current_request == null) {
                    for (Request r : this.requests) {
                        if (!created) this.current_request = r;
                        created = true;
                        if (Math.abs(r.from - this.currentFloor) <
                            Math.abs(this.current_request.from - this.currentFloor)) {
                            this.current_request = r;
                        }
                    }
                }
                this.searchRequest = true;
            }
            this.idle = false;
        }
    }

    /**
     * implements actions on the searchRequest state<br>
     * moves elevator to the nearest request<br>
     * and checks new requests on any floor<br>
     */
    void search() {
        if (this.current_request != null) {
            if (this.currentFloor < this.current_request.from) {
                this.currentFloor++;
            } else if (this.currentFloor > this.current_request.from) {
                this.currentFloor--;
            } else {
                this.searchRequest = false;
                this.took_person = true;
            }

            if (this.currentFloor != this.current_request.from) {
                for (Request r : this.requests) {
                    if (r.from == this.currentFloor) {
                        this.took_person = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * implements actions on the took_person state<br>
     * tries to set current request with the min distance <br>
     * from requests starting from this floor<br>
     * if set -> adds it to started and adds its person<br>
     * * also if elevator is not full, adds other requests from this floor
     */
    void took_person() {

        if (this.current_request != null &&
                this.currentFloor == this.current_request.from &&
                this.startedRequest == 0) {
            current_request = null;
        }

        boolean created = false;

        //выбираем кратчайшую заявку для выполнения
        if (this.current_request == null) {
            for (Request r : this.requests) {
                if (r.from == this.currentFloor) {
                    if (!created) {
                        this.current_request = r;
                    }
                    created = true;
                    if (Math.abs(r.from - r.to) <
                            Math.abs(this.current_request.from -
                                    this.current_request.to)) {
                        this.current_request = r;
                    }
                }
            }
        }
        if (created) {
            if (!this.current_request.started) {
                this.current_request.started = true;
                this.startedRequest++;
                this.peopleCount++;
            }
        }

        //Добавляем остальные заявки кроме выбранной с этого этажа
        for (Request r : this.requests) {
            if (r.from == this.currentFloor) {
                if (this.current_request != r) {
                    if (this.peopleCount < this.maxPeopleCount &&
                            !r.started) {
                        this.peopleCount++;
                        this.startedRequest++;
                        r.started = true;
                    }
                }
            }
        }

        this.took_person = false;
    }

    /**
     * implements actions on the startedRequest state<br>
     * moves elevator in the direction of chosen request<br>
     * else takes finishedRequest state if completed<br>
     * **before and after the movement:<br>
     * if elevator is not full, tries to add new requests and people
     */
    void started() {
        for (Request r : this.requests) {
            if (r.from == this.currentFloor && !r.started &&
                    this.peopleCount < this.maxPeopleCount) {
                this.took_person = true;
                return;
            }
        }

        if (this.current_request == null) {
            this.idle = true;
            return;
        }

        if (this.currentFloor != this.current_request.to) {
            if (this.current_request.direction.equals("up")) {
                this.currentFloor++;
            } else {
                this.currentFloor--;
            }
        } else {
            this.finishedRequest = true;
        }

        for (Request r : this.requests) {
            if (r.from == this.currentFloor && !r.started &&
                    this.peopleCount < this.maxPeopleCount) {
                this.took_person = true;
                return;
            }
        }
    }

    /**
     * implements actions on the finishedRequest state<br>
     * removes person, current request and chooses another request
     * if there are any<br>
     * else sets idle state
     */
    void finished() {

        for (Request r : this.for_removing) {
            this.peopleCount--;
            this.startedRequest--;
            this.requests.remove(r);
        }
        this.for_removing.clear();

        if (this.current_request != null && this.current_request.started &&
                this.current_request.to == this.currentFloor) {
            this.peopleCount--;
            this.startedRequest--;
            this.requests.remove(this.current_request);
            this.current_request = null;
        }
        this.finishedRequest = false;

        if (this.requests.size() > 0) {
            for (Request r : this.requests) {
                if (r.started) {
                    this.current_request = r;
                }
            }
            if (this.current_request == null) {
                this.idle = true;
            }
        } else {
            this.idle = true;
        }
    }

    /**
     * - implements the behaviour of elevator on the one step<br>
     * according to its states<br>
     * - also adds new request from all on each step and <br>
     * finishes concurrent started requests, if they are gone,<br>
     * besides the chosen one
     */
    void update() {
        //adding requests
//        for (Request r : this.link.all_requests) {
//            if (this.requests.size() < 6 && !r.started) {
//                try {
//                    this.addRequest(r);
//                } catch (WrongFloorException exc) {
//                    System.out.println("Very odd Request!");
//                }
//                this.for_removing.add(r);
//                break;
//            }
//        }

        //moves requests from shared to elevator until their size >= limit
        this.selectRequest(2);

//        //removing them from shared requests
//        for (Request r : this.for_removing) {
//            this.link.all_requests.remove(r);
//        }
//        this.for_removing.clear();

        //discarding people on this floor
        for (Request r : this.requests) {
            if (r != this.current_request &&
                    !this.finishedRequest && !this.idle && !this.took_person) {
                if (r.to == this.currentFloor && r.started &&
                        !this.isInRemoving(r)) {
                    this.for_removing.add(r);
                }
            }
        }

        if (this.for_removing.size() > 0 && !this.finishedRequest) {
            this.finishedRequest = true;
        } else if (this.requests.isEmpty()) {
            this.idle = true;
        } else if (this.finishedRequest) {
            this.finished();
        } else if (this.took_person) {
            this.took_person();
        } else if (this.idle) {
            this.idle();
        } else if (this.searchRequest) {
            this.search();
        } else if (this.startedRequest > 0) {
            this.started();
        }
    }

    /**
     * if no requests at all - return<br>
     * if requests less than limit -> find request with nearest start point, <br>
     * add it to elevator and remove from shared pool
     * @param limit number, that limits the amount of requests on the elevator
     */
    void selectRequest(int limit) {
        //searching on all requests to find
        // the most suitable request to add to elevator
        Request minReq = null;
        int min_dist = 10000000, min_path = 10000000;
        if (this.requests.size() < limit &&
                !this.link.all_requests.isEmpty()) {
            synchronized (this.link.all_requests) {
                for (Request r : this.link.all_requests) {
                    if (!r.started && r.from > 0 &&
                            r.from <= this.link.floors_count &&
                            Math.abs(r.from - this.currentFloor) < min_dist &&
                            Math.abs(r.from - r.to) < min_path) {
                        minReq = r;
                        min_path = Math.abs(r.from - r.to);
                        min_dist = Math.abs(r.from - this.currentFloor);
                    }
                }

                this.link.all_requests.remove(minReq);
            }
            try {
                this.addRequest(minReq);
            } catch (WrongFloorException exc) {
                System.out.println("Very odd Request!");
            }
        }
    }

    /**
     * sets to in request and adds it to calling elevator
     * @param r request to add
     * @throws WrongFloorException floor number lesser or equal 0
     */
    void addRequest(Request r) throws WrongFloorException {
        //lift is full
        if (this.peopleCount == this.maxPeopleCount || r == null) return;

        //setting the end floor
        if (r.direction.equals("up")) {
            if (r.from < this.link.floors_count && r.from > 0) {
                r.to = r.from + 1 + (int) (Math.round(Math.random() *
                        (this.link.floors_count - r.from - 1)));
            } else {
                throw new WrongFloorException();
            }
        } else {
            if (r.from > 1 && r.from <= this.link.floors_count) {
                r.to = 1 + (int)(Math.round(Math.random() * (r.from - 2)));
            } else {
                throw new WrongFloorException();
            }
        }

        if (r.to < 0 || r.to > this.link.floors_count) {
            throw new WrongFloorException();
        }

        this.requests.add(r);
    }
}