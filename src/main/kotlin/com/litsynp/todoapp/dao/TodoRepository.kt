package com.litsynp.todoapp.dao

import com.litsynp.todoapp.domain.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {

}
