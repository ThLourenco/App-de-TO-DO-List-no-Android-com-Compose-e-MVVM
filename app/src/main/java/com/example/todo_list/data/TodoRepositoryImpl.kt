package com.example.todo_list.data

import com.example.todo_list.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {
    //implementar os membors
    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = id?.let { //verifico de ele noa ofr vazio
            dao.getBy(it)?.copy(// usando um copy significa se ele retorna alguma coisa, quer dizer
                //que nao esta nulo, entao nao precisa usar o let para verificar
                    title = title,
                    descripition = description
            )
        } ?: TodoEntity( //nova entidade para fazer o insert com id novo
            title = title,
            descripition = description,
            isCompleted = false)

        dao.insert(entity)
    }
    // Função usada para atualizar apenas o status "isCompleted" de uma tarefa (ex: checkbox).
    // Primeiro buscamos a tarefa existente no banco pelo ID.
    // Se existir, usamos .copy() para manter todos os dados e mudar apenas o campo isCompleted.
    // Depois inserimos novamente — como Room já conhece o ID, isso funciona como um update.
    // Isso é útil em telas onde o usuário marca uma tarefa como feita (ou desfaz).
    //Esse método existe pra casos específicos, tipo atualizar só o status de completado (checkbox) sem alterar o resto.
    override suspend fun updataCompletd(id: Long, iscompleted: Boolean) {
       val existingTodo = dao.getBy(id) ?: return //Se não encontrar, ele retorna e não faz nada (?: return).
        val updateEntity = existingTodo.copy(isCompleted = iscompleted) //e encontrar, ele faz um .copy() pra alterar apenas o campo isCompleted.
        dao.insert(updateEntity)
    }

    override suspend fun delete(id: Long) {
        val existingTodo = dao.getBy(id) ?: return
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