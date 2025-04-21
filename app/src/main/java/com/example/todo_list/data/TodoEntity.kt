package com.example.todo_list.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    // é sempre bom ter base de dados em cada camada para reprensentar os dados daquela camada

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val descripition: String?,
    val isCompleted: Boolean
)
