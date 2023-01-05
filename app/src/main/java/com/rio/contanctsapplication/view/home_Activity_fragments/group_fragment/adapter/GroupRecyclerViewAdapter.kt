package com.rio.contanctsapplication.view.home_Activity_fragments.group_fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.model.testing.SampleData

class GroupRecyclerViewAdapter(private val interaction: IGroupItemInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Group>(){
        override fun areItemsTheSame(oldItem:Group, newITem:Group):Boolean {
            return oldItem == newITem
        }

        override fun areContentsTheSame(oldItem:Group, newITem:Group):Boolean {
            return oldItem.groupName == newITem.groupName
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.group_recycler_view_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindUi(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list:List<Group>){
        differ.submitList(list)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: IGroupItemInteraction?
    ): RecyclerView.ViewHolder(itemView){
        val dp: ImageView = itemView.findViewById(R.id.groupRecyclerViewGroupDp)
        val groupName: TextView = itemView.findViewById(R.id.groupRecyclerViewGroupName)
        val recycler: RecyclerView = itemView.findViewById(R.id.groupRecyclerViewItemRecyclerView)
        val contactsCount: TextView = itemView.findViewById(R.id.groupRecyclerViewGroupContactCount)
        val item: ConstraintLayout = itemView.findViewById(R.id.groupsRecyclerViewItem)
        fun bindUi(group:Group){
            groupName.text = group.groupName
            item.setOnClickListener {
                interaction?.onItemClicked(group)
            }
            val adapter = GroupInnerRecyclerViewAdapter()
            adapter.submitList(SampleData.listOfContacts)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(interaction?.getActivityContext())
            itemView.findViewById<TextView>(R.id.noContactInfoTextView).visibility = View.INVISIBLE
        }
    }

    interface IGroupItemInteraction{
        fun onItemClicked(group: Group)
        fun getViewModel():ViewModel
        fun getActivityContext():Context
    }
}