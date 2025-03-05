package br.com.mixzyn.erp.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User extends AbstractEntity {
    
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // criando a relacao muitos para muitos entre usuarios e roles
    @ManyToMany(
        // definindo cascateamento para ALL, todas as alteracoes na tabela serao replicadas
        cascade = CascadeType.ALL,
        // define a forma de comunicacao com o banco de dados, o EAGER sempre consulta o banco de dados,
        // ja o LAZY consulta apenas quando utilizamos um atributo relacionado
        fetch = FetchType.EAGER
        )
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
}
