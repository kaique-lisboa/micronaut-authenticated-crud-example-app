package br.com.kaique.repository

import br.com.kaique.model.entity.Role
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
abstract class RoleRepository: JpaRepository<Role, Int> {
    abstract fun findByName(name: String): List<Role>
}