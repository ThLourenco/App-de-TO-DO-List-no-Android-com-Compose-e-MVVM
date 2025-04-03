package com.example.todo_list.domain

data class Todo(
    val id:  Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean
)

//fake objects
val todo1= Todo(
    1,
    title = "todo1",
    description = "Description for todo 1",
    isCompleted = false
)
val todo2= Todo(
    1,
    title = "todo1",
    description = "Description for todo 1",
    isCompleted = true
)

val todo3= Todo(
    1,
    title = "todo1",
    description = "Description for todo 1",
    isCompleted = false
)