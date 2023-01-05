package com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentContactInsertionBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.adapter.ContactInsertFragmentMenuAdapter
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.adapter.ContactInsertionFragmentUserInfoRecyclerViewAdapter
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view_model.ContactInsertionFragmentViewModel
import com.rio.contanctsapplication.view_model.FContactInsertionFragmentViewModelFactory

class ContactInsertionFragment : Fragment(),
    ContactInsertFragmentMenuAdapter.IMenuInteraction,
    ContactInsertionFragmentUserInfoRecyclerViewAdapter.IUserInteraction{
    private lateinit var binding: FragmentContactInsertionBinding
    private val contactInsertionFragmentViewModel: ContactInsertionFragmentViewModel by lazy{
        ViewModelProvider(
            requireActivity(),
            FContactInsertionFragmentViewModelFactory(
                ContactsDatabase.getInstanceOfDataBase(requireActivity())
            )
        )[ContactInsertionFragmentViewModel::class.java]
    }
    private val userInfo = MutableLiveData<ArrayList<UserInfoItem>>()
    private val details:ArrayList<UserInfoItem> = arrayListOf()
    private var count=0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_insertion, container, false)
        binding.lifecycleOwner = this

        initMenuRecyclerView()
        initUserRecyclerView()
        binding.saveButton.setOnClickListener {
            if(isValid()){
                val contact = arrayOf("", "", "", "", "", "", "")
                for(data in userInfo.value!!){
                    if(data.priority != 1) {
                        contact[data.priority - 1] += "~" + data.itemValue
                    }
                    o
                }
                contactInsertionFragmentViewModel.insertData(Contact(contact[0], contact[1], contact[2], contact[3], contact[4], "", "", 0), requireActivity())
                requireActivity().supportFragmentManager.popBackStack()
            }else{
                Toast.makeText(requireActivity(), "Name, Phone number is necessary", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun isValid():Boolean{
        var isNameAvailable = false
        var isPhoneNumberAvailable = false
        userInfo.value?.let{
            for(data in userInfo.value!!){
                if(data.itemName == "Username" && data.itemValue.isNotEmpty()){
                    isNameAvailable = true
                }else if((data.itemName == "Phone Number" && data.itemValue.isNotEmpty()) || (data.itemName == "Mobile Number" && data.itemValue.isNotEmpty())){
                    isPhoneNumberAvailable = true
                }
            }
        }
        return isNameAvailable && isPhoneNumberAvailable
    }

    override fun onMenuItemClicked(menuItem: MenuItem) {
        if(menuItem.type=="Editable only"){
            for(userInfo in details){
                if(userInfo.itemName == menuItem.itemTarget){
                    makeToastWith(requireActivity(), "Item ${menuItem.itemTarget} Already Exists")
                    return
                }
            }
            details.add(UserInfoItem(++count, menuItem.itemTarget, menuItem.itemTargetIcon,
                "", menuItem.targetPriority, menuItem.inputType, menuItem.inputFilter))
        }else{
            details.add(UserInfoItem(++count, menuItem.itemTarget, menuItem.itemTargetIcon,
                "", menuItem.targetPriority, menuItem.inputType, menuItem.inputFilter))
        }
        makeToastWith(requireActivity(), "Item ${menuItem.itemTarget} created")
        userInfo.value = details
    }

    /**
     * [initMenuRecyclerView] method initializes the menu recyclerView in contact insertion fragment
     */
    private fun initMenuRecyclerView(){
        val menuItems = arrayListOf(
            MenuItem("Username", "edit_profile", "Username",
                "profile_cif","Editable only", 1, InputType.TYPE_CLASS_TEXT, arrayOf(InputFilter.LengthFilter(40))
            ),
            MenuItem("Address", "add_address", "Address",
                "location","Addable", 5, InputType.TYPE_CLASS_TEXT, arrayOf(InputFilter.LengthFilter(125))
            ),
            MenuItem("Email", "add_email", "E-mail",
                "email","Addable", 4, InputType.TYPE_CLASS_TEXT, arrayOf(InputFilter.LengthFilter(30))
            ),
            MenuItem("Group", "add_group", "Add Groups",
                "group_cif","Editable only", 6, InputType.TYPE_CLASS_TEXT, arrayOf(InputFilter.LengthFilter(40))
            ),
            MenuItem("Phone no", "add_phone_number", "Phone Number",
                "phone","Addable", 3, InputType.TYPE_CLASS_NUMBER, arrayOf(InputFilter.LengthFilter(10))
            ),
            MenuItem("Mobile no", "add_mobile_number", "Mobile Number",
                "mobile","Addable", 2, InputType.TYPE_CLASS_NUMBER, arrayOf(InputFilter.LengthFilter(10))
            ),
            MenuItem("Company", "edit_company", "Company Name",
                "company","Editable only", 7, InputType.TYPE_CLASS_TEXT, arrayOf(InputFilter.LengthFilter(30))
            )
        )

        binding.ContactInsertionMenuRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ContactInsertFragmentMenuAdapter(this@ContactInsertionFragment)
        adapter.submitList(menuItems)
        binding.ContactInsertionMenuRecyclerView.adapter = adapter
    }

    private fun initUserRecyclerView(){
        binding.ContactInsertionUserInfoRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ContactInsertionFragmentUserInfoRecyclerViewAdapter(contactInsertionFragmentViewModel, this@ContactInsertionFragment)
        userInfo.observe(viewLifecycleOwner){
            adapter.submitList(it.sortedBy {it2 ->  it2.priority })
        }
        binding.ContactInsertionUserInfoRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contactInsertionFragmentViewModel.contactData = MutableLiveData(HashMap())
    }

    override fun getActivityContext(): Context = requireActivity()


    override fun onRemoveClicked(userInfoItem: UserInfoItem) {

        details.remove(userInfoItem)
        makeToastWith(requireActivity(), "Item removed")
        userInfo.value = details
    }

    override fun onTextChanged(text: String, userInfoItem: UserInfoItem) {

        details[details.indexOf(userInfoItem)].itemValue = text
    }

    override fun onEditClicked(userInfoItem: UserInfoItem) {
        makeToastWith(requireActivity(), "Edit Clicked")
    }

//    // creating Coroutine exception handler and handling Exception thrown during the coroutine execution
//    private val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        requireActivity().runOnUiThread {
//            when(exception){
//                is ContactAlreadyExistsException -> {
//                    showInfoBox(
//                        requireActivity(),
//                        "Contact Already Exists",
//                        "Saving contact with same name is not allowed since it may leads " +
//                                "to confusion among contacts.\nTry another name",
//                        iconId = R.drawable.app_icon
//                    )
//                }
//            }
//        }
//    }
}