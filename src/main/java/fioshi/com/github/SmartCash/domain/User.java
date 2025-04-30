package fioshi.com.github.SmartCash.domain;

import fioshi.com.github.SmartCash.domain.dto.UserDtoSignUp;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Spent> spents;

    public User(UserDtoSignUp dtoSignUp) {
        this.nome = dtoSignUp.name();
        this.surname = dtoSignUp.surname();
        this.cpf = dtoSignUp.cpf();
        this.email = dtoSignUp.email();
        this.password = dtoSignUp.password();
    }
}
