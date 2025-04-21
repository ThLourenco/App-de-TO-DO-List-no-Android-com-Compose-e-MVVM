package com.example.todo_list.ui.feature.addedit.list


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list.data.TodoDataBaseProvider
import com.example.todo_list.data.TodoRepositoryImpl
import com.example.todo_list.domain.Todo
import com.example.todo_list.domain.todo1
import com.example.todo_list.domain.todo2
import com.example.todo_list.domain.todo3
import com.example.todo_list.navigation.AddEditRoute
import com.example.todo_list.ui.UIEvent
import com.example.todo_list.ui.components.Todoitem
import com.example.todo_list.ui.feature.addedit.AddEditEvent
import com.example.todo_list.ui.theme.TodolistTheme
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navigationToAddEditScrean: (id: Long?) -> Unit,
) {
    //injeçao de depedencia
    //Quando voce nao trabalha com uma biblioteca de injeçao de depedencia
    //t odo esse trabalho de instanciar as depedencias manualmente, para as tela de sua saplicaçao
    //se voce usar uma injeçao nao vai ser necessario usar esse modelo de injeçao
    val context = LocalContext.current.applicationContext
    val database = TodoDataBaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.todoDao)

    val viewModel = viewModel<ListViewModel>{
        ListViewModel( repository =  repository)
    }
    //vai receber o state do nosso view model
    //flow sem state
    //val todos = viewModel.todos
    val todos by viewModel.todos.collectAsState() //passei oque era um flow para stateflow

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UIEvent.Navigate<*> -> {
                    when(uiEvent.route){
                        is AddEditRoute ->{
                            navigationToAddEditScrean(uiEvent.route.id)
                        }
                    }
                }
                UIEvent.NavigateBack -> {}
                is UIEvent.ShowSnackerBar -> {
                }
            }
        }
    }

    LaunchedEffect(Unit){
        viewModel.uiEvent.collect{ uiEvent ->{}
            when(uiEvent){
                is UIEvent.Navigate<*> -> {
                    when(uiEvent.route){
                        is AddEditRoute ->{
                            navigationToAddEditScrean(uiEvent.route.id)

                        }
                    }
                }
                UIEvent.NavigateBack -> {
                    TODO()
                }
                is UIEvent.ShowSnackerBar -> {
                    TODO()
                }
            }            }
    }

    ListContent(
        todos = todos,
        onEvent = viewModel::onEvent


    )
}

@Composable
fun ListContent(//modifier: Modifier = Modifier
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit,
    ) {
    //// Scaffold é um layout de estrutura que ajuda a organizar os elementos principais da tela.
    //// Ele fornece slots (espaços) prontos para:
    //// - barra superior (topBar)
    //// - botão flutuante (floatingActionButton)
    //// - menu lateral (drawerContent)
    //// - conteúdo principal (content)
    //// Ele cuida do espaçamento automático entre os elementos.
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ListEvent.AddEdit(null))}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ){ paddingValues -> // trocamos o it para paddingValues para nao dar warning
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos){ index, todo -> //trocamos o it para todo
                Todoitem(
                    //qunado colocamos  onCompleteChange = {} lambda vazio
                   todo = todo,
                        onCompleteChange = {
                            onEvent(ListEvent.CompleteChanged(todo.id,it))
                        },
                        onItemClicked = {
                            onEvent(ListEvent.AddEdit(todo.id))
                        },
                        onDeleteClicked = {
                            onEvent(ListEvent.Delete(todo.id))
                        } )
                    if(index < todos.lastIndex){
                        Spacer(modifier = Modifier.height(16.dp))
                    }
            }

        }
    }

}

@Preview
@Composable
private fun ListContentPreview(){
    TodolistTheme {
        ListContent(
            todos = listOf(
                todo1,
                todo2,
                todo3,
            ),
            onEvent = {},)
    }
}