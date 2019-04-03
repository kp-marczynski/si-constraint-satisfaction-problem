package pl.marczynski.pwr.si.csp.game.solving_algorithm;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;

public class ForwardCheckingAlgoritm implements SolvingAlgorithm {
    @Override
    public Board initializeBoard(String fileName) {
        Board board = Board.initializeFromFile(fileName);
        board.initializeNullsWithPossibleValues();
        return board;
    }

    @Override
    public Board makeMove(Board board, FieldId fieldId, Integer value) {
        board.makeMove(fieldId, value);
        board.removeAllForbiddenValues();
        return board;
    }
}
