package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value(("${trello.api.endpoint.prod}"))
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String username;


    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                buildTrelloUrl(trelloApiEndpoint,trelloAppKey,trelloToken,username,"all","name", "id", "url"),
                TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElse(new ArrayList<>());
    }

    private URI buildTrelloUrl(String trelloApiEndpoint, String trelloAppKey, String trelloToken, String username,String listsParameter, String... fieldsOfBoard) {

        StringBuilder fieldsBuilder = new StringBuilder();
        for (String field : fieldsOfBoard) {
            fieldsBuilder.append(field);
            fieldsBuilder.append(",");
        }

        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fieldsOfBoard", fieldsBuilder.toString())
                .queryParam("lists", listsParameter)
                .build().encode().toUri();
    }
}
