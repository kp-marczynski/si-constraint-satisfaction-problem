package pl.marczynski.pwr.si.csp.board.skyscraper;

import pl.marczynski.pwr.si.csp.board.AbstractBoard;
import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.Field;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkyscraperBoard extends AbstractBoard {
    private List<SkyscraperConstraint> constraints;

    private SkyscraperBoard(int size) {
        super(size);
        constraints = new ArrayList<>();
    }

    private SkyscraperBoard(SkyscraperBoard board) {
        super(board);
        this.constraints = board.constraints;
    }

    @Override
    public Board copy() {
        return new SkyscraperBoard(this);
    }

    public static SkyscraperBoard initializeFromFile(String fileName) {
        SkyscraperBoard skyscraperBoard = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(DATA_PATH + fileName))) {
            String line = fileReader.readLine();
            Integer size = Integer.valueOf(line);
            skyscraperBoard = new SkyscraperBoard(size);
            line = fileReader.readLine();
            while (line != null) {
                String[] splitedLine = line.split(";");
                if (splitedLine.length != size + 1) {
                    throw new IllegalStateException("Incorrect number of skyscraper constraints");
                }
                SkyscraperConstraintType type = SkyscraperConstraintType.getFromString(splitedLine[0]);
                for (int i = 1; i < splitedLine.length; i++) {
                    if (!splitedLine[i].equals("0")) {
                        skyscraperBoard.constraints.add(new SkyscraperConstraint(type, i - 1, Integer.valueOf(splitedLine[i])));
                    }
                }
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!skyscraperBoard.checkParsedConstraints()) {
            throw new IllegalStateException("File contains wrong constraints");
        }
        return skyscraperBoard;
    }

    private boolean checkParsedConstraints() {
        List<SkyscraperConstraint> ups = constraints.stream().filter(cons -> cons.getType().equals(SkyscraperConstraintType.UP)).collect(Collectors.toList());
        List<SkyscraperConstraint> downs = constraints.stream().filter(cons -> cons.getType().equals(SkyscraperConstraintType.DOWN)).collect(Collectors.toList());
        List<SkyscraperConstraint> lefts = constraints.stream().filter(cons -> cons.getType().equals(SkyscraperConstraintType.LEFT)).collect(Collectors.toList());
        List<SkyscraperConstraint> rights = constraints.stream().filter(cons -> cons.getType().equals(SkyscraperConstraintType.RIGHT)).collect(Collectors.toList());

        return checkParsedConstraints(ups, downs) && checkParsedConstraints(lefts, rights);
    }

    private boolean checkParsedConstraints(List<SkyscraperConstraint> oneSide, List<SkyscraperConstraint> otherSide) {
        for (SkyscraperConstraint one : oneSide) {
            Optional<SkyscraperConstraint> optionalOther = otherSide.stream().filter(other -> other.getIndex() == one.getIndex()).findAny();
            if (optionalOther.isPresent()) {
                int sum = optionalOther.get().getVisibleSkyScrappers() + one.getVisibleSkyScrappers();
                if (sum > getBoardSize() + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean validateConstraints() {
        List<Field> fields = new ArrayList<>();
        for (SkyscraperConstraint constraint : constraints) {
            switch (constraint.getType()) {
                case UP:
                case DOWN:
                    fields = getColumn(constraint.getIndex());
                    break;
                case LEFT:
                case RIGHT:
                    fields = getRow(constraint.getIndex());
                    break;
            }
            if (constraint.getType().hasReversedOrder()) {
                Collections.reverse(fields);
            }

            fields = fields.stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (fields.size() == getBoardSize()) {
                int currentMax = 0;
                int visible = 0;
                for (Field field : fields) {
                    Integer singleValue = field.getValue();
                    if (singleValue > currentMax) {
                        currentMax = singleValue;
                        ++visible;
                    }
                }
                if (visible != constraint.getVisibleSkyScrappers()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String constraintsToString() {
        StringBuilder builder = new StringBuilder();
        for (SkyscraperConstraint constraint : constraints) {
            builder.append("\n").append(constraint);
        }
        return builder.toString();
    }
}
