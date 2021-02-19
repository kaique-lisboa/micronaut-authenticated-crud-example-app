package br.com.kaique.model.form

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.*

@Introspected
data class UserForm(

        @NotNull
        val cpf: Long,

        @NotBlank
        var name: String,

        @Email
        var email: String,

        @Size(min=8)
        val password: String,

        @Pattern(regexp="/d{8}")
        val cep: String,

        val roles: List<String>?
)