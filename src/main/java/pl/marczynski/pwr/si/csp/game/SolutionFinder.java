package pl.marczynski.pwr.si.csp.game;

import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.heuristics.Heuristics;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.SolvingAlgorithm;

import java.util.List;

public class SolutionFinder {
    FieldId currentRoot;
    List<Integer> valuesToCheck;
    SolvingAlgorithm solvingAlgorithm;
    Heuristics heuristics;

    public SolutionFinder(FieldId currentRoot, List<Integer> valuesToCheck, SolvingAlgorithm solvingAlgorithm, Heuristics heuristics) {
        this.currentRoot = currentRoot;
        this.valuesToCheck = valuesToCheck;
        this.solvingAlgorithm = solvingAlgorithm;
        this.heuristics = heuristics;
    }
}
