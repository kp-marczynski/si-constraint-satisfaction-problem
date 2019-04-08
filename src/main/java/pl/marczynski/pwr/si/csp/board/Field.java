package pl.marczynski.pwr.si.csp.board;

public class Field {
    //    private List<Integer> possibleValues;
    private final Integer value;
    private final FieldId fieldId;

//    private Field(FieldId fieldId) {
//        this.possibleValues = new ArrayList<>();
//        this.fieldId = fieldId;
//    }

    private Field(Field field) {
        this.fieldId = field.fieldId;
        this.value = field.value;
    }

    private Field(FieldId fieldId, int value){
        this.value = value;
        this.fieldId = fieldId;
    }

    public static Field createForSingleValue(FieldId fieldId, int value) {
        Field result = new Field(fieldId, value);
//        result.possibleValues.add(value);

        return result;
    }

    public FieldId getFieldId() {
        return fieldId;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        String result = String.valueOf(value);
        return String.format("%4s", result);
    }
}
