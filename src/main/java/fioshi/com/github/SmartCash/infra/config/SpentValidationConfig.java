package fioshi.com.github.SmartCash.infra.config;

import fioshi.com.github.SmartCash.spent.service.validacao.InstallmentsValidation;
import fioshi.com.github.SmartCash.spent.service.validacao.SpentValidation;
import fioshi.com.github.SmartCash.spent.service.validacao.ValueValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpentValidationConfig {

    @Bean
    public SpentValidation spentValidationIntallments(){
        return new InstallmentsValidation();
    }

    @Bean
    public SpentValidation spentValidationValue(){
        return new ValueValidation();
    }
}
