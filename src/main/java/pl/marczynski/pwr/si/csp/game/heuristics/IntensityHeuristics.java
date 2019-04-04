package pl.marczynski.pwr.si.csp.game.heuristics;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.Field;
import pl.marczynski.pwr.si.csp.board.FieldId;

import java.util.Objects;

public class IntensityHeuristics implements Heuristics {
    @Override
    public FieldId getNextField(Board board) {
        int boardSize = board.getBoardSize();
        Integer intensityIndex = null;
        FieldId selectedField = null;
        for (int rowNum = 0; rowNum < boardSize; rowNum++) {
            int rowElems = board.getRow(rowNum).stream().filter(Objects::nonNull).mapToInt(Field::getNumberOfPossibleValues).sum();
            for (int colNum = 0; colNum < boardSize; colNum++) {
                int colElems = board.getColumn(colNum).stream().filter(Objects::nonNull).mapToInt(Field::getNumberOfPossibleValues).sum();

                FieldId fieldId = new FieldId(rowNum, colNum);
                Field field = board.getFieldForCoordinates(fieldId);

                int numberOfPossibleValues;
                boolean isEligible = true;
                if (field != null) {
                    numberOfPossibleValues = field.getNumberOfPossibleValues();
                    if (numberOfPossibleValues <= 1) {
                        isEligible = false;
                    }
                } else {
                    numberOfPossibleValues = board.getPossibleValues(fieldId).size();
                }

                if (isEligible) {
                    int currentIntensityIndex = (2 * boardSize - (rowElems + colElems)) * numberOfPossibleValues;
                    if (intensityIndex == null || currentIntensityIndex < intensityIndex) {
                        intensityIndex = currentIntensityIndex;
                        selectedField = fieldId;
                    }
                }
            }
        }
        return selectedField;
    }
}
