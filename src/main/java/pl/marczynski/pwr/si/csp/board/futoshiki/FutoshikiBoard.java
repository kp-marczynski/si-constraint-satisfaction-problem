package pl.marczynski.pwr.si.csp.board.futoshiki;

import pl.marczynski.pwr.si.csp.board.AbstractBoard;
import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.Field;
import pl.marczynski.pwr.si.csp.board.FieldId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FutoshikiBoard extends AbstractBoard {
    private List<FutoshikiConstraint> constraints;

    private FutoshikiBoard(int size) {
        super(size);
        constraints = new ArrayList<>();
    }

    private FutoshikiBoard(FutoshikiBoard board) {
        super(board.board);
        this.constraints = board.constraints;
    }

    @Override
    public Board copy() {
        return new FutoshikiBoard(this);
    }

    public static FutoshikiBoard initializeFromFile(String fileName) {
        FutoshikiBoard futoshikiBoard = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(DATA_PATH + fileName + ".txt"))) {
            String line = fileReader.readLine();
            Integer size = Integer.valueOf(line);
            futoshikiBoard = new FutoshikiBoard(size);
            line = fileReader.readLine();
            if (!line.contains("START")) {
                throw new IllegalStateException("START string was expected");
            }
            for (Integer rowNum = 0; rowNum < size; rowNum++) {
                String row = fileReader.readLine();
                String[] splitedRow = row.split(";");
                for (int colNum = 0; colNum < splitedRow.length; colNum++) {
                    if (!splitedRow[colNum].equals("0")) {
                        int value = Integer.parseInt(splitedRow[colNum]);
                        futoshikiBoard.board[rowNum][colNum] = Field.createForSingleValue(new FieldId(rowNum, colNum), value);
                    }
                }
            }
            line = fileReader.readLine();
            if (!line.contains("REL")) {
                throw new IllegalStateException("REL string was expected");
            }
            line = fileReader.readLine();
            while (line != null) {
                String[] splittedLine = line.split(";");
                if (splittedLine.length == 2) {
                    FieldId smaller = new FieldId(splittedLine[0].charAt(0) - 'A', Integer.valueOf(splittedLine[0].substring(1)) - 1);
                    FieldId bigger = new FieldId(splittedLine[1].charAt(0) - 'A', Integer.valueOf(splittedLine[1].substring(1)) - 1);
                    futoshikiBoard.constraints.add(new FutoshikiConstraint(smaller, bigger));
                }
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return futoshikiBoard;
    }

    @Override
    public boolean validateConstraints() {
        for (FutoshikiConstraint constraint : constraints) {
            FieldId smaller = constraint.getSmaller();
            Field smallerField = this.board[smaller.getRowNum()][smaller.getColNum()];
            FieldId bigger = constraint.getBigger();
            Field biggerField = this.board[bigger.getRowNum()][bigger.getColNum()];
            if (smallerField != null && biggerField != null) {
                if (biggerField.hasOneValue() && smallerField.hasOneValue()) {
                    if (biggerField.getSingleValue() < smallerField.getSingleValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected String constraintsToString() {
        StringBuilder builder = new StringBuilder();
        for (FutoshikiConstraint constraint : constraints) {
            builder.append("\n").append(constraint);
        }
        return builder.toString();
    }
}
