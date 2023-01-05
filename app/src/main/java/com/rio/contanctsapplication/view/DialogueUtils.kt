package com.rio.contanctsapplication.view

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast



/**
 * [makeToastWith] method makes the Toast message with the given message
 * @param context ot type Context representing the context of the activity
 * @param message of type String representing the message to be shown in the Toast
 */
fun makeToastWith(
    context: Context,
    message: String
){
    // initializing the Toast Message
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}



/**
 * [makeAlertDialogueWithToast] method makes alertBox with the given data
 * @param title of type String representing the title of the dialogue box
 * @param message of type String representing the message to shown in the dialogue box
 * @param onPositiveMessage of type String representing the message to be shown in the Toast when
 * the positive button is clicked
 * @param onNegativeMessage of type String representing the message to be Shown in the Toast when
 * the negative button is clicked
 * @param onNeutralMessage of type String representing the message to be Shown in the Toast when
 * the neutral button is clicked
 * @param negativeButtonText (default value = "no") of type string representing the text of the
 * negative button
 * @param positiveButtonText (default value = "yes") of type string representing the text of the
 * positive button
 * @param neutralButtonText (default value = "cancel") of type string representing the text of the
 * neutral button
 * @param iconId of type Int representing the id of the image resource in the resource folder
 */
fun makeAlertDialogueWithToast
            (context: Context, title:String, message:String,
             onPositiveMessage:String, onNegativeMessage:String,
             onNeutralMessage:String, negativeButtonText:String="no",
             positiveButtonText:String="yes", neutralButtonText:String="cancel",
             iconId:Int?=null):Byte{
    var temp:Byte = 0
    val alertDialog = AlertDialog.Builder(context).also {
        it.setTitle(title)
        it.setMessage(message)
        it.setPositiveButton(positiveButtonText){_, _ ->
            makeToastWith(context, onPositiveMessage)
            temp = 1
        }
        it.setNegativeButton(negativeButtonText){_, _ ->
            makeToastWith(context, onNegativeMessage)
            temp = 2
        }
        it.setNeutralButton(neutralButtonText){_, _ ->
            makeToastWith(context, onNeutralMessage)
            temp = 3
        }
        // validating whether the icon id is given or not if given then displays it the alert dialog
        if(iconId != null){
            try {
                it.setIcon(iconId)
            }catch (e:Exception){
                throw IllegalArgumentException("Expecting resource id(R.drawable.[iconName]), but provide irrelevant")
            }
        }
    }
    alertDialog.show()
    return temp
}


/**
 * [showInfoBox] method makes alertBox with the given data
 * @param title of type String representing the title of the dialogue box
 * @param message of type String representing the message to shown in the dialogue box
 * @param iconId of type Int representing the id of the image resource in the resource folder
 */
fun showInfoBox(context: Context, title:String, message:String, iconId:Int?=null){
    val alertDialog = AlertDialog.Builder(context).also {
        it.setTitle(title)
        it.setMessage(message)
        it.setPositiveButton("Ok"){_, _ ->
        }
        // validating whether the icon id is given or not if given then displays it the alert dialog
        if(iconId != null){
            try {
                it.setIcon(iconId)
            }catch (e:Exception){
                throw IllegalArgumentException("Expecting resource id(R.drawable.[iconName]), " +
                        "but provide irrelevant")
            }
        }
    }
    alertDialog.show()
}


