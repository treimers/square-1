package net.treimers.square1;

import java.util.ArrayList;
import java.util.List;

public class SquareOnePositions {
    public static void main(String[] args) {
        List<String> positions = generateAllPositions();
        for (String position : positions) {
            System.out.println(position);
        }
        System.out.println("Total positions: " + positions.size());
    }

    public static List<String> generateAllPositions() {
        List<String> positions = new ArrayList<>();

        // Wir generieren alle möglichen Aufteilungen der Ecken und Kanten
        // e_o: Ecken oben, k_o: Kanten oben
        for (int e_o = 0; e_o <= 8; e_o++) {
            for (int k_o = 0; k_o <= 8; k_o++) {
                int e_u = 8 - e_o; // Ecken unten
                int k_u = 8 - k_o; // Kanten unten

                // Die obere und untere Ebene müssen jeweils eine gerade Anzahl an Stücken haben
                if ((e_o + k_o) % 2 == 0 && (e_u + k_u) % 2 == 0) {
                    // Die minimale Anzahl an Stücken pro Ebene ist 4, die maximale 8
                    if (e_o + k_o >= 4 && e_u + k_u >= 4) {
                        // Generiere alle Permutationen für die obere und untere Ebene
                        generatePermutations("", e_o, k_o, e_u, k_u, positions);
                    }
                }
            }
        }

        return positions;
    }

    private static void generatePermutations(String prefix, int e_o, int k_o, int e_u, int k_u, List<String> positions) {
        if (e_o == 0 && k_o == 0) {
            // Obere Ebene ist vollständig, jetzt die untere Ebene generieren
            generateLowerLayer(prefix, e_u, k_u, positions);
            return;
        }

        if (e_o > 0) {
            generatePermutations(prefix + "E", e_o - 1, k_o, e_u, k_u, positions);
        }
        if (k_o > 0) {
            generatePermutations(prefix + "K", e_o, k_o - 1, e_u, k_u, positions);
        }
    }

    private static void generateLowerLayer(String upperLayer, int e_u, int k_u, List<String> positions) {
        generateLowerPermutations(upperLayer, "", e_u, k_u, positions);
    }

    private static void generateLowerPermutations(String upperLayer, String lowerPrefix, int e_u, int k_u, List<String> positions) {
        if (e_u == 0 && k_u == 0) {
            positions.add(upperLayer + " (oben) und " + lowerPrefix + " (unten)");
            return;
        }

        if (e_u > 0) {
            generateLowerPermutations(upperLayer, lowerPrefix + "E", e_u - 1, k_u, positions);
        }
        if (k_u > 0) {
            generateLowerPermutations(upperLayer, lowerPrefix + "K", e_u, k_u - 1, positions);
        }
    }
}
