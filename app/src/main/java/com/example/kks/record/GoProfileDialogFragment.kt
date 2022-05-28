package com.example.kks.record

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kks.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GoProfileDialogFragment(var writerId:String) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        Log.d("bottomsheet:",writerId)

        binding.bottomSheetTv.setOnClickListener {
           //todo profile activity로 이동.
        //    intent.putExtra("writerId",)
         //   startActivity(intent)
        }

        return binding.root
    }

    companion object {
        const val TAG = "BottomSheetFragment"
    }



}