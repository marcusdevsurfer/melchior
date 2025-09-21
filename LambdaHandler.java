package com.devshore.MelchiorAlexaSkill;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;
import com.devshore.MelchiorAlexaSkill.handler.MelchiorAlexaSkill;

public class LambdaHandler extends SkillStreamHandler {
    
    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(new MelchiorAlexaSkill())
                .withSkillId(System.getenv().getOrDefault("alexa.skill.id"))
                .build();
    }
    
    public LambdaHandler() {
        super(getSkill());
    }
}
