package br.com.kaique.repository

import br.com.kaique.model.entity.Todo
import br.com.kaique.model.entity.User
import br.com.kaique.model.form.TodoForm
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*
import javax.transaction.Transactional

@Repository
abstract class TodoRepository: JpaRepository<Todo, Int> {

    abstract fun findByUserCpf(cpf: Long): List<Todo>
    abstract fun findByUserCpfAndId(cpf: Long, id: Int): Optional<Todo>


    @Transactional
    fun save(todoForm: TodoForm, user: User): Todo {
        var todo = Todo(null, todoForm.description, false, user);
        return save(todo)
    }

}