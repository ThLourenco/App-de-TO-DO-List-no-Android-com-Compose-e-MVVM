package com.example.todo_list.ui.feature.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list.data.TodoDataBaseProvider
import com.example.todo_list.data.TodoRepositoryImpl
import com.example.todo_list.ui.UIEvent
import com.example.todo_list.ui.theme.TodolistTheme

@Composable
fun AddEditScrean(
    id: Long?,
    navigateBack: () -> Unit,
) {

    //injeçao de depedencia
    //Quando voce nao trabalha com uma biblioteca de injeçao de depedencia
    //t odo esse trabalho de instanciar as depedencias manualmente, para as tela de sua saplicaçao
    //se voce usar uma injeçao nao vai ser necessario usar esse modelo de injeçao
    val context = LocalContext.current.applicationContext
    val database = TodoDataBaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.todoDao)

    val viewModel = viewModel<AddEditViewModel>{
        AddEditViewModel(
            id = id, // tem que passar o id para buscar a tarefa se nao vai criar uma nova tarefa
            repository =  repository)
    }

    val title = viewModel.title
    val description = viewModel.description

    val snackerbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect{uiEvent ->
            when(uiEvent){
                is UIEvent.ShowSnackerBar -> {
                    snackerbarHostState.showSnackbar(message = uiEvent.message)
                }
                UIEvent.NavigateBack -> {
                    navigateBack()
                }
                is UIEvent.Navigate<*> -> {
                    //como nao tem nenhuma tela apos o cadastro, nao vamos implementar nada
                }
            }
        }
    }

    AddEditContent(title = title
        , descrition = description,
        snackerbarHostState = SnackbarHostState()
        , onEvent = viewModel::onEvent)
        //sintaxi reduzida, viewmodel.onEvent(it) esse seria o normal
}

@Composable
fun AddEditContent(
    title: String,
    descrition: String?,
    snackerbarHostState : SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit
){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(AddEditEvent.Save)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackerbarHostState)
        }

    ){ paddingValues -> //?
        //passar o layout
        Column (
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(AddEditEvent.TitleChanged(it))
                },
                //onvaluechange que a gente quer atualizar o valor do texto
                //fluxo de dados uni direcional
                placeholder = {
                    Text(text = "Title")
                })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = descrition ?: "",
                onValueChange = {
                    onEvent(AddEditEvent.DescriptionChanged(it))
                },
                placeholder = {
                    Text(text = "Descripiton (optional)")
                })
        }
    }

}

@Preview
@Composable
private fun AddEditContentPreview(){
    TodolistTheme {
        AddEditContent(title =  "", descrition = null, snackerbarHostState = SnackbarHostState(), onEvent = {})//= {} lambda vazio
    }
}