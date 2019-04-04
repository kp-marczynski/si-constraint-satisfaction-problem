package pl.marczynski.pwr.si.csp;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.SolutionFinder;
import pl.marczynski.pwr.si.csp.game.heuristics.FifoHeuristics;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.BacktrackingAlgorithm;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SolutionFinder solutionFinder = new SolutionFinder(new BacktrackingAlgorithm(), new FifoHeuristics(), "research_data/test_futo_4_0");
        Board solution = solutionFinder.findSolution();
        System.out.println(solution);

    }

    public static void manualRun() {
        Board board = Board.initializeFromFile("skyscrapper_4_0");
        System.out.println(board);
        Scanner scanner = new Scanner(System.in);
        while (board.validate() && !board.isGameOver()) {
            System.out.print("Next params: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            int val = scanner.nextInt();

            board.makeMove(new FieldId(row, col), val);
            System.out.println(board);
        }
    }
}
