package pl.marczynski.pwr.si.csp;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class MainTest {
    
    @Test
    public void evaluateTestData() {
        String directory = "test_data";
        List<String> fileNames = Main.getFilesInDirectory(directory);
        fileNames.sort(Comparator.naturalOrder());
        Main.findSolutionsforFiles(directory, fileNames);
    }

    @Test
    public void evaluateResearchData() {
        String directory = "research_data";
        List<String> fileNames = Main.getFilesInDirectory(directory);
        fileNames.sort(Comparator.naturalOrder());
        Main.findSolutionsforFiles(directory, fileNames);
    }
}
