package pl.marczynski.pwr.si.csp;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.SolutionFinder;
import pl.marczynski.pwr.si.csp.game.heuristics.FifoHeuristics;
import pl.marczynski.pwr.si.csp.game.heuristics.Heuristics;
import pl.marczynski.pwr.si.csp.game.heuristics.IntensityHeuristics;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.BacktrackingAlgorithm;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.ForwardCheckingAlgoritm;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.SolvingAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<SolvingAlgorithm> solvingAlgorithms = Arrays.asList(new BacktrackingAlgorithm(), new ForwardCheckingAlgoritm());
        List<Heuristics> heuristics = Arrays.asList(new FifoHeuristics(), new IntensityHeuristics());
        List<String> futoshikiFiles = new ArrayList<>();
        List<String> skyscraperFiles = new ArrayList<>();
        for (int i = 4; i <= 5; ++i) {
            for (int j = 0; j <= 4; ++j) {
                futoshikiFiles.add("test_data/futoshiki_" + i + "_" + j);
                skyscraperFiles.add("test_data/skyscrapper_" + i + "_" + j);
            }
        }
        List<String> fileNames = new ArrayList<>(futoshikiFiles);
        fileNames.addAll(skyscraperFiles);
        for (String fileName : fileNames) {
            for (SolvingAlgorithm solvingAlgorithm : solvingAlgorithms) {
                for (Heuristics heuristic : heuristics) {
                    SolutionFinder solutionFinder = new SolutionFinder(solvingAlgorithm, heuristic, fileName);
                    System.out.print(solvingAlgorithm.getClass().getSimpleName() + ": " + heuristic.getClass().getSimpleName() + ": " + fileName + ": ");

                    long startTime = System.currentTimeMillis();
                    Board solution = solutionFinder.findSolution();
                    long endTime = System.currentTimeMillis();

                    long elapsedTime = endTime - startTime;
                    System.out.println(elapsedTime + " ms");
                    System.out.println(solution);
                }
            }
        }
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
