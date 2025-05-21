package fioshi.com.github.SmartCash.user.domain.dto;

import fioshi.com.github.SmartCash.user.domain.model.User;

public record UserDtoDetail(
        String nome,
        String surname,
        String cpf,
        String email
) {
    public UserDtoDetail(User user){
        this(
                user.getNome(),
                user.getSurname(),
                user.getCpf(),
                user.getEmail()
        );
    }
}
