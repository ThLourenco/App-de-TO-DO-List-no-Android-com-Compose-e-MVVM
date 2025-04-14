package com.example.todo_list.domain

data class Todo(
    //To do é só um nome de classe comum pra representar tarefas ou itens de uma lista de afazeres.
    // "To do", que significa "a fazer" ou "para fazer
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