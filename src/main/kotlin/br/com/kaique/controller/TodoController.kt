package br.com.kaique.controller

import br.com.kaique.component.UserComponent
import br.com.kaique.model.entity.Todo
import br.com.kaique.model.form.TodoForm
import br.com.kaique.model.form.TodoUpdateForm
import br.com.kaique.repository.TodoRepository
import br.com.kaique.repository.UserRepository
import io.micronaut.data.annotation.Query
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.utils.SecurityService
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid


@Validated
@Controller("todos")
@Secured(SecurityRule.IS_AUTHENTICATED)
class TodoController(private val todoRepository: TodoRepository, private val userComponent: UserComponent) {

    @Get
    fun getTodos(@QueryValue userCpf: Long?): HttpResponse<List<Todo>> {

        userComponent.getLoggedUser()?.let { user ->
            return HttpResponse.ok (if (userComponent.isAdmin(user)) {
                // If user has role admin get from all users
                userCpf?.let { cpf ->
                    todoRepository.findByUserCpf(cpf)
                } ?: todoRepository.findAll()

            } else {
                // otherwise he should only see his todos
                todoRepository.findByUserCpf(user.cpf)
            })
        }

        return HttpResponse.notFound()
    }

    @Post
    fun addTodo(@Valid @Body todoForm: TodoForm): HttpResponse<Todo> {
        userComponent.getLoggedUser()?.let { return HttpResponse.created(todoRepository.save(todoForm, it)) }
        return HttpResponse.notFound()
    }

    @Put("/{id}")
    @Transactional
    fun modifyTodo(@Valid @Body todoForm: TodoUpdateForm, @PathVariable id: Int): HttpResponse<Todo> {

        userComponent.getLoggedUser()?.let { user ->
           if (userComponent.isAdmin(user)) {
               // If user has role admin get todo from all users
                todoRepository.findById(id).takeIf { it.isPresent }?.get()
           } else {
               // otherwise he should only be able to modify his todos
                todoRepository.findByUserCpfAndId(user.cpf, id).takeIf { it.isPresent }?.get()
           }?.let { todo ->
                todo.apply {
                    description = todoForm.description ?: description
                    done = todoForm.done ?: done
                }
                return HttpResponse.ok(todo)
           }
        }
        return HttpResponse.notFound()
    }

    @Delete("/{id}")
    fun deleteTodo(@PathVariable id: Int): HttpResponse<Unit> {
        userComponent.getLoggedUser()?.let { user ->
            if (userComponent.isAdmin(user)) {
                // if user has role admin he may delete any Todo
                todoRepository.findById(id).takeIf { it.isPresent }?.get()
            } else {
                // otherwise he should only delete his todos
                todoRepository.findByUserCpfAndId(user.cpf, id).takeIf { it.isPresent }?.get()
            }?.let {
                todoRepository.deleteById(id)
                return HttpResponse.ok()
            }
        }
        return HttpResponse.notFound()
    }

}