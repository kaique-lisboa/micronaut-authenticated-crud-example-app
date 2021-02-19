package br.com.kaique.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.core.annotation.Introspected
import javax.persistence.*

@Entity
@Introspected
data class Role (

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        @Column
        val name: String,

        @ManyToMany(fetch = FetchType.LAZY)
        @JsonIgnore
        val users: MutableList<User> = mutableListOf()
        ) {
        override fun toString(): String {
                return ObjectMapper().writeValueAsString(this)
        }
}