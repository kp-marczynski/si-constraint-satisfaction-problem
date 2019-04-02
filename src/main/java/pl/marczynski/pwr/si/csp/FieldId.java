package pl.marczynski.pwr.si.csp;

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
}
