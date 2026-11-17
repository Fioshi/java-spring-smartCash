package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;

import java.util.List;

public interface GetCategoriesUseCase {

    List<SpentCategorie> execute();
}
