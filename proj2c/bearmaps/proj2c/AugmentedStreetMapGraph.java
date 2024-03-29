package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.TrieSet61B;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.lab9.MyTrieSet;
import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Jennifer Chen
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private TrieSet61B namesTrie;
    private Map<Point, Node> pointNodeMap;
    private Map<String, List<Node>> namesMap;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        pointNodeMap = new HashMap<>();
        namesTrie = new MyTrieSet();
        namesMap = new HashMap<>();
        for (Node n: nodes) {
            if (!neighbors(n.id()).isEmpty()) {
                pointNodeMap.put(new Point(n.lon(), n.lat()), n);
            }
            if (n.name() != null) {
                namesTrie.add(cleanString(n.name()));
                namesMap.computeIfAbsent(cleanString(n.name()), k -> new ArrayList<>()).add(n);
            }
        }

    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        WeirdPointSet wps = new WeirdPointSet(new ArrayList<>(pointNodeMap.keySet()));
        return pointNodeMap.get(wps.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        for (String cleanName : namesTrie.keysWithPrefix(cleanString(prefix))) {
            results.add(namesMap.get(cleanName).get(0).name());
        }
        return results;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> result = new ArrayList<>();
        for(Node n: namesMap.get(cleanString(locationName))) {
            Map<String, Object> location = new HashMap<>();
            location.put("lat", n.lat());
            location.put("lon", n.lon());
            location.put("name", n.name());
            location.put("id", n.id());
            result.add(location);
        }
        return result;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {

        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
