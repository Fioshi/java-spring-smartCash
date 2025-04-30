package fioshi.com.github.SmartCash.infra;

import fioshi.com.github.SmartCash.service.validacao.InstallmentsValidation;
import fioshi.com.github.SmartCash.service.validacao.SpentValidation;
import fioshi.com.github.SmartCash.service.validacao.ValueValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
