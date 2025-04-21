package com.example.todo_list.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database
// Essa anotação marca uma classe como o banco de dados principal do Room.
// Aqui você define:
// - quais são as entidades (ou tabelas) do banco
// - a versão do banco (pra migrações futuras)
// - se o esquema deve ser exportado (usado em testes ou documentação)
// Essa classe deve ser **abstrata** e herdar de RoomDatabase.
// Nela, você declara os DAOs que o Room vai usar pra acessar os dados.

@Database (
    entities = [TodoEntity::class],
    version = 1,
    )

//abstract class	Classe incompleta que não pode ser usada sozinha
//abstract val todoDao	Uma propriedade que será implementada depois
                            //Sua classe está herdando (:) de RoomDatabase
abstract class TodoDataBase: RoomDatabase() {
    abstract val todoDao: TodoDao //abstract val quer dizer que a propriedade será implementada depois (pelo Room, automaticamente).
}
///abstract class (ou classe abstrata) é uma classe que não pode ser instanciada diretamente.
//Ou seja, você não pode fazer isso:
//val db = TodoDataBase() // ❌ Isso dá erro!

object TodoDataBaseProvider{
    @Volatile
    //Sem @Volatile:
    //Uma pessoa escreve algo, a outra ainda vê a versão antiga.
    //
    //Com @Volatile:
    //Assim que uma pessoa escreve, a outra já vê o que foi atualizado.
    private var INSTANCE: TodoDataBase? = null
                //chamado de parâmetro da função.
    fun provide(context: Context): TodoDataBase{
        //chamado de parâmetro da função.
        return INSTANCE?: synchronized(this){  //Esse synchronized serve para garantir que apenas uma thread de cada vez execute o bloco de código dentro dele.
            val instance = Room.databaseBuilder(//Cria um construtor de banco de dados
                context.applicationContext,//Contexto da aplicação
                TodoDataBase::class.java, //Diz qual é a classe que representa o banco (aquela abstract class)
                "todo-app" //nome do banco de dados
            ).build()
            INSTANCE = instance //armazena a instancia
            instance //retorna a instancia
        }
    }
}