package br.com.kaique.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import io.micronaut.core.annotation.Introspected
import javax.persistence.*

@Entity
@Introspected
data class Todo(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        @Column
        var description: String,

        @Column
        var done: Boolean,

        @JsonIgnore
        @ManyToOne()
        val user: User,

)


