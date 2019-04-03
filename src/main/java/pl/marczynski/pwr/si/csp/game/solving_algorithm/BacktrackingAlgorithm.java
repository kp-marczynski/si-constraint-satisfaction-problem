package pl.marczynski.pwr.si.csp.game.solving_algorithm;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;

public class BacktrackingAlgorithm implements SolvingAlgorithm {
    @Override
    public Board initializeBoard(String fileName) {
        return Board.initializeFromFile(fileName);
    }

    @Override
    public Board makeMove(Board board, FieldId fieldId, Integer value) {
        board.makeMove(fieldId, value);
        return board;
    }
}
