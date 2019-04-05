package pl.marczynski.pwr.si.csp;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.Solution;
import pl.marczynski.pwr.si.csp.game.SolutionCollection;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String DATA_PATH = "./src/main/resources/";

    public static void main(String[] args) {
        List<SolvingAlgorithm> solvingAlgorithms = Arrays.asList(new BacktrackingAlgorithm(), new ForwardCheckingAlgorithm());
        List<Heuristics> heuristics = Arrays.asList(new FifoHeuristics(), new IntensityHeuristics());
        String directory = "test_data";
        List<String> fileNames = getFilesInDirectory(directory);
//        List<String> fileNames = Collections.singletonList("futoshiki_4_0.txt");
        for (String fileName : fileNames) {
            for (SolvingAlgorithm solvingAlgorithm : solvingAlgorithms) {
                for (Heuristics heuristic : heuristics) {
                    SolutionFinder solutionFinder = new SolutionFinder(solvingAlgorithm, heuristic, directory + "/" + fileName);
                    System.out.println(solvingAlgorithm.getClass().getSimpleName() + ": " + heuristic.getClass().getSimpleName() + ": " + fileName + ": ");

                    long startTime = System.currentTimeMillis();
                    SolutionCollection solutionCollection = solutionFinder.findSolution();
                    long endTime = System.currentTimeMillis();
                    List<Solution> solutions = solutionCollection.getSolutions();
                    System.out.println(String.format("%30s", "Total number of solutions: ") + solutions.size());
                    System.out.println(String.format("%30s","Moves for first: ") + solutions.get(0).getMoveCount());
                    System.out.println(String.format("%30s","Total moves: ") + solutionCollection.getCurrrentMoveCount());

                    double elapsedTime = ((double) (solutions.get(0).getEndTime() - startTime)) / 1000;
                    System.out.println(String.format("%30s","Time for first: ") + elapsedTime + " s");
                    System.out.println(String.format("%30s","Total end time: ") + ((double) (endTime - startTime)) / 1000 + " s");
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
