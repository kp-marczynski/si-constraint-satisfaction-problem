package pl.marczynski.pwr.si.csp.board;

import pl.marczynski.pwr.si.csp.board.futoshiki.FutoshikiBoard;
import pl.marczynski.pwr.si.csp.board.skyscraper.SkyscraperBoard;

import java.util.List;

public interface Board {
    boolean validate();

    boolean isGameOver();

    List<Integer> getPossibleValues(FieldId fieldId);

    boolean makeMove(FieldId fieldId, int value);

    boolean removeAllForbiddenValues();

    int getBoardSize();

    List<Integer> getBoardDomain();

    void initializeNullsWithPossibleValues();

    Board copy();

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
