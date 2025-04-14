package com.example.todo_list.ui

import android.telephony.SmsMessage.MessageClass

sealed interface UIEvent {

    data class ShowSnackerBar(val message: String): UIEvent
    data object NavigateBack: UIEvent
    data class Navigate<T: Any>(val route: T): UIEvent
}