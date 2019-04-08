package pl.marczynski.pwr.si.csp.game.heuristics;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.Field;
import pl.marczynski.pwr.si.csp.board.FieldId;

public class IntensityHeuristics implements Heuristics {
    @Override
    public FieldId getNextField(Board board) {
        int boardSize = board.getBoardSize();
        Integer intensityIndex = null;
        FieldId selectedField = null;
        for (int rowNum = 0; rowNum < boardSize; rowNum++) {
//            int rowElems = board.getRow(rowNum).stream().mapToInt(field -> field == null ? 1 : field.getNumberOfPossibleValues()).sum();

            for (int colNum = 0; colNum < boardSize; colNum++) {
//                int colElems = board.getColumn(colNum).stream().mapToInt(field -> field == null ? 1 : field.getNumberOfPossibleValues()).sum();

                FieldId fieldId = new FieldId(rowNum, colNum);
                Field field = board.getFieldForCoordinates(fieldId);

                int numberOfPossibleValues = 0;
                boolean isEligible = false;
                if (field == null) {
                    numberOfPossibleValues = board.getPossibleValues(fieldId).size();
                    if (numberOfPossibleValues > 0) {
                        isEligible = true;
                    }
                }

                if (isEligible) {
                    if (intensityIndex == null || numberOfPossibleValues < intensityIndex) {
                        intensityIndex = numberOfPossibleValues;
                        selectedField = fieldId;
                    }
                }
            }
        }
        return selectedField;
    }

    @Override
    public String getHeuristicsName() {
        return "intensityHeuristics";
    }
}
