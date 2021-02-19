package br.com.kaique.configuration.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.inject.Singleton

@Singleton
class BCryptPasswordEncoderService: PasswordEncoder {

    private val delegate: PasswordEncoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: CharSequence?): String {
        return delegate.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return delegate.matches(rawPassword, encodedPassword)
    }


}