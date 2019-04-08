package pl.marczynski.pwr.si.csp.board;

import java.util.ArrayList;
import java.util.List;

public class Field {
    //    private List<Integer> possibleValues;
    private final int value;
    private final FieldId fieldId;

//    private Field(FieldId fieldId) {
//        this.possibleValues = new ArrayList<>();
//        this.fieldId = fieldId;
//    }

    public Field(Field field) {
        this.fieldId = field.fieldId;
        this.value = field.value;
    }

    public static Field createForSingleValue(FieldId fieldId, int value) {
        Field result = new Field(fieldId);
//        result.possibleValues.add(value);

        return result;
    }

    public FieldId getFieldId() {
        return fieldId;
    }

    @Override
    public String toString() {
        String result = String.valueOf(value);
        return String.format("%4s", result);
    }
}
