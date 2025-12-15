package br.com.mixzyn.erp.model;

import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.mixzyn.erp.dto.LoginRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // criando a relacao muitos para muitos entre usuarios e roles
    // define a forma de comunicacao com o banco de dados, o EAGER sempre consulta o banco de dados
    @ManyToMany(fetch = FetchType.EAGER)

    // criando uma tabela intermediaria para relacionar usuarios e roles
    @JoinTable(
            // nome da tabela
            name = "tb_user_role",
            // nome da coluna que identifica a entidade
            joinColumns = @JoinColumn(name = "user_id"),
            // nome da coluna que identifica a outra entidade
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    
    private Set<Role> roles;

    // verifica se a senha da requisicao (sem cript) confere com a senha do banco de dados (cript)
    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
