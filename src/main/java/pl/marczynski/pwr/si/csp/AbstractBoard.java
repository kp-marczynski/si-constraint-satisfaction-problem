package pl.marczynski.pwr.si.csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractBoard implements Board {
    protected static final String DATA_PATH = "./src/main/resources/test_data/";

    protected final Field[][] board;
    private final String baseProblemName;

    protected AbstractBoard(int size, String baseProblemName) {
        this.board = new Field[size][size];
        this.baseProblemName = baseProblemName;
    }

    public List<String> getFilesNames() {
        List<String> result = new ArrayList<>();
        for (int i = 4; i <= 5; ++i) {
            for (int j = 0; j <= 4; ++j) {
                result.add(baseProblemName + "_" + i + "_" + j);
            }
        }
        return result;
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

            if (getRow(i).stream().filter(Objects::nonNull).anyMatch(Field::hasNoValues) || getColumn(i).stream().filter(Objects::nonNull).anyMatch(Field::hasNoValues)) {
                return false;
            }
        }
        return validateConstraints();
    }

    public abstract boolean validateConstraints();

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

    private boolean removeForbidenValues(int rowNum, int colNum) {
        return this.board[rowNum][colNum].removeForbidenValues(getForbidenValues(rowNum, colNum));
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

    @Override
    public String toString() {
        int size = this.board.length;
        StringBuilder builder = new StringBuilder();
        for (int rowNum = 0; rowNum < size; ++rowNum) {
            for (int colNum = 0; colNum < size; ++colNum) {
                if (this.board[rowNum][colNum] != null) {
                    builder.append(this.board[rowNum][colNum]);
                } else {
                    builder.append(String.format("%4s", "0"));
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
