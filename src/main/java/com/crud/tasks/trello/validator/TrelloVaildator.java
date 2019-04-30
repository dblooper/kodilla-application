package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloVaildator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloVaildator.class);
    public static final String VALIDATION_FAILED = "Someone is testing my app...";
    public static final String VALIDATION_PASSED = "Application works properly, card added";

    public void validateCard(final TrelloCard trelloCard) {
        if(trelloCard.getName().contains("test")) {
            LOGGER.info(VALIDATION_FAILED);
        } else {
            LOGGER.info(VALIDATION_PASSED);
        }
    }

    public List<TrelloBoard> valiDateTrelloBoards(List<TrelloBoard> trelloBoards) {
        LOGGER.info("Starting filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        LOGGER.info("Boards have been filtered. Current list size: " + filteredBoards.size());
        return filteredBoards;
    }
}
