package pl.marczynski.pwr.si.csp.game;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.heuristics.Heuristics;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.SolvingAlgorithm;

import java.util.List;

public class SolutionFinder {
    private FieldId currentRoot;
    private List<Integer> valuesToCheck;
    private final SolvingAlgorithm solvingAlgorithm;
    private final Heuristics heuristics;
    private Board board;

    public SolutionFinder(SolvingAlgorithm solvingAlgorithm, Heuristics heuristics, String fileName) {
        this.solvingAlgorithm = solvingAlgorithm;
        this.heuristics = heuristics;
        this.board = solvingAlgorithm.initializeBoard(fileName);
        initializeRoot();
    }

    private SolutionFinder(SolutionFinder parent, int parentRootValue) {
        this.solvingAlgorithm = parent.solvingAlgorithm;
        this.heuristics = parent.heuristics;
        this.board = parent.board.copy();
        solvingAlgorithm.makeMove(this.board, parent.currentRoot, parentRootValue);
        initializeRoot();
    }

    private void initializeRoot() {
        if (this.currentRoot == null) {
            this.currentRoot = heuristics.getNextField(this.board);
            if (this.currentRoot != null) {
                this.valuesToCheck = this.board.getPossibleValues(this.currentRoot);
            }
        }
    }

    public Board findSolution() {
        if (!this.board.validate()) {
            return null;
        } else if (this.board.isGameOver()) {
            return this.board;
        } else {
            for (Integer value : valuesToCheck) {
                Board solution = new SolutionFinder(this, value).findSolution();
                if (solution != null) {
                    return solution;
                }
            }
        }
        return null;
    }
}
