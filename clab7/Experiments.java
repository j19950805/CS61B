import edu.princeton.cs.algs4.StdRandom;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Integer> bst = new BST<>();
        List<Integer> items = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            xValues.add(i);
            items.add(i);
        }
        for (int i = 0; i < 5000; i++) {
            int random =  StdRandom.uniform(items.size());
            bst.add(items.remove(random));
            yValues.add(bst.AverageDepth());
            y2Values.add(ExperimentHelper.optimalAverageDepth(i + 1));
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("number of items").yAxisTitle("Average depth").build();
        chart.addSeries("random Insertion BST", xValues, yValues);
        chart.addSeries("optimal BST", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        BST<Integer> bst = new BST<>();
        List<Integer> items = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            items.add(i);
        }
        for (int i = 0; i < 5000; i++) {
            int random = StdRandom.uniform(items.size());
            bst.add(items.remove(random));
        }
        double startingDepth = bst.AverageDepth();
        for (int i = 0; i < 495000; i++) {
            xValues.add(i);
            int randomKey = bst.getRandomKey();
            items.add(randomKey);
            bst.deleteTakingSuccessor(randomKey);
            int random = StdRandom.uniform(items.size());
            bst.add(items.remove(random));
            yValues.add(bst.AverageDepth());
            y2Values.add(startingDepth);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("number of operations").yAxisTitle("Average depth").build();
        chart.addSeries("random Insertion & deletion", xValues, yValues);
        chart.addSeries("Starting depth", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        BST<Integer> bst = new BST<>();
        List<Integer> items = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            items.add(i);
        }
        for (int i = 0; i < 5000; i++) {
            int random = StdRandom.uniform(items.size());
            bst.add(items.remove(random));
        }
        double startingDepth = bst.AverageDepth();
        for (int i = 0; i < 495000; i++) {
            xValues.add(i);
            int randomKey = bst.getRandomKey();
            items.add(randomKey);
            bst.deleteTakingRandom(randomKey);
            int random = StdRandom.uniform(items.size());
            bst.add(items.remove(random));
            yValues.add(bst.AverageDepth());
            y2Values.add(startingDepth);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("number of operations").yAxisTitle("Average depth").build();
        chart.addSeries("random Insertion & deletion", xValues, yValues);
        chart.addSeries("Starting depth", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment3();
    }
}
