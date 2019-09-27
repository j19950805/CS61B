import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight START times are >= END times.
 * If a flight STARTs at the same time as a flight's END time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private static final Comparator<Flight> START_COMPARATOR = (f1, f2) -> f1.startTime - f2.startTime;
    private static final Comparator<Flight> END_COMPARATOR = (f1, f2) -> f1.endTime - f2.endTime;

    private ArrayList<Flight> flights;

    public FlightSolver(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public int solve() {
        PriorityQueue<Flight> startTimePQ = new PriorityQueue<>(START_COMPARATOR);
        PriorityQueue<Flight> endTimePQ = new PriorityQueue<>(END_COMPARATOR);
        for (Flight flight: flights) {
            startTimePQ.add(flight);
            endTimePQ.add(flight);
        }
        int maxNum = 0;
        int tally = 0;

        while (!startTimePQ.isEmpty()) {
            int startTime = startTimePQ.peek().startTime();
            int endTime = endTimePQ.peek().endTime();

            if (startTime <= endTime) {
                Flight f = startTimePQ.remove();
                tally += f.passengers();
            } else {
                Flight f = endTimePQ.remove();
                tally -= f.passengers();
            }

            maxNum = Math.max(tally, maxNum);
        }
        return maxNum;
    }


}
