package fioshi.com.github.SmartCash.service;

import fioshi.com.github.SmartCash.domain.MonthlyExpense;
import fioshi.com.github.SmartCash.domain.Spent;
import fioshi.com.github.SmartCash.domain.dto.SpentDtoInsert;
import fioshi.com.github.SmartCash.repository.MonthlyExpenseRepository;
import fioshi.com.github.SmartCash.repository.SpentRepository;
import fioshi.com.github.SmartCash.repository.UserRepository;
import fioshi.com.github.SmartCash.service.validacao.SpentValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
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
    public void insertSpent(SpentDtoInsert dtoInsert) {

        validation.forEach(validation -> validation.validation(dtoInsert));

        var user = userRepository.getReferenceById(dtoInsert.idUser());

        var currentMonth = LocalDate.now().getMonth();
        var currentYear = LocalDate.now().getYear();

        var monthlyExpenses = monthlyExpenseRepository.findAllByUserId(dtoInsert.idUser());
        var spent = new Spent(dtoInsert);

        var optionalMonthlyExpense = monthlyExpenses
                .stream()
                .filter(month -> month.getMonth() == currentMonth && month.getYear() == currentYear)
                .findFirst();

        //jeito mais clean para instancia um objeto caso o optinal seja null
        var monthlyExpense = optionalMonthlyExpense.orElseGet(
                () -> new MonthlyExpense(
                        currentYear, currentMonth, new LinkedList<>(), user
                )
        );
        monthlyExpense.addSpent(spent);
        monthlyExpenseRepository.save(monthlyExpense);
        spentRepository.save(spent);
    }
}
