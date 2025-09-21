package com.devshore.MelchiorAlexaSkill.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

@Component
public class CancelAndStopIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input
                .matches(Predicates.intentName("AMAZON.CancelIntent").or(Predicates.intentName("AMAZON.StopIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Adiós. Si tienes otra pregunta, no dudes en consultarme.";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Eva", speechText)
                .build();
    }

}
