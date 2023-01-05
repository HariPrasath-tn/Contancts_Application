package com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.UserInfoItem
import com.rio.contanctsapplication.view_model.ContactInsertionFragmentViewModel

class ContactInsertionFragmentUserInfoRecyclerViewAdapter(private val viewModel:ContactInsertionFragmentViewModel, private val interaction: IUserInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object{
        const val DEFAULT_VIEW_TYPE = 0
        const val GROUP_VIEW_TYPE = 1
    }

    private var isReStarted = 0;

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserInfoItem>(){
        override fun areItemsTheSame(oldItem: UserInfoItem, newITem: UserInfoItem):Boolean {
            return oldItem == newITem
        }

        override fun areContentsTheSame(oldItem: UserInfoItem, newITem: UserInfoItem):Boolean {
            return oldItem.itemName == newITem.itemName
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == GROUP_VIEW_TYPE){
            return GroupViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.contact_insertion_group_data_recyclerview_item,
                    parent,
                    false
                ),
                viewModel,
                interaction
            )
        }
        return DefaultViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contact_insertion_data_recyclerview_item,
                parent,
                false
            ),
            viewModel,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DefaultViewHolder){
            holder.bindUi(differ.currentList[position])
        }else if(holder is GroupViewHolder){
            holder.bindUi(differ.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int = if(differ.currentList[position].itemName != "Add Groups"){
        DEFAULT_VIEW_TYPE
    }else{
        GROUP_VIEW_TYPE
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<UserInfoItem>){
        differ.submitList(list)
        notifyDataSetChanged()
        isReStarted = 0
    }

    class GroupViewHolder(
        itemView: View,
        private val viewModel: ContactInsertionFragmentViewModel,
        private val interaction: ContactInsertionFragmentUserInfoRecyclerViewAdapter.IUserInteraction?
    ): RecyclerView.ViewHolder(itemView){
        private val dp:ImageView = itemView.findViewById(R.id.contactInsertionGroupUserInfoDp)
        private val dataValue:TextView = itemView.findViewById(R.id.contactInsertionGroupUSerInfoValue)
        private val editButton:ImageButton = itemView.findViewById(R.id.contactInsertionDataGroupEditButton)
        private val removeButton:ImageButton = itemView.findViewById(R.id.contactInsertionDataGroupRemoveButton)
        fun bindUi(userInfoItem:UserInfoItem){
            dataValue.hint = userInfoItem.itemName
            dataValue.text = userInfoItem.itemValue


            interaction?.let {
                removeButton.setOnClickListener{ _ ->
                    it.onRemoveClicked(userInfoItem)
                }
                editButton.setOnClickListener{_->
                    it.onEditClicked(userInfoItem)
                }

                it.getActivityContext().let { it1 ->
                    val drawableResourceId = it1.resources.getIdentifier(
                        userInfoItem.itemIcon,
                        "drawable",
                        it1.packageName
                    );
                    Glide.with(it1).load(drawableResourceId).into(dp)
                };
            }

        }
    }

    class DefaultViewHolder(
        itemView: View,
        private val viewModel: ContactInsertionFragmentViewModel,
        private val interaction: ContactInsertionFragmentUserInfoRecyclerViewAdapter.IUserInteraction?
    ): RecyclerView.ViewHolder(itemView){

        private val dp: ImageView = itemView.findViewById(R.id.contactInsertionGroupUserInfoDp)
        private val dataValue:TextView = itemView.findViewById(R.id.contactInsertionGroupUSerInfoValue)
        private val removeButton:ImageButton = itemView.findViewById(R.id.contactInsertionDataEditButton)

        fun bindUi(userInfoItem: UserInfoItem){
            var currentItem = -1
            dataValue.hint = userInfoItem.itemName
            dataValue.inputType = userInfoItem.inputType
            dataValue.filters = userInfoItem.inputFilter

            dataValue.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                currentItem = if (hasFocus) {
                    userInfoItem.itemId
                } else {
                    -1
                }
            }

            dataValue.text = userInfoItem.itemValue


            interaction?.let {

                dataValue.doOnTextChanged { text, start, before, count ->
                    if (currentItem == userInfoItem.itemId) {
                        it.onTextChanged(text.toString(), userInfoItem)
                    }
                }

                removeButton.setOnClickListener{ _ ->
                    it.onRemoveClicked(userInfoItem)
                }

                it.getActivityContext().let { it1 ->
                    val drawableResourceId = it1.resources.getIdentifier(
                        userInfoItem.itemIcon,
                        "drawable",
                        it1.packageName
                    );
                    Glide.with(it1).load(drawableResourceId).into(dp)
                };
            }

        }
    }

    interface IUserInteraction{
        fun getActivityContext():Context
        fun onRemoveClicked(userInfoItem: UserInfoItem)
        fun onTextChanged(text:String, userInfoItem: UserInfoItem)
        fun onEditClicked(userInfoItem: UserInfoItem)
    }
}
