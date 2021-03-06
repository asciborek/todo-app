package com.github.asciborek.todolist.api

import com.github.asciborek.todolist.ToDo
import com.github.asciborek.todolist.dao.TodoDAO
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
class ToDoController(private val dao: TodoDAO) {

    @GetMapping("/todos/{id}")
    fun getOne(@PathVariable("id") id: Int): ResponseEntity<*> {
        val todo = dao.findById(id)
        return if (todo != null) ResponseEntity(todo, HttpStatus.OK) else ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @GetMapping("/todos")
    fun getAll() : Collection<ToDo> {
        return dao.findAll()
    }

    @PostMapping("/todos")
    fun create(@RequestBody toDo: ToDo, uriBuilder: UriComponentsBuilder): ResponseEntity<Int> {
        val id = dao.save(toDo)
        val location = uriBuilder.path("/todos/{id}").buildAndExpand(id).toUri()
        val headers = HttpHeaders()
        headers.location = location
        return ResponseEntity(id, headers, HttpStatus.CREATED)
    }

    @PatchMapping("/todos/{id}")
    fun updateDoneFlag(@PathVariable("id") id : Int, @RequestParam("done") done: Boolean) : ResponseEntity<Unit> {
        return if (dao.setDoneFlag(id, done)) ResponseEntity(HttpStatus.NO_CONTENT) else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/todos/{id}")
    fun delete(@PathVariable("id") id: Int) : ResponseEntity<Unit> {
       return if (dao.delete(id)) ResponseEntity(HttpStatus.NO_CONTENT) else ResponseEntity(HttpStatus.NOT_FOUND)
    }

}