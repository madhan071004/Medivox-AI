package com.medai.app.ui.chat

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medai.app.data.model.ChatMessage
import com.medai.app.databinding.ItemMessageAssistantBinding
import com.medai.app.databinding.ItemMessageUserBinding
import io.noties.markwon.Markwon

class ChatAdapter(
    private val markwon: Markwon
) : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_USER      = 0
        private const val VIEW_ASSISTANT = 1
    }

    override fun getItemViewType(position: Int) =
        if (getItem(position).role == "user") VIEW_USER else VIEW_ASSISTANT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_USER -> UserViewHolder(
                ItemMessageUserBinding.inflate(inflater, parent, false)
            )
            else -> AssistantViewHolder(
                ItemMessageAssistantBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = getItem(position)
        when (holder) {
            is UserViewHolder      -> holder.bind(msg)
            is AssistantViewHolder -> holder.bind(msg)
        }
    }

    // ── User ViewHolder ────────────────────────────────────
    inner class UserViewHolder(
        private val binding: ItemMessageUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: ChatMessage) {
            binding.tvMessage.text = msg.content

            if (msg.imageUri != null) {
                binding.ivAttachment.visibility = View.VISIBLE
                Glide.with(binding.root)
                    .load(Uri.parse(msg.imageUri))
                    .centerCrop()
                    .into(binding.ivAttachment)
            } else {
                binding.ivAttachment.visibility = View.GONE
            }
        }
    }

    // ── Assistant ViewHolder ───────────────────────────────
    inner class AssistantViewHolder(
        private val binding: ItemMessageAssistantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: ChatMessage) {
            if (msg.isLoading) {
                binding.tvMessage.visibility    = View.GONE
                binding.layoutTyping.visibility = View.VISIBLE
            } else {
                binding.tvMessage.visibility    = View.VISIBLE
                binding.layoutTyping.visibility = View.GONE
                markwon.setMarkdown(binding.tvMessage, msg.content)
            }
        }
    }

    // ── DiffCallback ───────────────────────────────────────
    class DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(a: ChatMessage, b: ChatMessage) = a.id == b.id
        override fun areContentsTheSame(a: ChatMessage, b: ChatMessage) = a == b
    }
}
