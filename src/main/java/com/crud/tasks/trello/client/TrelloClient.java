package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrelloConfig trelloConfig;

    public List<TrelloBoardDto> getTrelloBoards() {

        URI url = buildTrelloUrl(trelloConfig.getTrelloApiEndpoint(),trelloConfig.getTrelloAppKey(), trelloConfig.getTrelloToken(),trelloConfig.getUsername(),"all","name", "id", "url");

        try{
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElse(new ArrayList<>());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }

    }

    private URI buildTrelloUrl(String trelloApiEndpoint, String trelloAppKey, String trelloToken, String username,String listsParameter, String... fieldsOfBoard) {

        StringBuilder fieldsBuilder = new StringBuilder();
        if(fieldsOfBoard.length > 1) {
            for (int i = 0; i < fieldsOfBoard.length - 1; i++) {
                fieldsBuilder.append(fieldsOfBoard[i]);
                fieldsBuilder.append(",");
            }
            fieldsBuilder.append(fieldsOfBoard[fieldsOfBoard.length - 1]);
        } else {
            fieldsBuilder.append(fieldsOfBoard.length);
        }

        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", fieldsBuilder.toString())
                .queryParam("lists", listsParameter)
                .build().encode().toUri();
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();

        return restTemplate.postForObject(url,null, CreatedTrelloCardDto.class);
    }
}
