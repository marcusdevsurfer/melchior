package com.devshore.MelchiorAlexaSkill.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.devshore.MelchiorAlexaSkill.handler.MelchiorAlexaSkill;


@RestController
public class AlexaController {

    private static final Logger logger = LoggerFactory.getLogger(AlexaController.class);
    
    private final Skill skill;
    
    @Value("${alexa.skill.id:amzn1.ask.skill.fcacc88d-f1aa-4193-ab2f-094c5799014a}")
    private String skillId;

    public AlexaController(MelchiorAlexaSkill melchiorAlexaSkill, 
                          @Value("${alexa.skill.id}") String skillId) {
        this.skillId = skillId;
        // Construye la skill usando la clase Skills.
        this.skill = Skills.standard()
                .addRequestHandlers(melchiorAlexaSkill)
                .withSkillId(skillId)
                .build();
    }

    @PostMapping("/alexa-skill")
    public ResponseEntity<ResponseEnvelope> handleAlexaRequest(@RequestBody RequestEnvelope requestEnvelope) {
        try {
            logger.info("Received Alexa request: {}", requestEnvelope.getRequest().getType());
            
            // Validate request envelope
            if (requestEnvelope == null || requestEnvelope.getRequest() == null) {
                logger.error("Invalid request envelope received");
                return ResponseEntity.badRequest().build();
            }
            
            // Validate skill ID if present
            if (requestEnvelope.getSession() != null && 
                requestEnvelope.getSession().getApplication() != null &&
                requestEnvelope.getSession().getApplication().getApplicationId() != null) {
                
                String requestSkillId = requestEnvelope.getSession().getApplication().getApplicationId();
                if (!skillId.equals(requestSkillId)) {
                    logger.warn("Skill ID mismatch. Expected: {}, Received: {}", skillId, requestSkillId);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
            
            ResponseEnvelope response = skill.invoke(requestEnvelope);
            logger.info("Successfully processed Alexa request");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error processing Alexa request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Alexa Skill is running");
    }
}
