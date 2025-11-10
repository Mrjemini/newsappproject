package com.example.newsapp.presentation.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.MessageRepository
import com.example.newsapp.domain.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    init {
        observeMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            messageRepository.getAllMessages().collect { msgs ->
                _messages.value = msgs
            }
        }
    }

    fun sendMessage(text: String?, imageUri: String?) {
        viewModelScope.launch {
            messageRepository.sendMessage(text, imageUri)
        }
    }

    fun simulateReceivedMessage() {
        viewModelScope.launch {
            delay(500) // Simulate network delay
            val responses = listOf(
                "Hello! How are you?",
                "That's interesting!",
                "Thanks for sharing!",
                "I see what you mean.",
                "Cool! Tell me more."
            )
            messageRepository.receiveMessage(responses.random(), null)
        }
    }
}