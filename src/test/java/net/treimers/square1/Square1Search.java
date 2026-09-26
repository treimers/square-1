package net.treimers.square1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Square1Search {
    private static final int TOTAL_UNITS = 12; // 360° / 30°
    private static final char CORNER = 'C';    // 60° = 2 units

    public static void main(String[] args) {
        String targetTop = "CCCCCC";
        String targetBottom = normalizeBottomString("CEEEECEEEE");

        List<String> sequence = findSequence(targetTop, targetBottom);
        if (sequence == null) {
            System.out.println("No sequence found.");
            return;
        }

        System.out.println("Sequence (" + sequence.size() + " moves):");
        for (String move : sequence) {
            System.out.print(move);
        }
        System.out.println();
        System.out.println();
        printPositionsAlongSequence(sequence);

        if (args.length > 0 && !args[0].isBlank()) {
            System.out.println();
            System.out.println("Positions for provided sequence:");
            printPositionsAlongSequence(parseMovesString(args[0]));
        }
    }

    private static List<String> findSequence(String targetTop, String targetBottom) {
        State start = new State(stringToList("CECECECE"), stringToList(normalizeBottomString("ECECECEC")));
        String startKey = key(start);

        Map<String, Prev> prevMap = new HashMap<>();
        Deque<State> queue = new ArrayDeque<>();
        prevMap.put(startKey, new Prev(null, null));
        queue.add(start);

        while (!queue.isEmpty()) {
            State current = queue.removeFirst();
            if (isTarget(current, targetTop, targetBottom)) {
                return buildSequence(prevMap, key(current));
            }

            for (int a = -5; a <= 6; a++) {
                for (int b = -5; b <= 6; b++) {
                    State next = move(current, a, b);
                    if (next == null) {
                        continue;
                    }
                    String nextKey = key(next);
                    if (prevMap.containsKey(nextKey)) {
                        continue;
                    }
                    prevMap.put(nextKey, new Prev(key(current), moveString(a, b)));
                    queue.addLast(next);
                }
            }
        }

        return null;
    }

    private static boolean isTarget(State state, String targetTop, String targetBottom) {
        return listToString(state.top).equals(targetTop) && listToString(state.bottom).equals(targetBottom);
    }

    private static List<String> buildSequence(Map<String, Prev> prevMap, String endKey) {
        List<String> moves = new ArrayList<>();
        String key = endKey;
        while (true) {
            Prev prev = prevMap.get(key);
            if (prev == null || prev.move == null) {
                break;
            }
            moves.add(0, prev.move);
            key = prev.parentKey;
        }
        return moves;
    }

    private static State move(State state, int aSigned, int bSigned) {
        int a = normalizeToUnits(aSigned);
        int b = normalizeToUnits(bSigned);

        List<Character> topRotated = rotateByUnits(state.top, a);
        if (topRotated == null) {
            return null;
        }
        List<Character> bottomRotated = rotateByUnits(state.bottom, b);
        if (bottomRotated == null) {
            return null;
        }

        if (!hasBoundaryAt(topRotated, 6) || !hasBoundaryAt(bottomRotated, 6)) {
            return null;
        }

        List<Character>[] topSplit = splitAt(topRotated, 6);
        List<Character>[] bottomSplit = splitAt(bottomRotated, 6);
        if (topSplit == null || bottomSplit == null) {
            return null;
        }

        List<Character> newTop = new ArrayList<>(bottomSplit[0]);
        newTop.addAll(topSplit[1]);
        List<Character> newBottom = new ArrayList<>(topSplit[0]);
        newBottom.addAll(bottomSplit[1]);

        return new State(newTop, newBottom);
    }

    private static int normalizeToUnits(int signed) {
        int units = signed % TOTAL_UNITS;
        if (units < 0) {
            units += TOTAL_UNITS;
        }
        return units;
    }

    private static String moveString(int a, int b) {
        // Output notation: clockwise from top (top layer), clockwise from bottom (bottom layer).
        int aSigned = toCanonicalSigned(normalizeToUnits(a));
        int bSigned = toCanonicalSigned(normalizeToUnits(b));
        int outA = aSigned;   // top: internal matches clockwise from top
        int outB = -bSigned;  // bottom: invert to match clockwise from bottom
        return outA + "," + outB + "/";
    }

    private static int toCanonicalSigned(int units) {
        return units <= 6 ? units : units - TOTAL_UNITS;
    }

    private static String normalizeBottomString(String s) {
        // User convention: bottom counted clockwise from bottom.
        // Internal order is clockwise from top, so mirror the order.
        return reverseKeepingFirst(s);
    }

    private static String reverseKeepingFirst(String s) {
        if (s.length() <= 1) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        sb.append(s.charAt(0));
        for (int i = s.length() - 1; i >= 1; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    private static String denormalizeBottomString(String s) {
        return reverseKeepingFirst(s);
    }

    private static void printPositionsAlongSequence(List<String> sequence) {
        State state = new State(stringToList("CECECECE"), stringToList(normalizeBottomString("ECECECEC")));
        System.out.println("Start: Top=" + listToString(state.top)
                + " | Bottom=" + denormalizeBottomString(listToString(state.bottom)));
        int step = 1;
        for (String move : sequence) {
            int[] parsed = parseMove(move);
            if (parsed == null) {
                System.out.println("Could not parse move: " + move);
                return;
            }
            int outA = parsed[0];
            int outB = parsed[1];
            state = move(state, outA, -outB);
            if (state == null) {
                System.out.println("Illegal move at step " + step + ": " + move);
                return;
            }
            System.out.println("Step " + step + " " + move + " -> Top=" + listToString(state.top)
                    + " | Bottom=" + denormalizeBottomString(listToString(state.bottom)));
            step++;
        }
    }

    private static int[] parseMove(String move) {
        String trimmed = move.trim();
        if (!trimmed.endsWith("/")) {
            return null;
        }
        String core = trimmed.substring(0, trimmed.length() - 1);
        int comma = core.indexOf(',');
        if (comma == -1) {
            return null;
        }
        try {
            int a = Integer.parseInt(core.substring(0, comma));
            int b = Integer.parseInt(core.substring(comma + 1));
            return new int[] { a, b };
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static List<String> parseMovesString(String moves) {
        List<String> list = new ArrayList<>();
        String trimmed = moves.trim();
        if (trimmed.isEmpty()) {
            return list;
        }
        String[] parts = trimmed.split("/");
        for (String part : parts) {
            String p = part.trim();
            if (!p.isEmpty()) {
                list.add(p + "/");
            }
        }
        return list;
    }

    private static List<Character> rotateByUnits(List<Character> layer, int units) {
        if (units == 0) {
            return new ArrayList<>(layer);
        }
        int index = boundaryIndexAt(layer, units);
        if (index == -1) {
            return null;
        }
        List<Character> rotated = new ArrayList<>();
        rotated.addAll(layer.subList(index, layer.size()));
        rotated.addAll(layer.subList(0, index));
        return rotated;
    }

    private static boolean hasBoundaryAt(List<Character> layer, int units) {
        return boundaryIndexAt(layer, units) != -1;
    }

    private static int boundaryIndexAt(List<Character> layer, int units) {
        if (units == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < layer.size(); i++) {
            sum += pieceSize(layer.get(i));
            if (sum == units) {
                return i + 1;
            }
        }
        return -1;
    }

    private static List<Character>[] splitAt(List<Character> layer, int units) {
        int index = boundaryIndexAt(layer, units);
        if (index == -1) {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<Character>[] parts = new List[2];
        parts[0] = new ArrayList<>(layer.subList(0, index));
        parts[1] = new ArrayList<>(layer.subList(index, layer.size()));
        return parts;
    }

    private static int pieceSize(char piece) {
        return piece == CORNER ? 2 : 1;
    }

    private static String key(State state) {
        return listToString(state.top) + "|" + listToString(state.bottom);
    }

    private static List<Character> stringToList(String s) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }
        return list;
    }

    private static String listToString(List<Character> list) {
        StringBuilder sb = new StringBuilder();
        for (Character c : list) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static class State {
        private final List<Character> top;
        private final List<Character> bottom;

        private State(List<Character> top, List<Character> bottom) {
            this.top = top;
            this.bottom = bottom;
        }
    }

    private static class Prev {
        private final String parentKey;
        private final String move;

        private Prev(String parentKey, String move) {
            this.parentKey = parentKey;
            this.move = move;
        }
    }
}
