package pl.marczynski.pwr.si.csp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append("\nConst:");
        for (SkyscraperConstraint constraint : constraints) {
            builder.append("\n").append(constraint);
        }
        return builder.toString();
    }
}
