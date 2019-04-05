package pl.marczynski.pwr.si.csp.board;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractBoard implements Board {
    protected static final String DATA_PATH = "./src/main/resources/";

    protected final List<List<Field>> board;

    protected AbstractBoard(int size) {
        this.board = createEmptyBoard(size);
    }

    private List<List<Field>> createEmptyBoard(int size) {
        List<List<Field>> result = new ArrayList<>();
        for (int rowNum = 0; rowNum < size; rowNum++) {
            List<Field> row = new ArrayList<>();
            for (int colNum = 0; colNum < size; colNum++) {
                row.add(colNum, null);
            }
            result.add(rowNum, row);
        }
        return result;
    }

    protected AbstractBoard(Board board) {
        int size = board.getBoardSize();
        this.board = createEmptyBoard(size);
        for (int rowNum = 0; rowNum < size; rowNum++) {
            for (int colNum = 0; colNum < size; colNum++) {
                FieldId fieldId = new FieldId(rowNum, colNum);
                Field field = board.getFieldForCoordinates(fieldId);
                if (field != null) {
                    setField(fieldId, new Field(field));
                }
            }
        }
    }

    public void setField(FieldId fieldId, Field field) {
        this.board.get(fieldId.getRowNum()).set(fieldId.getColNum(), field);
    }

    public int getBoardSize() {
        return this.board.size();
    }

    public abstract Board copy();

    public boolean makeMove(FieldId fieldId, int value) {
        Field field = getFieldForCoordinates(fieldId);
        if (field == null) {
            setField(fieldId, Field.createForSingleValue(fieldId, value));
            return true;
        } else {
            return field.setSingleValue(value);
        }
    }

    public List<Integer> getPossibleValues(FieldId fieldId) {
        Field field = getFieldForCoordinates(fieldId);
        if (field != null) {
            if (field.getNumberOfPossibleValues() > 1) {
                return new ArrayList<>(field.getPossibleValues());
            } else {
                return new ArrayList<>();
            }
        } else {
            List<Integer> result = getBoardDomain();
            result.removeAll(getForbiddenValues(fieldId));
            return result;
        }
    }

    @Override
    public Field getFieldForCoordinates(FieldId fieldId) {
        return this.board.get(fieldId.getRowNum()).get(fieldId.getColNum());
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
                FieldId fieldId = new FieldId(rowNum, colNum);
                if (getFieldForCoordinates(fieldId) == null) {
                    setField(fieldId, Field.createForAvailableValues(fieldId, getPossibleValues(fieldId)));
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
                FieldId fieldId = new FieldId(rowNum, colNum);
                Field field = getFieldForCoordinates(fieldId);
                if (field == null || !field.hasOneValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected abstract boolean validateConstraints();

    protected abstract String constraintsToString();

    public List<Field> getRow(int rowNum) {
        return new ArrayList<>(board.get(rowNum));
    }

    public List<Field> getColumn(int colNum) {
        List<Field> result = new ArrayList<>();
        for (List<Field> fields : board) {
            result.add(fields.get(colNum));
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
                    FieldId fieldId = new FieldId(rowNum, colNum);
                    if (getFieldForCoordinates(fieldId) != null) {
                        Pair<Boolean, Boolean> removalResult = removeForbiddenValues(fieldId);
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

    private Pair<Boolean, Boolean> removeForbiddenValues(FieldId fieldId) {
        Field field = getFieldForCoordinates(fieldId);
        int numberOfPossibleValues = field.getNumberOfPossibleValues();
        boolean result = field.removeForbiddenValues(getForbiddenValues(fieldId));
        int newNumberOfPossibleValues = field.getNumberOfPossibleValues();
        boolean possibleValuesChanged = numberOfPossibleValues != newNumberOfPossibleValues;
        return new Pair<>(result, possibleValuesChanged);
    }

    private List<Integer> getForbiddenValues(FieldId fieldId) {
        List<Field> row = getRow(fieldId.getRowNum());
        row.set(fieldId.getColNum(), null);
        List<Integer> result = row.stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList());
        List<Field> column = getColumn(fieldId.getColNum());
        column.set(fieldId.getRowNum(), null);
        result.addAll(column.stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList()));
        List<Integer> collect = result.stream().distinct().collect(Collectors.toList());
        return collect;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int rowNum = 0; rowNum < getBoardSize(); ++rowNum) {
            for (int colNum = 0; colNum < getBoardSize(); ++colNum) {
                Field field = getFieldForCoordinates(new FieldId(rowNum, colNum));
                if (field != null) {
                    builder.append(field);
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
