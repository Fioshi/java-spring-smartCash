package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;
import fioshi.com.github.SmartCash.spent.repository.MonthlyExpenseRepository;
import fioshi.com.github.SmartCash.spent.repository.SpentRepository;
import fioshi.com.github.SmartCash.spent.service.validacao.SpentValidation;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class SpentServiceImp implements SpentService {

    private final SpentRepository spentRepository;
    private final UserRepository userRepository;
    private final MonthlyExpenseRepository monthlyExpenseRepository;
    private final List<SpentValidation> validation;

    public SpentServiceImp(
            SpentRepository spentRepository,
            UserRepository userRepository,
            MonthlyExpenseRepository monthlyExpenseRepository,
            List<SpentValidation> validation
    ) {
        this.spentRepository = spentRepository;
        this.userRepository = userRepository;
        this.monthlyExpenseRepository = monthlyExpenseRepository;
        this.validation = validation;
    }

    @Override
    @Transactional(rollbackOn = BusinessException.class)
    public void insertSpent(SpentDtoInsert dtoInsert) {
        validation.forEach(validation -> validation.validation(dtoInsert));

        var user = userRepository.getReferenceById(dtoInsert.idUser());

        var currentMonth = LocalDate.now().getMonth();
        var currentYear = LocalDate.now().getYear();

        var monthlyExpenses = monthlyExpenseRepository.findAllByUserId(dtoInsert.idUser());
        var spent = new Spent(dtoInsert, user);

        spentRepository.save(spent);

        if (spent.getInstallments() > 1){
            for (int i = 0; i < spent.getInstallments(); i++) {
                int finalI = i;

                var optionalMonthlyExpense = monthlyExpenses
                        .stream()
                        .filter(month -> month.getMonth() == currentMonth.plus(finalI) && month.getYear() == currentYear)
                        .findFirst();
                var monthlyExpense = optionalMonthlyExpense.orElseGet(
                    () -> new MonthlyExpense(
                            currentYear, currentMonth.plus(finalI), new LinkedList<>(), user
                    )
            );
                monthlyExpense.addSpent(spent);
                monthlyExpenseRepository.save(monthlyExpense);
            }
        }
    }
}
