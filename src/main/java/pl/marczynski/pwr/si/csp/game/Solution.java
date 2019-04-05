package pl.marczynski.pwr.si.csp.game;

import pl.marczynski.pwr.si.csp.board.Board;

public class Solution {
    private final Board board;
    private final int moveCount;
    private final long endTime;

    public Solution(Board board, int moveCount, long endTime) {
        this.board = board;
        this.moveCount = moveCount;
        this.endTime = endTime;
    }

    public Board getBoard() {
        return board;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public long getEndTime() {
        return endTime;
    }
}
