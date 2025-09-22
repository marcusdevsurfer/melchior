package com.devshore.MelchiorAlexaSkill.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

@Component
public class MelchiorAlexaSkill implements RequestHandler {

    private final LaunchRequestHandler launchRequestHandler;
    private final ProgrammingQuestionHandler programmingQuestionHandler;
    private final FallbackIntentHandler fallbackIntentHandler;
    private final SessionEndedRequestHandler sessionEndedRequestHandler;
    private final CancelAndStopIntentHandler cancelAndStopIntentHandler;
    private final HelpIntentHandler helpIntentHandler;

    @Autowired
    public MelchiorAlexaSkill(
            LaunchRequestHandler launchRequestHandler,
            ProgrammingQuestionHandler programmingQuestionHandler,
            FallbackIntentHandler fallbackIntentHandler,
            SessionEndedRequestHandler sessionEndedRequestHandler,
            CancelAndStopIntentHandler cancelAndStopIntentHandler,
            HelpIntentHandler helpIntentHandler) {
        this.launchRequestHandler = launchRequestHandler;
        this.programmingQuestionHandler = programmingQuestionHandler;
        this.fallbackIntentHandler = fallbackIntentHandler;
        this.sessionEndedRequestHandler = sessionEndedRequestHandler;
        this.cancelAndStopIntentHandler = cancelAndStopIntentHandler;
        this.helpIntentHandler = helpIntentHandler;
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return true; // This handler can handle all requests, we'll delegate inside handle()
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        if (launchRequestHandler.canHandle(input)) {
            return launchRequestHandler.handle(input);
        } else if (programmingQuestionHandler.canHandle(input)) {
            return programmingQuestionHandler.handle(input);
        } else if (fallbackIntentHandler.canHandle(input)) {
            return fallbackIntentHandler.handle(input);
        } else if (sessionEndedRequestHandler.canHandle(input)) {
            return sessionEndedRequestHandler.handle(input);
        } else if (cancelAndStopIntentHandler.canHandle(input)) {
            return cancelAndStopIntentHandler.handle(input);
        } else if (helpIntentHandler.canHandle(input)) {
            return helpIntentHandler.handle(input);
        }
        return Optional.empty();
    }

}
