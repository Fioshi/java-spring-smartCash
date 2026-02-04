package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.domain.dto.AdditionalIncomeDtoInsert;
import fioshi.com.github.SmartCash.spent.domain.dto.AdditionalIncomeDtoList;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;
import fioshi.com.github.SmartCash.spent.repository.AdditionalIncomeRepository;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public class AdditionalIncomeServiceImp implements AdditionalIncomeService {

    private final AdditionalIncomeRepository additionalIncomeRepository;
    private final UserRepository userRepository;

    public AdditionalIncomeServiceImp(
            AdditionalIncomeRepository additionalIncomeRepository,
            UserRepository userRepository
    ) {
        this.additionalIncomeRepository = additionalIncomeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AdditionalIncome insertIncome(Long userId, AdditionalIncomeDtoInsert dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

        var income = new AdditionalIncome(
                dto.valor(),
                dto.descricao(),
                dto.ano(),
                dto.mes(),
                user
        );

        return additionalIncomeRepository.save(income);
    }

    @Override
    public List<AdditionalIncomeDtoList> listByUser(Long userId) {
        return additionalIncomeRepository.findByUserId(userId).stream()
                .map(AdditionalIncomeDtoList::new)
                .toList();
    }

    @Override
    public Double getAdditionalIncomeByMonth(Long userId, int ano, Month mes) {
        return additionalIncomeRepository.findByUserIdAndAnoAndMes(userId, ano, mes).stream()
                .mapToDouble(AdditionalIncome::getValor)
                .sum();
    }

    @Override
    @Transactional
    public void deleteIncome(Long id) {
        var income = additionalIncomeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Rendimento extra nao encontrado"));
        additionalIncomeRepository.delete(income);
    }
}
