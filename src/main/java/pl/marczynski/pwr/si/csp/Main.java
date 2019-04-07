package pl.marczynski.pwr.si.csp;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
//        List<SolvingAlgorithm> solvingAlgorithms = Arrays.asList(new BacktrackingAlgorithm(), new ForwardCheckingAlgorithm());
//        List<Heuristics> heuristics = Arrays.asList(new FifoHeuristics());
//        String directory = "research_data";
////        List<String> fileNames = getFilesInDirectory(directory);
//        List<String> fileNames = Collections.singletonList("test_futo_4_0.txt");
//
//        List<SolutionCollection> allSolutions = new ArrayList<>();
//        for (String fileName : fileNames) {
//            for (SolvingAlgorithm solvingAlgorithm : solvingAlgorithms) {
//                for (Heuristics heuristic : heuristics) {
//                    SolutionFinder solutionFinder = new SolutionFinder(solvingAlgorithm, heuristic, directory + "/" + fileName);
//
//                    SolutionCollection solutionCollection = solutionFinder.findSolution();
//                    allSolutions.add(solutionCollection);
//                    System.out.println(solutionCollection);
//                }
//            }
//        }
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
