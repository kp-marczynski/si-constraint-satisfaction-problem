package pl.marczynski.pwr.si.csp;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
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
        String directory = args[0];
        List<String> fileNames = Collections.singletonList(args[1]);
        List<SolutionCollection> solutionsForFiles = findSolutionsForFiles(directory, fileNames);
        System.out.println(getCsvString(solutionsForFiles));
    }

    public static String getCsvString(List<SolutionCollection> solutions) {
        StringBuilder builder = new StringBuilder();
        builder.append("File name,Solving Algorithm,Heuristics,Total number of solutions,Moves for first,Total moves,Time for first,Total end time\n");
        for (SolutionCollection solutionCollection : solutions) {
            builder.append(solutionCollection.getFileName()).append(",")
                    .append(solutionCollection.getAlgorithmName()).append(",")
                    .append(solutionCollection.getHeuristicsName()).append(",")
                    .append(solutionCollection.getNumberOfSolutions()).append(",")
                    .append(solutionCollection.getMoveCountForFirst()).append(",")
                    .append(solutionCollection.getCurrentMoveCount()).append(",")
                    .append(solutionCollection.getTimeForFirst()).append(",")
                    .append(solutionCollection.getTotalTime()).append("\n");
        }
        return builder.toString();
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

    static List<SolutionCollection> findSolutionsForFiles(String directory, List<String> fileNames) {
        List<SolvingAlgorithm> solvingAlgorithms = Arrays.asList(new BacktrackingAlgorithm(), new ForwardCheckingAlgorithm());
        List<Heuristics> heuristics = Arrays.asList(new FifoHeuristics(), new IntensityHeuristics());

        List<SolutionCollection> allSolutions = new ArrayList<>();
        for (String fileName : fileNames) {
            for (SolvingAlgorithm solvingAlgorithm : solvingAlgorithms) {
                for (Heuristics heuristic : heuristics) {
                    SolutionFinder solutionFinder = new SolutionFinder(solvingAlgorithm, heuristic, directory + "/" + fileName);

                    SolutionCollection solutionCollection = solutionFinder.findSolution();
                    allSolutions.add(solutionCollection);
                    System.out.println(solutionCollection);
                }
            }
        }
        return allSolutions;
    }

    static List<String> getFilesInDirectory(String directory) {
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
