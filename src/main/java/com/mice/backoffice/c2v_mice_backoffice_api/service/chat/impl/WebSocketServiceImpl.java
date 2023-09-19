package com.mice.backoffice.c2v_mice_backoffice_api.service.chat.impl;

import com.google.gson.Gson;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.WebSocketConfigDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.MessageSendDto;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;

import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.time.Duration;


@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {
    // Service

    // Repository

    @Value("${chatServer.domain}")
    private String webSocketUrlFormat;

    @Value("${chatServer.timeout}")
    private long timeout;

    private final Gson gson;

    @Override
    public void sendMessage(MessageSendDto messageSendDto, WebSocketConfigDto webSocketConfigDto) throws Exception {
        String webSocketUrl = String.format(webSocketUrlFormat, webSocketConfigDto.getDeviceId(), webSocketConfigDto.getAppId(), webSocketConfigDto.getUserId());
        URI url = new URI(webSocketUrl);

        String message = gson.toJson(messageSendDto);
        Flux<String> input = Flux.just(message);

        WebSocketClient client = new ReactorNettyWebSocketClient();

        EmitterProcessor<String> output = EmitterProcessor.create();

        Mono<Void> sessionMono = client.execute(url, session ->
                session.send(input.map(session::textMessage))
                        .then(session
                                .receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .subscribeOn(Schedulers.parallel())
                                .then())
                        .then()
        );

        Flux<String> stringFlux = output.doOnSubscribe(s -> sessionMono.subscribe());

        String result = stringFlux.blockFirst(Duration.ofMillis(5000));

        System.out.println(result);
    }
}
