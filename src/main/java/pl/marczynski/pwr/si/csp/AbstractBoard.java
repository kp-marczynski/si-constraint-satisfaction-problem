package pl.marczynski.pwr.si.csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractBoard implements Board {
    private Field[][] board;

    public AbstractBoard(int size) {
        this.board = new Field[size][size];
    }

    public boolean validate() {
        int size = board.length;
        for (int i = 0; i < size; i++) {
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

            if (getRow(i).stream().anyMatch(Field::hasNoValues) || getColumn(i).stream().anyMatch(Field::hasNoValues)) {
                return false;
            }
        }
        return true;
    }

    private List<Field> getRow(int rowNum) {
        return Arrays.asList(board[rowNum]);
    }

    private List<Field> getColumn(int colNum) {
        List<Field> result = new ArrayList<>();
        for (Field[] fields : board) {
            result.add(fields[colNum]);
        }
        return result;
    }

    private List<Integer> getForbidenValues(int rowNum, int colNum) {
        List<Field> row = getRow(rowNum);
        row.remove(colNum);
        List<Integer> result = row.stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList());
        List<Field> column = getColumn(colNum);
        column.remove(rowNum);
        result.addAll(column.stream().filter(Objects::nonNull).filter(Field::hasOneValue).map(Field::getSingleValue).collect(Collectors.toList()));
        return result.stream().distinct().collect(Collectors.toList());
    }
}
