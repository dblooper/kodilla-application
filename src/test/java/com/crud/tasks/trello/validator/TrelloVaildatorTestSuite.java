package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloVaildatorTestSuite {

    @Autowired
    private TrelloVaildator trelloVaildator;

    @Test
    public void boardValidatorTest() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "Test", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("2", "TEST", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("3", "my_tasks", new ArrayList<>()));

        //When
        List<TrelloBoard> properBoards = trelloVaildator.valiDateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, properBoards.size());
        assertEquals("3", properBoards.get(0).getId());
    }

}