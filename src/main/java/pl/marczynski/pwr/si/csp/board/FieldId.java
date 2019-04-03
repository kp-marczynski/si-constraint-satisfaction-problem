package pl.marczynski.pwr.si.csp.board;

import java.util.Objects;

public class FieldId {
    private final int rowNum;
    private final int colNum;

    public FieldId(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldId fieldId = (FieldId) o;
        return rowNum == fieldId.rowNum &&
                colNum == fieldId.colNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, colNum);
    }

    @Override
    public String toString() {
        return String.valueOf((char) (((int) 'A') + rowNum)) + colNum;
    }
}
