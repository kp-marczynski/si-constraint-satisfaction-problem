package pl.marczynski.pwr.si.csp;

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
        super(size);
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
                    throw new IllegalStateException("Inccorect nuber of skyscraper constraints");
                }
                SkyscrapperConstraintType type = SkyscrapperConstraintType.getFromString(splitedLine[0]);
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
                    fields = getColumn(constraint.getIndex());
                    break;
                case DOWN:
                    fields = getColumn(constraint.getIndex());
                    Collections.reverse(fields);
                    break;
                case LEFT:
                    fields = getRow(constraint.getIndex());
                    break;
                case RIGHT:
                    fields = getRow(constraint.getIndex());
                    Collections.reverse(fields);
                    break;
            }
            fields = fields.stream().filter(Objects::nonNull).filter(Field::hasOneValue).collect(Collectors.toList());
            if (fields.size() == this.board.length) {
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append("\nConst:");
        for (SkyscraperConstraint constraint : constraints) {
            builder.append("\n").append(constraint);
        }
        builder.append("\n\nisValid: ").append(validate());
        return builder.toString();
    }
}
