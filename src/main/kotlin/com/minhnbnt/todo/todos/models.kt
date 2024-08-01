package com.minhnbnt.todo.todos

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name="todos")
data class Todo (

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id: Int?,

    var detail: String,

    @ColumnDefault("false")
    var isCompleted: Boolean?
)