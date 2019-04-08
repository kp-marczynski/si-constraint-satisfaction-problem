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
        super(board);
        this.constraints = board.constraints;
    }

    @Override
    public Board copy() {
        return new FutoshikiBoard(this);
    }

    public static FutoshikiBoard initializeFromFile(String fileName) {
        FutoshikiBoard futoshikiBoard = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(DATA_PATH + fileName))) {
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
                        FieldId fieldId = new FieldId(rowNum, colNum);
                        futoshikiBoard.setField(fieldId, Field.createForSingleValue(fieldId, value));
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
        if (!futoshikiBoard.checkParsedConstraints()) {
            throw new IllegalStateException("File contains wrong constraints");
        }
        return futoshikiBoard;
    }

    private boolean checkParsedConstraints() {
        if (constraints.stream().anyMatch(constraint -> constraint.getSmaller().equals(constraint.getBigger()))) {
            return false;
        }
        for (int i = 0; i < constraints.size(); i++) {
            for (int j = 0; j < constraints.size(); j++) {
                if (i != j) {
                    FutoshikiConstraint first = constraints.get(i);
                    FutoshikiConstraint second = constraints.get(j);
                    if ((first.getSmaller().equals(second.getSmaller()) && first.getBigger().equals(second.getBigger()))
                            || (first.getSmaller().equals(second.getBigger()) && first.getBigger().equals(second.getSmaller()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public boolean validateConstraints() {
        for (FutoshikiConstraint constraint : constraints) {
            FieldId smaller = constraint.getSmaller();
            Field smallerField = getFieldForCoordinates(smaller);
            FieldId bigger = constraint.getBigger();
            Field biggerField = getFieldForCoordinates(bigger);
            if (smallerField != null && biggerField != null) {
                if (biggerField.getValue() < smallerField.getValue()) {
                    return false;
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
