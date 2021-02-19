package br.com.kaique.model.form

import io.micronaut.core.annotation.Introspected
import javax.annotation.Nullable
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class TodoForm(

        @get: NotBlank
        @get: Size(min=3, max = 100)
        val description: String,

)
data class TodoUpdateForm(

        @get: Size(min=3, max = 100)
        val description: String?,

        val done: Boolean?

)
