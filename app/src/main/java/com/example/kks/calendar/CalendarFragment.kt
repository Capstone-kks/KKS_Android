package com.example.kks.calendar


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kks.databinding.FragmentCalendarBinding


class CalendarFragment():Fragment() {
    lateinit var binding: FragmentCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater,container,false)


        val monthListManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val monthListAdapter = AdapterMonth(requireContext())
        binding.calendarRecyclerView.layoutManager = monthListManager
        binding.calendarRecyclerView.adapter= monthListAdapter
        binding.calendarRecyclerView.scrollToPosition(Int.MAX_VALUE/2)

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarRecyclerView)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.calendarRecyclerView.adapter?.notifyDataSetChanged()

    }


}