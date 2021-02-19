package br.com.kaique.configuration.security

import br.com.kaique.model.entity.Role
import br.com.kaique.model.form.UserForm
import br.com.kaique.repository.RoleRepository
import br.com.kaique.repository.UserRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Property
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartupEventListener(private val userRepository: UserRepository, private val roleRepository: RoleRepository) {

    val Log = LoggerFactory.getLogger(StartupEventListener::class.java)

    // Obtain Initial users from application.yml
    @set:Inject
    @setparam:Property(name = "users.initial-users")
    var initialUsers: List<Map<String, Any>>? = null;

    // Obtain initial roles from application.yml
    @set:Inject
    @setparam:Property(name = "roles.initial-roles")
    var initialRoles: List<String>? = null

    @EventListener
    internal fun onStartupEvent(event: StartupEvent) {

        initialRoles?.let { roles ->
            val roleAdmin = roleRepository.saveAll(
                    roles.map { role ->
                        Role(
                                null,
                                role,
                                mutableListOf()
                        )
                    }
            )
            Log.info("Adding Roles: {}", roleAdmin)
        }

        initialUsers?.let { users ->

            val users = userRepository.saveAll(
                    users.map { user ->
                        ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                .registerModule(KotlinModule())
                                .convertValue(user, UserForm::class.java)
                    }
            )
            Log.info("Adding User: {}", users)
        }
    }
}