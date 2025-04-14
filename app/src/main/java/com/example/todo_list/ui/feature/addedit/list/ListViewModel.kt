package com.example.todo_list.ui.feature.addedit.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.TodoRepository
import com.example.todo_list.navigation.AddEditRoute
import com.example.todo_list.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository,
) : ViewModel(){

    val todos = repository.getAll()
    //transformar o fluxo de dados em um stateflow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()

        )

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent){
        when(event){
            is ListEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(UIEvent.Navigate(AddEditRoute(event.id)))
                }
            }
            is ListEvent.CompleteChanged -> {
                completeChanged(event.id, event.isCompleted)
            }
            is ListEvent.Delete -> {
                delete(event.id)
            }
        }
    }

    private fun delete(id: Long)
    {
        viewModelScope.launch {
            repository.delete(id = id)
        }
    }

    private fun completeChanged(id: Long, iscompleted: Boolean){
        viewModelScope.launch {
            repository.updataCompletd(id, iscompleted)
        }
    }
}