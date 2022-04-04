package com.example.kks.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kks.databinding.FragmentCalendarBinding
import com.example.kks.record.WriteActivity


import java.util.*

class CalendarFragment():Fragment() {
    lateinit var binding: FragmentCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater,container,false)

        binding.writeFb.setOnClickListener {
            startActivity(Intent(context,WriteActivity::class.java))

        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }


}