package com.example.todo_list.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo_list.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(title: String, description: String?, id:  Long? = null)

    suspend fun updataCompletd(id: Long, iscompleted: Boolean)

    suspend fun delete(id: Long)

    //forma reativa
    fun getAll(): Flow<List<Todo>>

    suspend fun getBy(id: Long): Todo?
}