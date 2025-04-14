package com.example.todo_list.ui.feature.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.TodoRepository
import com.example.todo_list.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long? =null,
    private val repository: TodoRepository,
): ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init { //verificar se o id nao for null, se estamos verificando se o id nao for null siginica
        //que estamos tentando editar essa tarefa
        id?.let {
            viewModelScope.launch {
                val todo = repository.getBy(it) //it é o id
                title = todo?.title ?: "" //retorna valor vazio
                description = todo?.description // pode retornar null
            }
        }
    }

    fun onEvent(event: AddEditEvent){
        when(event){

            is AddEditEvent.TitleChanged -> {
               title = event.title
            }
            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }
             AddEditEvent.Save -> {
                saveTodo()
             }

        }
    }

    private fun saveTodo(){
        viewModelScope.launch {
            if(title.isBlank()) {
                _uiEvent.send(UIEvent.ShowSnackerBar("Title cannot be empty"))
                return@launch
            }
            repository.insert(title, description, id)
            _uiEvent.send(UIEvent.NavigateBack)
        }
    }

}