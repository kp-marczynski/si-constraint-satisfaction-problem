package pl.marczynski.pwr.si.csp;

import org.junit.Test;
import pl.marczynski.pwr.si.csp.game.SolutionCollection;
import pl.marczynski.pwr.si.csp.game.SolutionFinder;
import pl.marczynski.pwr.si.csp.game.heuristics.FifoHeuristics;
import pl.marczynski.pwr.si.csp.game.heuristics.Heuristics;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainTest {

    private static final String DATA_PATH = "./src/main/resources/";

    @Test
    public void test() {
        List<SolvingAlgorithm> solvingAlgorithms = Arrays.asList(new BacktrackingAlgorithm(), new ForwardCheckingAlgorithm());
        List<Heuristics> heuristics = Arrays.asList(new FifoHeuristics());
        String directory = "test_data";
        List<String> fileNames = getFilesInDirectory(directory);
//        List<String> fileNames = Collections.singletonList("test_futo_4_0.txt");

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
