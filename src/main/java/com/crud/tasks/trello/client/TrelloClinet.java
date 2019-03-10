package com.crud.tasks.trello.client;

import com.crud.tasks.controller.BoardNotFoundException;
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
public class TrelloClinet {

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

    public List<TrelloBoardDto> getTrelloBoards() throws BoardNotFoundException {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                buildTrelloUrl(trelloApiEndpoint,trelloAppKey,trelloToken,username,"name", "id", "url"),
                TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElseThrow(BoardNotFoundException::new);
    }

    private URI buildTrelloUrl(String trelloApiEndpoint, String trelloAppKey, String trelloToken, String username, String... fields) {

        StringBuilder fieldsBuilder = new StringBuilder();
        for (String field : fields) {
            fieldsBuilder.append(field);
            fieldsBuilder.append(",");
        }

        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", fieldsBuilder.toString())
                .build().encode().toUri();
    }
}
