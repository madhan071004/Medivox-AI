package com.medai.app.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.medai.app.R
import com.medai.app.data.model.Conversation
import com.medai.app.databinding.ItemConversationBinding

class ConversationAdapter(
    private val onClick: (Conversation) -> Unit
) : ListAdapter<Conversation, ConversationAdapter.VH>(Diff()) {

    private var activeId: String? = null

    fun setActiveId(id: String?) {
        activeId = id
        notifyDataSetChanged()
    }

    inner class VH(private val b: ItemConversationBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(conv: Conversation) {
            b.tvTitle.text = conv.title
            val isActive = conv.id == activeId
            b.root.isSelected = isActive
            b.root.setOnClickListener { onClick(conv) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class Diff : DiffUtil.ItemCallback<Conversation>() {
        override fun areItemsTheSame(a: Conversation, b: Conversation) = a.id == b.id
        override fun areContentsTheSame(a: Conversation, b: Conversation) = a == b
    }
}
