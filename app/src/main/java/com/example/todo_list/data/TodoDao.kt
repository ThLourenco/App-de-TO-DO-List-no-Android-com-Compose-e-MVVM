package com.example.todo_list.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao //DAO significa Data Access Object.
interface TodoDao {
    //Na prática, um DAO é uma interface (ou classe abstrata) que define como você acessa os dados no banco de dados.
    // Ele abstrai o acesso ao banco e oferece métodos como insert(), delete(), update(), getAll(), etc.
    //boa pratica, caso tenha um aplicativo grande é sempre bom ter daos por entidade
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Aqui você está dizendo o que deve acontecer se houver um conflito no momento da inserção.
    //ABORT (padrão)	Cancela a operação (erro lançado)
    //REPLACE	Substitui o registro antigo pelo novo
    //IGNORE	Ignora o novo dado, não insere nada
    //FAIL, ROLLBACK	Comportamentos mais técnicos relacionados à transação
    suspend fun insert(entity: TodoEntity) //Uma Entity é basicamente uma tabela no banco de dados.

    @Delete
    suspend fun delete(entity: TodoEntity)

    @Query("SELECT * FROM todos")
    //forma reativa
    fun getAll(): Flow<List<TodoEntity>> //flow = fluxo

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getBy(id: Long): TodoEntity?
}