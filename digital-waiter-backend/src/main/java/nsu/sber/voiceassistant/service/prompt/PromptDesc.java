package nsu.sber.voiceassistant.service.prompt;

import nsu.sber.voiceassistant.model.IntentType;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PromptDesc {
    IntentType intent();
    String description();
    String entitiesHint() default "";
}