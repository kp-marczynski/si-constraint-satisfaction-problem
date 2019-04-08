package pl.marczynski.pwr.si.csp.board;

import pl.marczynski.pwr.si.csp.board.futoshiki.FutoshikiBoard;
import pl.marczynski.pwr.si.csp.board.skyscraper.SkyscraperBoard;

import java.util.List;

public interface Board {
    boolean validate();

    boolean isGameOver();

    List<Integer> getPossibleValues(FieldId fieldId);

    boolean makeMove(FieldId fieldId, int value);

    void removeAllForbiddenValues();

    int getBoardSize();

    List<Integer> getBoardDomain();

    Field getFieldForCoordinates(FieldId fieldId);

    void setField(FieldId fieldId, Field field);

    List<List<Field>> getBoard();

    List<FieldId> getChangedFields();

    Board copy();

    List<Field> getRow(int rowNum);

    List<Field> getColumn(int colNum);

    static Board initializeFromFile(String fileName) {
        if (fileName.contains("futo")) {
            return FutoshikiBoard.initializeFromFile(fileName);
        } else if (fileName.contains("sky")) {
            return SkyscraperBoard.initializeFromFile(fileName);
        } else {
            throw new IllegalStateException("Not recognized file");
        }
    }
}
