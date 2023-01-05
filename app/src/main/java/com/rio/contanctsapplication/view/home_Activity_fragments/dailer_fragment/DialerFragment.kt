package com.rio.contanctsapplication.view.home_Activity_fragments.dailer_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentDialerBinding

class DialerFragment : Fragment(), OnClickListener {
    private lateinit var binding:FragmentDialerBinding
    private val PHONE_MAX_DIGIT = 10
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialer, container, false)
        binding.lifecycleOwner = this
        binding.dialer.apply {
            number0.setOnClickListener(this@DialerFragment)
            number1.setOnClickListener(this@DialerFragment)
            number2.setOnClickListener(this@DialerFragment)
            number3.setOnClickListener(this@DialerFragment)
            number4.setOnClickListener(this@DialerFragment)
            number5.setOnClickListener(this@DialerFragment)
            number6.setOnClickListener(this@DialerFragment)
            number7.setOnClickListener(this@DialerFragment)
            number8.setOnClickListener(this@DialerFragment)
            number9.setOnClickListener(this@DialerFragment)
            symbolAsh.setOnClickListener(this@DialerFragment)
            symbolStar.setOnClickListener(this@DialerFragment)
            clearDigit.setOnClickListener(this@DialerFragment)
        }
        return binding.root
    }

    override fun onClick(v: View?) {
        binding.dialer.apply {
            when (v) {
                number1->insert("1")
                number2->insert("2")
                number3->insert("3")
                number4->insert("4")
                number5->insert("5")
                number6->insert("6")
                number7->insert("7")
                number8->insert("8")
                number9->insert("9")
                number0->insert("0")
                symbolStar->insert("*")
                symbolAsh->insert("#")
                else-> deleteOneDigit()
            }
        }
    }

    private fun deleteOneDigit(){
        var text = binding.dialer.phoneNumber.text
        if(text.isNotEmpty()){
            text = text.subSequence(0,text.length-1)
        }
        binding.dialer.phoneNumber.text = text
    }
    private fun insert(stringNumber:String){
        var text = binding.dialer.phoneNumber.text
        if(text.length < PHONE_MAX_DIGIT){
            text = "$text$stringNumber"
        }
        binding.dialer.phoneNumber.text = text
    }
}