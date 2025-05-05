package fioshi.com.github.SmartCash.user.service.validation;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.repository.UserRepository;

public class CpfValidation implements UserValidation {

    private final UserRepository userRepository;

    public CpfValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validar(UserDtoSignUp dtoSignUp) {
        if (userRepository.existsByCpf(dtoSignUp.cpf())) {
            throw new BusinessException("O CPF deve ser único no sistema");
        }

        if (dtoSignUp.cpf() == null || !dtoSignUp.cpf().matches("\\d{11}")) {
            throw new BusinessException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (dtoSignUp.cpf().chars().distinct().count() == 1) {
            throw new BusinessException("CPF inválido (todos os dígitos são iguais)");
        }

        int[] numeros = dtoSignUp.cpf().chars().map(c -> c - '0').toArray();

        int dv1 = calcularDigito(numeros, 10);
        int dv2 = calcularDigito(numeros, 11);

        if (dv1 != numeros[9] || dv2 != numeros[10]) {
            throw new BusinessException("CPF inválido (dígitos verificadores incorretos)");
        }
    }

    private int calcularDigito(int[] numeros, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < pesoInicial - 1; i++) {
            soma += numeros[i] * (pesoInicial - i);
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
