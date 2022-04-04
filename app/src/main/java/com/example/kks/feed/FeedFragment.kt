package com.example.kks.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kks.databinding.FragmentFeedBinding
import com.example.kks.record.RecordActivity

class FeedFragment:Fragment() {

    lateinit var binding : FragmentFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater,container,false)

        binding.button.setOnClickListener {
            startActivity(Intent(context, RecordActivity::class.java))
        }
        return binding.root
    }




}