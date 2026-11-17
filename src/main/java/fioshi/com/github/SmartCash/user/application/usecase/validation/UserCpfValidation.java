package fioshi.com.github.SmartCash.user.application.usecase.validation;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase.SignUpUserCommand;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;

public class UserCpfValidation implements UserSignUpValidation {

    private final UserRepositoryPort userRepositoryPort;

    public UserCpfValidation(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void validate(SignUpUserCommand command) {
        String cpf = command.cpf();

        if (userRepositoryPort.existsByCpf(cpf)) {
            throw new BusinessException("O CPF deve ser único no sistema");
        }

        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new BusinessException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new BusinessException("CPF inválido (todos os dígitos são iguais)");
        }

        int[] numeros = cpf.chars().map(c -> c - '0').toArray();

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
