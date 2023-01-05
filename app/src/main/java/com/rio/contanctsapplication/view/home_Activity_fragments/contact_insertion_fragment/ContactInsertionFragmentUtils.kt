package com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment

import android.app.DatePickerDialog
import android.text.InputFilter
import android.util.Log
import android.widget.TextView
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.ContactInsertionFragment
import java.util.*
import kotlin.collections.ArrayList

/**
 * [onPickDateButtonClicked] method shows the date picker dialog and get date from the user and set
 * it to the given view
 * @param view of type TextView representing the view to show the picked date
 */
fun ContactInsertionFragment.onPickDateButtonClicked
            (view: TextView) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    // creating the dialog box instance and functionality after selecting the date
    val datePickerDialog = DatePickerDialog(requireActivity(),{ _, year, monthOfYear, dayOfMonth ->
        val dob = "$dayOfMonth-${monthOfYear + 1}-$year"
        // assigning the date to the view provided
        view.text = dob
    },year,
        month,
        day
    )
    // showing the calender dialog box
    datePickerDialog.show()
}


/**
 * [MenuItem] data class that stores the Menu items
 */
data class MenuItem(
    val itemName:String,
    val itemIcon:String,
    val itemTarget:String,
    val itemTargetIcon:String,
    val type:String,
    val targetPriority:Int,
    val inputType:Int,
    val inputFilter:Array<InputFilter>
)

/**
 * [UserInfoItem] data class stores the User info items such as name, phone no, etc.
 */
data class UserInfoItem(
    val itemId:Int,
    val itemName:String,
    val itemIcon:String,
    var itemValue:String,
    val priority:Int,
    val inputType:Int,
    val inputFilter:Array<InputFilter>
)

fun ArrayList<UserInfoItem>.insert(userInfoItem: UserInfoItem){
    if(this.size == 0){
        this.add(userInfoItem)
        return
    }
    for((index,item) in this.withIndex()){
        if(item.priority > userInfoItem.priority){
            this.add(index, userInfoItem)
            return
        }
    }
    this.add(userInfoItem)
}