package br.com.kaique.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.core.annotation.Introspected
import io.micronaut.security.authentication.UserDetails
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

@Entity
@Introspected
data class User (

        @Id
        val cpf: Long,

        @Column
        var name: String,

        @Column
        @Email
        var email: String,

        @JsonIgnore
        @Column
        val password: String,

        @Column
        @Pattern(regexp="/d{8}")
        val cep: String,

        @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
        val roles: List<Role> = listOf(),

        @JsonIgnore
        @OneToMany(mappedBy= "user")
        val todos: List<Todo> = listOf(),

        ) {
        override fun toString(): String {
                return ObjectMapper().writeValueAsString(this)
        }
}
