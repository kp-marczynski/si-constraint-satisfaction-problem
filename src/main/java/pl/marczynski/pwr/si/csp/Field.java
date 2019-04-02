package pl.marczynski.pwr.si.csp;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private List<Integer> possibleValues;
    private final FieldId fieldId;

    private Field(FieldId fieldId) {
        this.possibleValues = new ArrayList<>();
        this.fieldId = fieldId;
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
            throw new IllegalStateException();
        }
        return this.possibleValues.get(0);
    }

    public boolean hasNoValues() {
        return possibleValues.size() == 0;
    }

    public List<Integer> getPossibleValues() {
        return this.possibleValues;
    }

    public boolean removeForbidenValues(List<Integer> forbidenValues) {
        possibleValues.removeAll(forbidenValues);
        return possibleValues.size() >= 1;
    }
}
