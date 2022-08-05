package com.litsynp.todoapp.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Todo(
    @Id
    @GeneratedValue
    var id: Long? = null,

    var title: String,

    var description: String,
) {

    fun update(title: String, description: String) {
        this.title = title;
        this.description = description
    }
}
