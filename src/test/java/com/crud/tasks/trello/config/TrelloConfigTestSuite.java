package com.crud.tasks.trello.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloConfigTestSuite {

    @Autowired
    private TrelloConfig trelloConfig;

    @Test
    public void trelloConfigParametersTest() {
        //Given
        String ENDPOINT = "https://api.trello.com/1";
        String KEY = "c0dc669990a552c8c120d6f3b137194b";
        String TOKEN = "27fd7e209529730107afdc5408c43e62c37e9e5437d2cf04f6acf6e19bf42ead";
        String USERNAME = "danielkoplenski";

        //When&Then
        assertEquals(ENDPOINT, trelloConfig.getTrelloApiEndpoint());
        assertEquals(KEY, trelloConfig.getTrelloAppKey());
        assertEquals(TOKEN, trelloConfig.getTrelloToken());
        assertEquals(USERNAME, trelloConfig.getUsername());
    }
}