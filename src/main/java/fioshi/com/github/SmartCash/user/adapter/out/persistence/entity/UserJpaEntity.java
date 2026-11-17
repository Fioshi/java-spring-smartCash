package fioshi.com.github.SmartCash.user.adapter.out.persistence.entity;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.SpentJpaEntity;
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
public class UserJpaEntity {

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
    private List<SpentJpaEntity> spentJpaEntities;

    public UserJpaEntity(Long id, String nome, String surname, String cpf, String email, String password, Double salarioMensal) {
        this.id = id;
        this.nome = nome;
        this.surname = surname;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.salarioMensal = salarioMensal;
    }
}
