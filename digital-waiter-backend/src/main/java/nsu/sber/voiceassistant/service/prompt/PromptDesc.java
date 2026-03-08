package nsu.sber.voiceassistant.service.prompt;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PromptDesc {
    String  intent();
    String description();
    String entitiesHint() default "";
}