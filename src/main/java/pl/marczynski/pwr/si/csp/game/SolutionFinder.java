package pl.marczynski.pwr.si.csp.game;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;
import pl.marczynski.pwr.si.csp.game.heuristics.Heuristics;
import pl.marczynski.pwr.si.csp.game.solving_algorithm.SolvingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class SolutionFinder {
    private FieldId currentRoot;
    private List<Integer> valuesToCheck;
    private final SolvingAlgorithm solvingAlgorithm;
    private final Heuristics heuristics;
    private Board board;
    private final SolutionCollection solutionCollection;

    public SolutionFinder(SolvingAlgorithm solvingAlgorithm, Heuristics heuristics, String fileName) {
        this.solvingAlgorithm = solvingAlgorithm;
        this.heuristics = heuristics;
        this.board = solvingAlgorithm.initializeBoard(fileName);
        this.solutionCollection = new SolutionCollection(solvingAlgorithm.getAlgorithmName(), heuristics.getHeuristicsName(), fileName);
        initializeRoot();
    }

    private SolutionFinder(SolutionFinder parent, int parentRootValue) {
        this.solvingAlgorithm = parent.solvingAlgorithm;
        this.heuristics = parent.heuristics;
        this.board = parent.board.copy();
        this.solutionCollection = parent.solutionCollection;

        solvingAlgorithm.makeMove(this.board, parent.currentRoot, parentRootValue);
        this.solutionCollection.increaseMoveCount();
        initializeRoot();
    }

    private void initializeRoot() {
        if (this.currentRoot == null) {
            this.currentRoot = heuristics.getNextField(this.board);
            if (this.currentRoot != null) {
                this.valuesToCheck = this.board.getPossibleValues(this.currentRoot);
            } else {
                this.valuesToCheck = new ArrayList<>();
            }
        }
    }

    public SolutionCollection findSolution() {
        if (!this.board.validate()) {
            return this.solutionCollection;
        } else if (this.board.isGameOver()) {
            this.solutionCollection.addSolution(new Solution(this.board, solutionCollection.getCurrrentMoveCount(), System.currentTimeMillis()));
            return this.solutionCollection;
        } else {
            for (Integer value : valuesToCheck) {
                new SolutionFinder(this, value).findSolution();
            }
        }
        this.solutionCollection.setEndTime();
        return this.solutionCollection;
    }
}
