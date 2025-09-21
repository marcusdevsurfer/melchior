package com.devshore.MelchiorAlexaSkill.skill;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.devshore.MelchiorAlexaSkill.handler.CancelAndStopIntentHandler;
import com.devshore.MelchiorAlexaSkill.handler.HelpIntentHandler;
import com.devshore.MelchiorAlexaSkill.handler.LaunchRequestHandler;
import com.devshore.MelchiorAlexaSkill.handler.ProgrammingQuestionHandler;
import com.devshore.MelchiorAlexaSkill.handler.SessionEndedRequestHandler;

@Component
public class MySkillStreamHandler extends SkillStreamHandler {

    @Value("alexa.skill.id")
    private static String skillId;
    
    @Autowired
    private LaunchRequestHandler launchRequestHandler;

    @Autowired
    private ProgrammingQuestionHandler programmingQuestionHandler;

    @Autowired
    private SessionEndedRequestHandler sessionEndedRequestHandler;

    @Autowired
    private CancelAndStopIntentHandler cancelAndStopIntentHandler;

    @Autowired
    private HelpIntentHandler helpIntentHandler;

    @Autowired
    public MySkillStreamHandler(Set<com.amazon.ask.dispatcher.request.handler.RequestHandler> handlers) {
        super(getSkill(handlers));
    }

    private static Skill getSkill(Set<com.amazon.ask.dispatcher.request.handler.RequestHandler> handlers) {
        return Skills.standard()
                .addRequestHandlers(handlers.toArray(new com.amazon.ask.dispatcher.request.handler.RequestHandler[0]))
                .withSkillId(skillId)
                .build();
    }
}
