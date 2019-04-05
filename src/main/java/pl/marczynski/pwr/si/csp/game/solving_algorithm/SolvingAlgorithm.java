package pl.marczynski.pwr.si.csp.game.solving_algorithm;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;

public interface SolvingAlgorithm {
    Board initializeBoard(String fileName);
    Board makeMove(Board board, FieldId fieldId, Integer value);
    String getAlgorithmName();
}
