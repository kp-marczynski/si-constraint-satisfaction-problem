package pl.marczynski.pwr.si.csp;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private List<Integer> possibleValues;

    private Field() {
        this.possibleValues = new ArrayList<>();
    }

    public static Field createForSize(int size) {
        Field result = new Field();
        for (int i = 1; i <= size; i++) {
            result.possibleValues.add(i);
        }
        return result;
    }

    public static Field createForSingleValue(int value) {
        Field result = new Field();
        result.possibleValues.add(value);
        return result;
    }

    public int getNumberOfPossibleValues() {
        return possibleValues.size();
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

}
