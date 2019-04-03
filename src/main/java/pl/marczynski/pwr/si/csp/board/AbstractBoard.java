package pl.marczynski.pwr.si.csp.board;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractBoard implements Board {
    protected static final String DATA_PATH = "./src/main/resources/test_data/";

    protected final Field[][] board;

    protected AbstractBoard(int size) {
        this.board = new Field[size][size];
    }

    protected AbstractBoard(Field[][] fields) {
        int size = fields.length;
        this.board = new Field[size][size];
        for (int rowNum = 0; rowNum < size; rowNum++) {
            for (int colNum = 0; colNum < size; colNum++) {
                if (fields[rowNum][colNum] != null) {
                    this.board[rowNum][colNum] = new Field(fields[rowNum][colNum]);
                }
            }
        }
    }

    public int getBoardSize() {
        return this.board.length;
    }

    public abstract Board copy();

    public boolean makeMove(FieldId fieldId, int value) {
        Field field = this.board[fieldId.getRowNum()][fieldId.getColNum()];
        if (field == null) {
            this.board[fieldId.getRowNum()][fieldId.getColNum()] = Field.createForSingleValue(fieldId, value);
            return true;
        } else {
            return field.setSingleValue(value);
        }
    }

    public List<Integer> getPossibleValues(FieldId fieldId) {
        return null; //todo
    }

    public List<Integer> getBoardDomain() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= getBoardSize(); i++) {
            result.add(i);
        }
        return result;
    }

    public void initializeNullsWithPossibleValues() {
        for (int rowNum = 0; rowNum < getBoardSize(); ++rowNum) {
            for (int colNum = 0; colNum < getBoardSize(); ++colNum) {
                if (this.board[rowNum][colNum] == null) {
                    this.board[rowNum][colNum] = Field.createForSize(new FieldId(rowNum, colNum), getBoardSize());
                    this.board[rowNum][colNum].removeForbiddenValues(getForbiddenValues(rowNum, colNum));
                }
            }
        }
    }

    public boolean validate() {
        for (int i = 0; i < getBoardSize(); i++) {
            List<Integer> row = getRow(i).stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList());
            int rowSize = row.size();
            if (row.stream().distinct().collect(Collectors.toList()).size() != rowSize) {
                return false;
            }
            List<Integer> column = getColumn(i).stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList());
            int colSize = column.size();
            if (column.stream().distinct().collect(Collectors.toList()).size() != colSize) {
                return false;
            }

            if (getRow(i).stream().filter(Objects::nonNull).anyMatch(Field::hasNoValues) || getColumn(i).stream().filter(Objects::nonNull).anyMatch(Field::hasNoValues)) {
                return false;
            }
        }
        return validateConstraints();
    }

    public boolean isGameOver() {
        for (int rowNum = 0; rowNum < getBoardSize(); rowNum++) {
            for (int colNum = 0; colNum < getBoardSize(); colNum++) {
                if (this.board[rowNum][colNum] == null || !this.board[rowNum][colNum].hasOneValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected abstract boolean validateConstraints();

    protected abstract String constraintsToString();

    protected List<Field> getRow(int rowNum) {
        return Arrays.asList(board[rowNum]);
    }

    protected List<Field> getColumn(int colNum) {
        List<Field> result = new ArrayList<>();
        for (Field[] fields : board) {
            result.add(fields[colNum]);
        }
        return result;
    }

    public boolean removeAllForbiddenValues() {
        boolean valuesChanged = true;
        boolean result = true;
        while (valuesChanged && result) {
            valuesChanged = false;
            for (int rowNum = 0; rowNum < getBoardSize(); ++rowNum) {
                for (int colNum = 0; colNum < getBoardSize(); ++colNum) {
                    if (this.board[rowNum][colNum] != null) {
                        Pair<Boolean, Boolean> removalResult = removeForbiddenValues(rowNum, colNum);
                        if (!removalResult.getKey()) {
                            result = false;
                        }
                        if (removalResult.getValue()) {
                            valuesChanged = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    private Pair<Boolean, Boolean> removeForbiddenValues(int rowNum, int colNum) {
        int numberOfPossibleValues = this.board[rowNum][colNum].getNumberOfPossibleValues();
        boolean result = this.board[rowNum][colNum].removeForbiddenValues(getForbiddenValues(rowNum, colNum));
        int newNumberOfPossibleValues = this.board[rowNum][colNum].getNumberOfPossibleValues();
        boolean possibleValuesChanged = numberOfPossibleValues != newNumberOfPossibleValues;
        return new Pair<>(result, possibleValuesChanged);
    }

    private List<Integer> getForbiddenValues(int rowNum, int colNum) {
        List<Field> row = getRow(rowNum);
        row.remove(colNum);
        List<Integer> result = row.stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList());
        List<Field> column = getColumn(colNum);
        column.remove(rowNum);
        result.addAll(column.stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList()));
        return result.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int rowNum = 0; rowNum < getBoardSize(); ++rowNum) {
            for (int colNum = 0; colNum < getBoardSize(); ++colNum) {
                if (this.board[rowNum][colNum] != null) {
                    builder.append(this.board[rowNum][colNum]);
                } else {
                    builder.append(String.format("%4s", "0"));
                }
            }
            builder.append("\n");
        }
        builder.append("\nConst:");
        builder.append(constraintsToString());
        builder.append("\n\nisValid: ").append(validate());
        return builder.toString();
    }
}
