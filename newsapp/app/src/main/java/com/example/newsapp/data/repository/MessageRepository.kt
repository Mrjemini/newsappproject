package com.example.newsapp.data.repository

import com.example.newsapp.data.local.dao.MessageDao
import com.example.newsapp.data.local.entities.MessageEntity
import com.example.newsapp.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val messageDao: MessageDao
) {
    fun getAllMessages(): Flow<List<Message>> =
        messageDao.getAllMessages().map { entities ->
            entities.map { it.toDomainModel() }
        }

    suspend fun sendMessage(text: String?, imageUri: String?): Long {
        val entity = MessageEntity(
            text = text,
            imageUri = imageUri,
            timestamp = System.currentTimeMillis(),
            isSent = true
        )
        return messageDao.insertMessage(entity)
    }

    suspend fun receiveMessage(text: String?, imageUri: String?): Long {
        val entity = MessageEntity(
            text = text,
            imageUri = imageUri,
            timestamp = System.currentTimeMillis(),
            isSent = false
        )
        return messageDao.insertMessage(entity)
    }

    private fun MessageEntity.toDomainModel() = Message(
        id = id,
        text = text,
        imageUri = imageUri,
        timestamp = timestamp,
        isSent = isSent
    )
}