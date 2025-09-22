package com.devshore.MelchiorAlexaSkill.handler;

import java.util.Optional;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

@Component
public class UniversalProgrammingHandler implements RequestHandler {
    private final ChatClient chatClient;

    public UniversalProgrammingHandler(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        // Manejar cualquier intent que no sea Launch, Help, Cancel, Stop, SessionEnded
        return input.matches(Predicates.intentName("QuestionIntent")) ||
               input.matches(Predicates.intentName("AMAZON.FallbackIntent")) ||
               (input.matches(Predicates.requestType(IntentRequest.class)) && 
                !input.matches(Predicates.intentName("AMAZON.HelpIntent")) &&
                !input.matches(Predicates.intentName("AMAZON.CancelIntent")) &&
                !input.matches(Predicates.intentName("AMAZON.StopIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        
        // Obtener la pregunta del usuario
        String userQuery = "ayuda con programación"; // Valor por defecto
        
        // Debug: Imprimir información
        System.out.println("=== DEBUG UniversalProgrammingHandler ===");
        System.out.println("Intent name: " + intentRequest.getIntent().getName());
        System.out.println("Intent slots: " + intentRequest.getIntent().getSlots());
        
        // Intentar obtener la pregunta de diferentes fuentes
        if (intentRequest.getIntent().getSlots() != null) {
            System.out.println("Available slots: " + intentRequest.getIntent().getSlots().keySet());
            
            // Buscar en diferentes posibles nombres de slots
            String[] possibleSlotNames = {"query", "question", "text", "input"};
            for (String slotName : possibleSlotNames) {
                if (intentRequest.getIntent().getSlots().get(slotName) != null) {
                    String slotValue = intentRequest.getIntent().getSlots().get(slotName).getValue();
                    if (slotValue != null && !slotValue.trim().isEmpty()) {
                        userQuery = slotValue;
                        System.out.println("Found " + slotName + " slot: " + userQuery);
                        break;
                    }
                }
            }
        }
        
        // Si no encontramos slots, usar el nombre del intent como pista
        if (userQuery.equals("ayuda con programación")) {
            String intentName = intentRequest.getIntent().getName();
            if (!intentName.equals("AMAZON.FallbackIntent")) {
                userQuery = "pregunta sobre " + intentName.toLowerCase();
            }
        }
        
        System.out.println("Final userQuery: " + userQuery);
        System.out.println("=== END DEBUG ===");
        
        String prompt = "Eres Melchior, el asistente de programación personal de Mark. Tu personalidad es la de un experto senior en desarrollo de software con más de 15 años de experiencia. Siempre te refieres a Mark por su nombre cuando le hablas directamente y en cada respuesta.\n\n"
                +
                "PERFIL PROFESIONAL:\n" +
                "- Especialista en múltiples lenguajes: Java, Python, JavaScript, TypeScript, C#, Go, Rust\n" +
                "- Experto en frameworks: Spring Boot, React, Angular, Vue.js, Django, Flask, .NET Core\n" +
                "- Conocimiento profundo en arquitecturas: microservicios, REST APIs, GraphQL, WebSockets\n" +
                "- Experto en bases de datos: SQL (PostgreSQL, MySQL), NoSQL (MongoDB, Redis, Cassandra)\n" +
                "- DevOps y cloud: AWS, Azure, Docker, Kubernetes, CI/CD, Terraform\n" +
                "- Metodologías ágiles: Scrum, Kanban, TDD, Clean Code, SOLID principles\n\n" +
                "ESTILO DE COMUNICACIÓN PARA VOZ:\n" +
                "- Respuestas concisas pero completas (máximo 2-3 párrafos)\n" +
                "- Explicaciones verbales claras sin mostrar código literal\n" +
                "- Describe la lógica y estructura en lugar de mostrar código\n" +
                "- Menciona nombres de clases, métodos y archivos importantes\n" +
                "- Explica patrones y mejores prácticas conceptualmente\n" +
                "- Usa un tono profesional pero amigable\n" +
                "- Cuando te dirijas a Mark, siempre di 'Mark' al inicio de tu respuesta\n\n" +
                "MANEJO DE CÓDIGO EN RESPUESTAS DE VOZ:\n" +
                "- NUNCA muestres código literal, solo explica la lógica\n" +
                "- Describe la estructura: 'necesitas crear una clase X con método Y'\n" +
                "- Menciona anotaciones importantes: 'usa la anotación RestController'\n" +
                "- Explica el flujo paso a paso verbalmente\n" +
                "- Para código complejo, ofrece enviarlo por otro medio\n" +
                "- Da ejemplos conceptuales en lugar de código real\n\n" +
                "EJEMPLOS DE RESPUESTAS CORRECTAS:\n" +
                "- 'Mark, para crear una API REST necesitas un controlador con RestController, un método con PostMapping, y usar RequestBody para recibir datos JSON'\n" +
                "- 'Mark, el problema requiere crear una clase Service con inyección de dependencias y manejar excepciones con try-catch'\n" +
                "- 'Mark, para autenticación JWT necesitas configurar Spring Security, crear un filtro personalizado y un servicio de tokens. ¿Quieres que te envíe el código completo por email?'";

        String aiResponse = chatClient.prompt()
                .system(prompt)
                .user(userQuery).call().content();

        return input.getResponseBuilder()
                .withSpeech(aiResponse)
                .withSimpleCard("Melchior contesta", aiResponse)
                .build();
    }
}
