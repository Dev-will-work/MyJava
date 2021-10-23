package Dev_will_work.hse.concurrency_graphics;

class UnknownDirectionException extends Exception {}

/**
 * utility class generating unique identifications for objects
 */
class Gen {
    static int value = 0;
    static int value() {
        return value++;
    }
}

/**
 *this class represents a request from a person, <br>
 * which needs to change the floor quickly to the elevator
 */
public class Request {
    final int from;
    final String direction;
    final int id = Gen.value();

    int to;
    boolean started = false;


    /**
     *
     * @param from floor number, on which the person stays
     * @param direction direction to move the person
     * @throws WrongFloorException floor number lesser or equal 0
     * @throws UnknownDirectionException any direction besides up or down
     */
    Request(int from, String direction) throws
            WrongFloorException, UnknownDirectionException {
        if (from > 0) {
            this.from = from;
        } else {
            throw new WrongFloorException();
        }

        if (direction.equals("up") || direction.equals("down")) {
            this.direction = direction;
        } else {
            throw new UnknownDirectionException();
        }
    }

    /**
     * prints information about the calling request
     */
    void print() {
        System.out.print("from " + from + " " + direction);
        if (this.to != 0) System.out.print(" to " + to);
        System.out.print(" " + this.started);
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}