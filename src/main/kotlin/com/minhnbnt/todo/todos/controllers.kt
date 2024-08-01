package com.minhnbnt.todo.todos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping("/todos")
class TodoController
@Autowired constructor(val todosRepo: TodoRepository) {

    @GetMapping
    fun getAll() = todosRepo.findAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun newTodo(@Validated @RequestBody todo: Todo) {
        todosRepo.save(todo)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): Todo {
        return todosRepo.findById(id).getOrElse { ->
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeDetail(
        @Validated @RequestBody payload: Todo,
        @PathVariable id: Int
    ) {
        val target = this.getById(id)

        target.detail = payload.detail
        todosRepo.save(target)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun complete(@PathVariable id: Int) {
        val target = this.getById(id)

        target.isCompleted = true
        todosRepo.save(target)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        val target = this.getById(id)
        todosRepo.delete(target)
    }
}