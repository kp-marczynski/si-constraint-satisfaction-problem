package pl.marczynski.pwr.si.csp.game;

import java.util.ArrayList;
import java.util.List;

public class SolutionCollection {
    private final List<Solution> solutions;
    private int currentMoveCount;

    public SolutionCollection() {
        this.solutions = new ArrayList<>();
        this.currentMoveCount = 0;
    }

    public void addSolution(Solution solution) {
        this.solutions.add(solution);
    }

    public int getCurrrentMoveCount() {
        return currentMoveCount;
    }

    public void increaseMoveCount() {
        ++this.currentMoveCount;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }
}
