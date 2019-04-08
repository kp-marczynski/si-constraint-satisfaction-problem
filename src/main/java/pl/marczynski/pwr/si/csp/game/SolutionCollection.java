package pl.marczynski.pwr.si.csp.game;

import java.util.ArrayList;
import java.util.List;

public class SolutionCollection {
    private final List<Solution> solutions;
    private int currentMoveCount;
    private final String algorithmName;
    private final String heuristicsName;
    private final String fileName;
    private final long startTimestamp;
    private long endTime;
    private boolean isTimeoutExceeded;

    public SolutionCollection(String algorithmName, String heuristicsName, String fileName) {
        this.fileName = fileName;
        this.algorithmName = algorithmName;
        this.heuristicsName = heuristicsName;

        this.solutions = new ArrayList<>();
        this.currentMoveCount = 0;
        this.startTimestamp = System.currentTimeMillis();
        this.isTimeoutExceeded = false;
    }

    public void addSolution(Solution solution) {
        this.solutions.add(solution);
    }

    public int getCurrentMoveCount() {
        return currentMoveCount;
    }

    public void increaseMoveCount() {
        ++this.currentMoveCount;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public double getTotalTime() {
        return ((double) endTime - startTimestamp) / 1000;
    }

    public double getTimeForFirst() {
        if (solutions.size() > 0) {
            return ((double) solutions.get(0).getEndTime() - startTimestamp) / 1000;
        } else {
            return 0.0;
        }
    }

    public void setEndTime() {
        endTime = System.currentTimeMillis();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public String getHeuristicsName() {
        return heuristicsName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public int getMoveCountForFirst() {
        if (solutions.size() > 0) {
            return solutions.get(0).getMoveCount();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(algorithmName).append(": ").append(heuristicsName).append(": ").append(fileName).append("\n")
                .append(String.format("%30s", "Total number of solutions: ")).append(solutions.size()).append("\n")
                .append(String.format("%30s", "Moves for first: ")).append(getMoveCountForFirst()).append("\n")
                .append(String.format("%30s", "Total moves: ")).append(currentMoveCount).append("\n")
                .append(String.format("%30s", "Time for first: ")).append(getTimeForFirst()).append(" s").append("\n")
                .append(String.format("%30s", "Total end time: ")).append(getTotalTime()).append(" s");

        return builder.toString();
    }

    public int getNumberOfSolutions() {
        return solutions.size();
    }

    public void timeout() {
        isTimeoutExceeded = true;
    }

    public boolean isTimeoutExceeded() {
        return this.isTimeoutExceeded;
    }
}
