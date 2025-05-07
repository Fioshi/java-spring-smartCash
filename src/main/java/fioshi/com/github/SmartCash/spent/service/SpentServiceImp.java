package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.domain.dto.MonthlySpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoDetail;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;
import fioshi.com.github.SmartCash.spent.domain.model.Type;
import fioshi.com.github.SmartCash.spent.repository.MonthlyExpenseRepository;
import fioshi.com.github.SmartCash.spent.repository.SpentRepository;
import fioshi.com.github.SmartCash.spent.service.validacao.SpentValidation;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
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
        var currentYear = Year.now().getValue();

        var monthlyExpenses = monthlyExpenseRepository.findAllByUserId(dtoInsert.idUser());
        var spent = new Spent(dtoInsert, user);

        spentRepository.save(spent);

        if (spent.getInstallments() >= 1){
            for (int i = 0; i < spent.getInstallments(); i++) {
                if (currentMonth.plus(i) == Month.JANUARY){
                    currentYear = currentYear + 1;
                }

                int finalI = i;
                int finalCurrentYear = currentYear;

                var optionalMonthlyExpense = monthlyExpenses
                        .stream()
                        .filter(month -> month.getMonth() == currentMonth.plus(finalI)
                                && month.getYear() == finalCurrentYear)
                        .findFirst();

                var monthlyExpense = optionalMonthlyExpense.orElseGet(
                     () -> new MonthlyExpense(
                            finalCurrentYear, currentMonth.plus(finalI), new LinkedList<>(), user
                    )
            );
                monthlyExpense.addSpent(spent);
                monthlyExpenseRepository.save(monthlyExpense);
            }
        }
    }

    @Override
    public List<MonthlySpentDtoList> listMontlhySpenceFiltered(Long id) {
        return monthlyExpenseRepository
                .findAllByUserId(id)
                .stream()
                .map(MonthlySpentDtoList::new)
                .toList();
    }

    @Override
    @Transactional()
    public MonthlySpentDtoList getSpentResume(Long userId, Month month, int year) {
        var monthlyExpense = monthlyExpenseRepository.findByUserIdAndMonthAndYear(userId, month, year);
        if (monthlyExpense.isPresent())
            return new MonthlySpentDtoList(monthlyExpense.get());
        throw new BusinessException("Nao ha registros desse mes para esse usuario");
    }

    @Override
    public SpentDtoDetail getSpentDetail(Long userId ,Long spentId) {
        var spent = spentRepository.findByIdAndUserId(spentId, userId);
        if (spent.isPresent())
            return new SpentDtoDetail(spent.get());
        throw new BusinessException("Transacao nao encontrada");
    }
}
