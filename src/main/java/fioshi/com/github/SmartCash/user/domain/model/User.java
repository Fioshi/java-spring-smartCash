package fioshi.com.github.SmartCash.user.domain.model;

import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String surname;

    private String cpf;

    private String email;

    private String password;

    private Double salarioMensal;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Spent> spents;

    public User(UserDtoSignUp dtoSignUp) {
        this.nome = dtoSignUp.name();
        this.surname = dtoSignUp.surname();
        this.cpf = dtoSignUp.cpf();
        this.email = dtoSignUp.email();
        this.password = dtoSignUp.password();
        this.salarioMensal = dtoSignUp.salarioMensal();
    }

    public User(UserDtoSignUp dtoSignUp, String encodedPassword) {
        this.nome = dtoSignUp.name();
        this.surname = dtoSignUp.surname();
        this.cpf = dtoSignUp.cpf();
        this.email = dtoSignUp.email();
        this.password = encodedPassword;
        this.salarioMensal = dtoSignUp.salarioMensal();
    }
}
