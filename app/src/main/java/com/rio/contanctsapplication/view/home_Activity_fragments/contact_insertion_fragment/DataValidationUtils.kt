package com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment

import com.rio.contanctsapplication.databinding.FragmentContactInsertionBinding


//fun validateInputs(binding: FragmentContactInsertionBinding):ArrayList<String>{
//    val missingData = arrayListOf<String>()
//    binding.apply {
//        if(InewContactName.text.length < 3){
//            missingData.add("Invalid name, Requires minimum of 3 characters")
//            newContactName.animate().apply {
//                saveContactButton.isEnabled=false
//                duration = 300
//                scaleXBy(0.1F)
//                scaleYBy(0.1F)
//            }.withEndAction {
//                newContactName.animate().apply {
//                    duration = 300
//                    scaleXBy(-0.1F)
//                    scaleYBy(-0.1F)
//                }.withEndAction {
//                    saveContactButton.isEnabled=true
//                }.start()
//            }
//        }
//        if(addContactsEmailId.text.length<9){
//            missingData.add("Invalid email id")
//            addContactsEmailId.animate().apply {
//                duration = 300
//                scaleXBy(0.1F)
//                scaleYBy(0.1F)
//            }.withEndAction {
//                addContactsEmailId.animate().apply {
//                    duration = 300
//                    scaleXBy(-0.1F)
//                    scaleYBy(-0.1F)
//                }.withEndAction {
//                    saveContactButton.isEnabled=true
//                }.start()
//            }
//        }
//        if(addContactsDOB.text.length<9){
//            missingData.add("DOB Missing")
//            addContactsDOB.animate().apply {
//                duration = 300
//                scaleXBy(0.1F)
//                scaleYBy(0.1F)
//            }.withEndAction {
//                addContactsDOB.animate().apply {
//                    duration = 300
//                    scaleXBy(-0.1F)
//                    scaleYBy(-0.1F)
//                }.withEndAction {
//                    saveContactButton.isEnabled=true
//                }.start()
//            }
//        }

//            if(mobileNo1.phoneNo.text.length in 1..9){
//                missingData.add("Invalid mobile number")
//                mobileNo1.phoneNo.animate().apply {
//                    duration = 300
//                    scaleXBy(0.1F)
//                    scaleYBy(0.1F)
//                }.withEndAction {
//                    mobileNo1.phoneNo.animate().apply {
//                        duration = 300
//                        scaleXBy(-0.1F)
//                        scaleYBy(-0.1F)
//                    }.start()
//                }
//            }
//
//            if(mobileNo2.phoneNo.text.length in 1..9){
//                missingData.add("Invalid Mobile Number")
//                mobileNo2.phoneNo.animate().apply {
//                    duration = 300
//                    scaleXBy(0.1F)
//                    scaleYBy(0.1F)
//                }.withEndAction {
//                    mobileNo2.phoneNo.animate().apply {
//                        duration = 300
//                        scaleXBy(-0.1F)
//                        scaleYBy(-0.1F)
//                    }.start()
//                }
//            }
//
//            if(mobileNo3.phoneNo.text.length in 1..9){
//                missingData.add("DOB Missing")
//                mobileNo3.phoneNo.animate().apply {
//                    duration = 300
//                    scaleXBy(0.1F)
//                    scaleYBy(0.1F)
//                }.withEndAction {
//                    mobileNo3.phoneNo.animate().apply {
//                        duration = 300
//                        scaleXBy(-0.1F)
//                        scaleYBy(-0.1F)
//                    }.start()
//                }
//            }



//    }
//    return missingData
//}