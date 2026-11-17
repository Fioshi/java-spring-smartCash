package fioshi.com.github.SmartCash.user.domain.model;

/**
 * Pure domain model representing a system user.
 */
public class User {

    private final Long id;
    private final String nome;
    private final String surname;
    private final String cpf;
    private final String email;
    private final String password;
    private final Double salarioMensal;

    public User(Long id, String nome, String surname, String cpf, String email, String password, Double salarioMensal) {
        this.id = id;
        this.nome = nome;
        this.surname = surname;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.salarioMensal = salarioMensal;
    }

    public static User createNew(String nome, String surname, String cpf, String email, String encodedPassword, Double salarioMensal) {
        return new User(null, nome, surname, cpf, email, encodedPassword, salarioMensal);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSurname() {
        return surname;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Double getSalarioMensal() {
        return salarioMensal;
    }
}
