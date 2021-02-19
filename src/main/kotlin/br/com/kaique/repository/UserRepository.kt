package br.com.kaique.repository

import br.com.kaique.model.entity.User
import br.com.kaique.model.form.UserForm
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import javax.transaction.Transactional

@Repository
abstract class UserRepository(private val encoder: PasswordEncoder, private val roleRepository: RoleRepository): JpaRepository<User, Int> {

    abstract fun findByEmail(email: String): List<User>

    @Transactional
    fun save(user: UserForm): User {

        val roles = user.roles?.mapNotNull { roleRepository.findByName(it).getOrNull(0) }

            var userEntity = User(
                    user.cpf,
                    user.name,
                    user.email,
                    encoder.encode(user.password),
                    user.cep,
                    roles ?: listOf()
            )

        userEntity = save(userEntity)
        roles?.forEach { role ->
            role.users.add(userEntity)
            roleRepository.update(role)
        }
        return userEntity
    }

    fun saveAll(users: List<UserForm>): List<User> {
         return users.map { user ->
            save(user)
        }
    }

}