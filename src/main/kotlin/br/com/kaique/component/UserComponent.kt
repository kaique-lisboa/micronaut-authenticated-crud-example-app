package br.com.kaique.component

import br.com.kaique.model.entity.User
import br.com.kaique.repository.UserRepository
import io.micronaut.context.annotation.Property
import io.micronaut.security.utils.SecurityService
import java.security.Principal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserComponent(private val securityService: SecurityService, private val userRepository: UserRepository) {

    @set:Inject
    @setparam:Property(name = "roles.admin-role-name")
    var adminRoleName: String? = null

    fun getLoggedUser(): User? {
        return securityService.username()?.get()?.let {
            userRepository.findByEmail(it)[0]
        }
    }

    fun isAdmin(user: User): Boolean {
        return user.roles.find { role -> role.name == adminRoleName } !== null
    }


}