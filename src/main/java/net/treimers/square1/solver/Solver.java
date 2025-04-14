package net.treimers.square1.solver;

import net.treimers.square1.model.*;
import java.util.*;

public class Solver {
	private static final Position GOAL = new Position();

	public MoveSequence solve(Position start) {
		PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
		Map<Position, Integer> visited = new HashMap<>();
		Node startNode = new Node(start, null, null, 0, heuristic(start));
		openList.add(startNode);
		int steps = 0;
		while (!openList.isEmpty()) {
			steps++;
			Node current = openList.poll();
			if (steps % 1000 == 0) {
				System.out.printf("Durchsuchte Knoten: %d, aktuelle Tiefe: %d, Position: %s%n", steps, current.g,
						current.position);
				System.out.printf("g: %d, f: %d%n", current.g, current.f);
			}
			if (current.position.equals(GOAL)) {
				return reconstructPath(current);
			}
			if (visited.containsKey(current.position) && visited.get(current.position) <= current.g)
				continue;
			visited.put(current.position, current.g);
			for (Move move : generatePossibleMoves()) {
				try {
					Position neighbor = current.position.move(move);
					int g = current.g + 1;
					int h = heuristic(neighbor);
					Node neighborNode = new Node(neighbor, current, move, g, g + h);
					openList.add(neighborNode);
				} catch (Exception ignored) {
					// illegale Züge einfach ignorieren
				}
			}
		}
		return null; // keine Lösung gefunden
	}

	private MoveSequence reconstructPath(Node node) {
		List<Move> moves = new ArrayList<>();
		while (node.prev != null && node.move != null) {
			moves.add(node.move);
			node = node.prev;
		}
		Collections.reverse(moves);
		MoveSequence sequence = new MoveSequence();
		sequence.getMoves().addAll(moves);
		return sequence;
	}

	private int heuristic(Position position) {
		// einfache Heuristik: Anzahl der falschen Zeichen im Stringvergleich
		String current = position.toString();
		String goal = Position.SOLVED_POSITION_STRING;
		int diff = 0;
		for (int i = 0; i < Math.min(current.length(), goal.length()); i++) {
			if (current.charAt(i) != goal.charAt(i))
				diff++;
		}
		diff += Math.abs(goal.length() - current.length());
		return diff;
	}

	private List<Move> generatePossibleMoves() {
		List<Move> moves = new ArrayList<>();
		for (int top = -6; top <= 6; top++) {
			for (int bottom = -6; bottom <= 6; bottom++) {
				moves.add(new Move(top, bottom, true)); // mit Schnitt
				moves.add(new Move(top, bottom, false)); // ohne Schnitt
			}
		}
		// Auch reine Twists ("/" oder "-")
		moves.add(new Move(0, 0, true));
		moves.add(new Move(0, 0, false));
		return moves;
	}

	private static class Node {
		Position position;
		Node prev;
		Move move;
		int g; // cost from start
		int f; // estimated cost (g + h)

		Node(Position position, Node prev, Move move, int g, int f) {
			this.position = position;
			this.prev = prev;
			this.move = move;
			this.g = g;
			this.f = f;
		}
	}

	public static void main(String[] args) {
		Position scrambled = new Position("D6G8E45H7B2C3FA1-"); // "6FA17G8HB25EC3D4-"); //A1B25E6F8HC3D47G/");
		Solver solver = new Solver();
		MoveSequence solution = solver.solve(scrambled);
		System.out.println("Lösung: " + solution);
	}
}
