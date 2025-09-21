package com.devshore.MelchiorAlexaSkill.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

@Component
public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Puedes hacerme preguntas sobre cualquier tema de programación. Por ejemplo, puedes decir, 'explícame qué es una variable en Java'.";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Eva Ayuda", speechText)
                .withReprompt(speechText)
                .build();

    }

}
