package com.example.todo_list.ui.feature


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_list.domain.Todo
import com.example.todo_list.domain.todo1
import com.example.todo_list.domain.todo2
import com.example.todo_list.domain.todo3
import com.example.todo_list.ui.components.Todoitem
import com.example.todo_list.ui.theme.TodolistTheme

@Composable
fun ListScreen(
    navigationToAddEditScrean: (id: Long?) -> Unit
) {
    ListContent(todos = emptyList(),
        onAddItemClick = navigationToAddEditScrean,)
}

@Composable
fun ListContent(//modifier: Modifier = Modifier
    todos: List<Todo>,
    onAddItemClick: (id: Long?) -> Unit,
    ){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {onAddItemClick(null)}) {
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
                        onCompleteChange = {},
                        onItemClicked = {},
                        onDeleteClicked = {} )
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
            onAddItemClick = {},)
    }
}