package br.com.kaique.configuration.security

import br.com.kaique.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*

import io.reactivex.Flowable

import org.reactivestreams.Publisher
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.Nullable

import javax.inject.Singleton


@Singleton
class UserPasswordAuthenticationProvider(private var userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) : AuthenticationProvider {


    override fun authenticate(@Nullable httpRequest: HttpRequest<*>? , req: AuthenticationRequest<*, *>?): Publisher<AuthenticationResponse> {

        if (req == null) return Flowable.just(AuthenticationFailed())

        val username = req.identity.toString()
        val password = req.secret.toString()

        val user = userRepository.findByEmail(username).getOrNull(0)

        return if (user != null && passwordEncoder.matches(password, user.password)) {
            val details = UserDetails(username, user.roles.map { role -> role.name })
            Flowable.just(details)
        } else {
            Flowable.error(AuthenticationException(AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)))
        }
    }

}