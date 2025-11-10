package com.example.newsapp.presentation.messages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ItemMessageReceivedBinding
import com.example.newsapp.databinding.ItemMessageSentBinding
import com.example.newsapp.domain.model.Message
import com.example.newsapp.util.DateUtils

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isSent) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemMessageSentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SentViewHolder(binding)
        } else {
            val binding = ItemMessageReceivedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ReceivedViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is SentViewHolder -> holder.bind(message)
            is ReceivedViewHolder -> holder.bind(message)
        }
    }

    inner class SentViewHolder(
        private val binding: ItemMessageSentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.tvMessage.isVisible = !message.text.isNullOrEmpty()
            binding.tvMessage.text = message.text

            binding.ivImage.isVisible = !message.imageUri.isNullOrEmpty()
            if (!message.imageUri.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(message.imageUri)
                    .into(binding.ivImage)
            }

            binding.tvTime.text = DateUtils.formatTime(message.timestamp)
        }
    }

    inner class ReceivedViewHolder(
        private val binding: ItemMessageReceivedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.tvMessage.isVisible = !message.text.isNullOrEmpty()
            binding.tvMessage.text = message.text

            binding.ivImage.isVisible = !message.imageUri.isNullOrEmpty()
            if (!message.imageUri.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(message.imageUri)
                    .into(binding.ivImage)
            }

            binding.tvTime.text = DateUtils.formatTime(message.timestamp)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Message, newItem: Message) =
            oldItem == newItem
    }
}