package pl.marczynski.pwr.si.csp.board;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private List<Integer> possibleValues;
    private final FieldId fieldId;

    private Field(FieldId fieldId) {
        this.possibleValues = new ArrayList<>();
        this.fieldId = fieldId;
    }

    public Field(Field field) {
        this.fieldId = field.fieldId;
        this.possibleValues = new ArrayList<>(field.possibleValues);
    }

    public static Field createForSize(FieldId fieldId, int size) {
        Field result = new Field(fieldId);
        for (int i = 1; i <= size; i++) {
            result.possibleValues.add(i);
        }
        return result;
    }

    public static Field createForSingleValue(FieldId fieldId, int value) {
        Field result = new Field(fieldId);
        result.possibleValues.add(value);
        return result;
    }

    public boolean hasOneValue() {
        return possibleValues.size() == 1;
    }

    public Integer getSingleValue() {
        if (this.possibleValues.size() != 1) {
            throw new IllegalStateException("Field does not have exactly one value");
        }
        return this.possibleValues.get(0);
    }

    public boolean setSingleValue(int value) {
        if (possibleValues.contains(value)) {
            possibleValues.clear();
            possibleValues.add(value);
            return true;
        }
        return false;
    }

    public boolean hasNoValues() {
        return possibleValues.size() == 0;
    }

    public List<Integer> getPossibleValues() {
        return this.possibleValues;
    }

    public int getNumberOfPossibleValues() {
        return possibleValues.size();
    }

    public boolean removeForbiddenValues(List<Integer> forbidenValues) {
        possibleValues.removeAll(forbidenValues);
        return possibleValues.size() >= 1;
    }

    @Override
    public String toString() {
        String result;
        if (possibleValues.size() == 1) {
            result = possibleValues.get(0).toString();
        } else if (possibleValues.size() < 1) {
            result = "!";
        } else result = "0";
        return String.format("%4s", result);
    }
}
