package pl.marczynski.pwr.si.csp.board.skyscraper;

import org.junit.Test;

public class SkyscraperBoardTest {
    @Test
    public void shouldInitializeFutoshikiBoard() {
        //given
        String fileName = "test_data/skyscrapper_5_3.txt";

        //when
        SkyscraperBoard skyscraperBoard = SkyscraperBoard.initializeFromFile(fileName);

        //then
        System.out.println(skyscraperBoard);
    }
}
