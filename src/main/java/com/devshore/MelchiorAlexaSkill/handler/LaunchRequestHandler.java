package com.devshore.MelchiorAlexaSkill.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;


@Component
public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Hola, soy Melchor, tu asistente para dudas de programación. ¿En qué puedo ayudarte?";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Melchor", speechText)
                .withReprompt("¿Tienes alguna pregunta de programación?")
                .build();
    }

}
