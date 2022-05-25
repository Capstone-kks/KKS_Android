package com.example.kks.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
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

//        binding.writeFb.setOnClickListener {
//            startActivity(Intent(context,WriteActivity::class.java))
//
//        }

        val monthListManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val monthListAdapter = AdapterMonth(requireContext())

        binding.calendarRecyclerView.apply{
            layoutManager = monthListManager
            adapter = monthListAdapter

                scrollToPosition(Int.MAX_VALUE/2)


        }

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarRecyclerView)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }


}