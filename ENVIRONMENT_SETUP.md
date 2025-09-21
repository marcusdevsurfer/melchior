# Configuración de Variables de Entorno

## Variables Requeridas

### 1. SPRING_AI_OPENAI_API_KEY
- **Descripción**: API Key de OpenAI para generar respuestas de IA
- **Obtener**: https://platform.openai.com/api-keys
- **Ejemplo**: `sk-proj-...`

### 2. ALEXA_SKILL_ID
- **Descripción**: ID único de tu Alexa Skill
- **Obtener**: Amazon Developer Console > Tu Skill > Endpoint
- **Ejemplo**: `amzn1.ask.skill.xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`

## Configuración Local

### Opción 1: Variables de entorno del sistema
```bash
export SPRING_AI_OPENAI_API_KEY="tu-api-key"
export ALEXA_SKILL_ID="tu-skill-id"
```

### Opción 2: Archivo .env (si usas IDE que lo soporte)
```bash
SPRING_AI_OPENAI_API_KEY=tu-api-key
ALEXA_SKILL_ID=tu-skill-id
```

## Configuración en Heroku

```bash
heroku config:set SPRING_AI_OPENAI_API_KEY="tu-api-key"
heroku config:set ALEXA_SKILL_ID="tu-skill-id"
```

## Seguridad

⚠️ **NUNCA** subas estos valores al repositorio Git
- Usa variables de entorno
- Mantén los valores seguros
- No compartas las API keys
