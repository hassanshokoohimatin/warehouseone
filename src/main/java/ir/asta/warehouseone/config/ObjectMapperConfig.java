package ir.asta.warehouseone.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer mapEmptyStringToEnum(){
        return
                builder -> builder.postConfigurer(objectMapper -> {
                   objectMapper.coercionConfigFor(LogicalType.Enum)
                           .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
                });
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer mapUnmatchedStringToEnum(){
        return
                builder -> builder.postConfigurer(objectMapper -> {
                    objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
                });
    }
}



