package com.example.todo_list.ui.components

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_list.domain.Todo
import com.example.todo_list.domain.todo1
import com.example.todo_list.domain.todo2
import com.example.todo_list.ui.theme.TodolistTheme

@Composable //Composable (exibe a UI)
fun Todoitem(
    todo: Todo,
    //[funçoes lambda]
    onCompleteChange: (Boolean) -> Unit, //estado de check, retorna um Unit
    onItemClicked: () ->Unit, //item clicado
    onDeleteClicked: () ->Unit, //item deletado
    modifier: Modifier = Modifier) {
    Surface( //layout raiz
        onClick = onItemClicked,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ){
        Row( //colocar os componentes organizados em linhas
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = todo.isCompleted,
                onCheckedChange = onCompleteChange)
            Spacer(modifier = Modifier.width(8.dp)) // Modifier.width só pode ser usado dentro de um Row para dar um espaço
            //e sempre utiliza um modifier (odifier.width(espaço da orizontal))
            Column(modifier = Modifier.weight(1f)) { //layout que organiza dentro dele os componentes em coluna

                Text(text = todo.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp)) // Modifier.heigth só pode ser usado dentro de um column (Modifier.width(espaço na vertical))
                todo.description?.let { //verificaçao de nulo
                    //se o description nao for nulo ele faz oque esta dento do let
                    Spacer(modifier = Modifier.height(8.dp)) // Modifier.heigth só pode ser usado dentro de um column (Modifier.width(espaço na vertical)
                    Text(text = todo.description, style = MaterialTheme.typography.bodyLarge)
                }

            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onDeleteClicked  //fluxo de dados unidirecional

            ){
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable //Composable (exibe a UI)
private fun TodoItemPreview(){
    TodolistTheme {
        Todoitem(
            todo = todo1,
            onCompleteChange = {},
            onItemClicked = {},
            onDeleteClicked = {}

        )
    }
}


@Preview
@Composable //Composable (exibe a UI)
private fun TodoItemCompletedPreview(){
    TodolistTheme {
        Todoitem(
            todo = todo2,
            onCompleteChange = {},
            onItemClicked = {},
            onDeleteClicked = {}
        )
    }
}