package pl.marczynski.pwr.si.csp;

import javafx.util.Pair;
import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.SolutionFinder;
import pl.marczynski.pwr.si.csp.game.heuristics.FifoHeuristics;
import pl.marczynski.pwr.si.csp.game.heuristics.Heuristics;
import pl.marczynski.pwr.si.csp.game.heuristics.IntensityHeuristics;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.BacktrackingAlgorithm;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.ForwardCheckingAlgorithm;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.SolvingAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String DATA_PATH = "./src/main/resources/";

    public static void main(String[] args) {
        List<SolvingAlgorithm> solvingAlgorithms = Arrays.asList(new BacktrackingAlgorithm(), new ForwardCheckingAlgorithm());
        List<Heuristics> heuristics = Arrays.asList(new FifoHeuristics(), new IntensityHeuristics());
        String directory = "test_data";
        List<String> fileNames = getFilesInDirectory(directory);
        for (String fileName : fileNames) {
            for (SolvingAlgorithm solvingAlgorithm : solvingAlgorithms) {
                for (Heuristics heuristic : heuristics) {
                    SolutionFinder solutionFinder = new SolutionFinder(solvingAlgorithm, heuristic, directory + "/" + fileName);
                    System.out.println(solvingAlgorithm.getClass().getSimpleName() + ": " + heuristic.getClass().getSimpleName() + ": " + fileName + ": ");

                    long startTime = System.currentTimeMillis();
                    Pair<Board, Integer> solution = solutionFinder.findSolution();
                    long endTime = System.currentTimeMillis();

                    long elapsedTime = endTime - startTime;
                    System.out.println("Time: " + elapsedTime + " ms");
                    System.out.println("Moves: " + solution.getValue());
                    System.out.println(solution.getKey());
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

    private static List<String> getFilesInDirectory(String directory) {
        List<String> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(DATA_PATH + "/" + directory))) {

            result = walk.filter(Files::isRegularFile).map(Path::getFileName)
                    .map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
