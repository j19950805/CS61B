package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private int numStatesExplored;
    private double timeSpent;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        HashMap<Vertex, Double> distTo = new HashMap<>();
        distTo.put(start, 0.0);
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        HashSet<Vertex> visited = new HashSet<>();
        numStatesExplored = 0;
        while(!pq.getSmallest().equals(end) && !pq.isEmpty() && sw.elapsedTime() < timeout) {
            numStatesExplored += 1;
            Vertex v = pq.removeSmallest();
            visited.add(v);
            for (WeightedEdge<Vertex> nwe: input.neighbors(v)) {
                if (!visited.contains(nwe.to())) {
                    relax(nwe, pq, distTo, edgeTo, input.estimatedDistanceToGoal(nwe.to(), end));
                }
            }
        }
        if (pq.isEmpty()) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (sw.elapsedTime() > timeout) {
            outcome = SolverOutcome.TIMEOUT;
        } else {
            outcome = SolverOutcome.SOLVED;
            solutionWeight = distTo.get(end);
            solution = new LinkedList<>();
            solution.add(end);
            Vertex v = end;
            while (edgeTo.get(v) != null) {
                v = edgeTo.get(v);
                solution.addFirst(v);
            }
            timeSpent = sw.elapsedTime();
        }
    }

    private void relax(WeightedEdge<Vertex> we, ArrayHeapMinPQ<Vertex> pq, HashMap<Vertex, Double> distTo,
                       HashMap<Vertex, Vertex> edgeTo, double estimatedDistToEnd) {
        Vertex to = we.to();
        Vertex from = we.from();
        double newDistTo = distTo.get(from) + we.weight();
        if (!pq.contains(to)) {
            distTo.put(to, newDistTo);
            edgeTo.put(to, from);
            pq.add(to, newDistTo + estimatedDistToEnd);
        } else {
            if (newDistTo < distTo.get(to)) {
                distTo.replace(to, newDistTo);
                edgeTo.replace(to, from);
                pq.changePriority(to, newDistTo + estimatedDistToEnd);
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
