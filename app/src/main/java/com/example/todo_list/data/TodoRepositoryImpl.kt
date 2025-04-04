package com.example.todo_list.data

import com.example.todo_list.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {
    //implementar os membors
    override suspend fun insert(title: String, description: String) {
        val entity = TodoEntity(title = title, descripition = description, isCompleted = false)
        dao.insert(entity)
    }

    override suspend fun updataCompletd(id: Long, iscompleted: Boolean) {
       val existingTodo = dao.getBy(id) ?: return
        val updateEntity = existingTodo.copy(isCompleted = iscompleted)
        dao.insert(updateEntity)
    }

    override suspend fun delete(id: Long) {
        val existingTodo = dao.getBy(id)?: return
        dao.delete(existingTodo)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map{ entites -> //mapeamento do flow
            entites.map{entity -> //mapeamento da entity
                Todo( //colocando no todo
                    id = entity.id,
                    title = entity.title,
                    description = entity.descripition,
                    isCompleted = entity.isCompleted
                )

            }

        }
    }

    override suspend fun getBy(id: Long): Todo? {
        return dao.getBy(id)?.let { entity ->
            Todo( //colocando no todo
                id = entity.id,
                title = entity.title,
                description = entity.descripition,
                isCompleted = entity.isCompleted
            )
        }
    }


}