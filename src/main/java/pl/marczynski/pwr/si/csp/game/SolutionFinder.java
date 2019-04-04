package pl.marczynski.pwr.si.csp.game;

import javafx.util.Pair;
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
    private int moveCount;

    public SolutionFinder(SolvingAlgorithm solvingAlgorithm, Heuristics heuristics, String fileName) {
        this.solvingAlgorithm = solvingAlgorithm;
        this.heuristics = heuristics;
        this.board = solvingAlgorithm.initializeBoard(fileName);
        this.moveCount = 0;
        initializeRoot();
    }

    private SolutionFinder(SolutionFinder parent, int parentRootValue) {
        this.solvingAlgorithm = parent.solvingAlgorithm;
        this.heuristics = parent.heuristics;
        this.board = parent.board.copy();
        solvingAlgorithm.makeMove(this.board, parent.currentRoot, parentRootValue);
        this.moveCount = 1;
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

    public Pair<Board, Integer> findSolution() {
        if (!this.board.validate()) {
            return new Pair<>(null, moveCount);
        } else if (this.board.isGameOver()) {
            return new Pair(this.board, moveCount);
        } else {
            for (Integer value : valuesToCheck) {
                Pair<Board, Integer> solution = new SolutionFinder(this, value).findSolution();
                this.moveCount += solution.getValue();
                if (solution.getKey() != null) {
                    return new Pair<>(solution.getKey(), moveCount);
                }
            }
        }
        return new Pair<>(null, moveCount);
    }
}
