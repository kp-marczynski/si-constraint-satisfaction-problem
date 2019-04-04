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
            int rowElems = board.getRow(rowNum).stream().mapToInt(field -> field == null ? 1 : field.getNumberOfPossibleValues()).sum();

            for (int colNum = 0; colNum < boardSize; colNum++) {
                int colElems = board.getColumn(colNum).stream().mapToInt(field -> field == null ? 1 : field.getNumberOfPossibleValues()).sum();

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
                    if (numberOfPossibleValues < 1) {
                        isEligible = false;
                    }
                }

                if (isEligible) {
                    int currentIntensityIndex = (rowElems + colElems) / numberOfPossibleValues; //minimizing choice & maximizing system response
                    if (intensityIndex == null || currentIntensityIndex > intensityIndex) {
                        intensityIndex = currentIntensityIndex;
                        selectedField = fieldId;
                    }
                }
            }
        }
        return selectedField;
    }
}
