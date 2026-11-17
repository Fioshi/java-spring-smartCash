package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.spent.application.port.in.GetCategoriesUseCase;
import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;

import java.util.Arrays;
import java.util.List;

public class GetCategoriesUseCaseImpl implements GetCategoriesUseCase {

    @Override
    public List<SpentCategorie> execute() {
        return Arrays.asList(SpentCategorie.values());
    }
}
