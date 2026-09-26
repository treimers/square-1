package net.treimers.square1;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Square1Shapes {
    private static final int TOTAL_UNITS = 12; // 360° / 30°
    private static final char CORNER = 'C';    // 60° = 2 units
    private static final char EDGE = 'E';      // 30° = 1 unit

    public static void main(String[] args) {
        Map<Integer, List<String>> shapesByCorners = new LinkedHashMap<>();

        for (int corners = 2; corners <= 6; corners++) {
            shapesByCorners.put(corners, generateLayerShapes(corners));
        }

        int total = 0;
        for (int topCorners = 2; topCorners <= 6; topCorners++) {
            int bottomCorners = 8 - topCorners;
            List<String> topShapes = shapesByCorners.get(topCorners);
            List<String> bottomShapes = shapesByCorners.get(bottomCorners);
            int count = topShapes.size() * bottomShapes.size();
            total += count;
        }

        System.out.println("Layer shapes per corner count (rotation-invariant):");
        for (Map.Entry<Integer, List<String>> entry : shapesByCorners.entrySet()) {
            int corners = entry.getKey();
            int edges = TOTAL_UNITS - 2 * corners;
            System.out.println("  corners=" + corners + ", edges=" + edges + " -> " + entry.getValue().size());
        }

        System.out.println();
        System.out.println("All Square-1 shapes (top/bottom, rotation-invariant):");
        int index = 1;
        for (int topCorners = 2; topCorners <= 6; topCorners++) {
            int bottomCorners = 8 - topCorners;
            int topEdges = TOTAL_UNITS - 2 * topCorners;
            int bottomEdges = TOTAL_UNITS - 2 * bottomCorners;

            List<String> topShapes = shapesByCorners.get(topCorners);
            List<String> bottomShapes = shapesByCorners.get(bottomCorners);

            for (String top : topShapes) {
                for (String bottom : bottomShapes) {
                    System.out.println(index++
                            + ". Top(" + topCorners + "C/" + topEdges + "E): " + top
                            + " | Bottom(" + bottomCorners + "C/" + bottomEdges + "E): " + bottom);
                }
            }
        }

        System.out.println();
        System.out.println("Total shapes: " + total);
    }

    private static List<String> generateLayerShapes(int corners) {
        int edges = TOTAL_UNITS - 2 * corners;
        int length = corners + edges;
        Set<String> unique = new LinkedHashSet<>();
        backtrack(0, corners, edges, new char[length], unique);
        return new ArrayList<>(unique);
    }

    private static void backtrack(int idx, int cornersLeft, int edgesLeft, char[] seq, Set<String> unique) {
        if (idx == seq.length) {
            String s = new String(seq);
            unique.add(canonicalRotation(s));
            return;
        }
        if (cornersLeft > 0) {
            seq[idx] = CORNER;
            backtrack(idx + 1, cornersLeft - 1, edgesLeft, seq, unique);
        }
        if (edgesLeft > 0) {
            seq[idx] = EDGE;
            backtrack(idx + 1, cornersLeft, edgesLeft - 1, seq, unique);
        }
    }

    private static String canonicalRotation(String s) {
        String best = s;
        int n = s.length();
        for (int i = 1; i < n; i++) {
            String rotated = s.substring(i) + s.substring(0, i);
            if (rotated.compareTo(best) < 0) {
                best = rotated;
            }
        }
        return best;
    }
}
