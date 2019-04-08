package pl.marczynski.pwr.si.csp.game.heuristics;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.Field;
import pl.marczynski.pwr.si.csp.board.FieldId;

public class FifoHeuristics implements Heuristics {
    @Override
    public FieldId getNextField(Board board) {
        int boardSize = board.getBoardSize();
        for (int rowNum = 0; rowNum < boardSize; rowNum++) {
            for (int colNum = 0; colNum < boardSize; colNum++) {
                FieldId fieldId = new FieldId(rowNum, colNum);
                Field field = board.getFieldForCoordinates(fieldId);
                if (field == null) {
                    return fieldId;
                }
            }
        }
        return null;
    }

    @Override
    public String getHeuristicsName() {
        return "fifoHeuristics";
    }
}
