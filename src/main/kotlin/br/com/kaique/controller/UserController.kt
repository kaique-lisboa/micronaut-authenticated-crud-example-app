package br.com.kaique.controller

import br.com.kaique.component.UserComponent
import br.com.kaique.model.entity.User
import br.com.kaique.repository.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import javax.annotation.security.RolesAllowed

@Controller("/users")
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserController(private val userComponent: UserComponent, private val userRepository: UserRepository) {

    @Get("/me")
    fun getLoggedUser(): HttpResponse<User> {
        return HttpResponse.ok(userComponent.getLoggedUser())
    }

    @Get()
    @RolesAllowed("ROLE_ADMIN")
    fun getUsers(): HttpResponse<List<User>> {
        return HttpResponse.ok(
                userRepository.findAll()
        )
    }

    @Get("/{cpf}")
    @RolesAllowed("ROLE_ADMIN")
    fun getUser(@PathVariable cpf: Int): HttpResponse<User> {
       return userRepository.findById(cpf).takeIf { it.isPresent }?.let {
           HttpResponse.ok(it.get())
       }?: HttpResponse.notFound()
    }

}