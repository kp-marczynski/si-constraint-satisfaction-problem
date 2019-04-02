package pl.marczynski.pwr.si.csp.skyscraper;

import pl.marczynski.pwr.si.csp.AbstractBoard;
import pl.marczynski.pwr.si.csp.Field;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SkyscraperBoard extends AbstractBoard {
    private List<SkyscraperConstraint> constraints;

    public SkyscraperBoard(int size) {
        super(size, "skyscrapper");
        constraints = new ArrayList<>();
    }

    public static SkyscraperBoard initializeFromFile(String fileName) {
        SkyscraperBoard skyscraperBoard = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(DATA_PATH + fileName + ".txt"))) {
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
        return skyscraperBoard;
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

            fields = fields.stream().filter(Objects::nonNull).filter(Field::hasOneValue).collect(Collectors.toList());
            if (fields.size() == getBoardSize()) {
                int currentMax = 0;
                int visible = 0;
                for (Field field : fields) {
                    Integer singleValue = field.getSingleValue();
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
