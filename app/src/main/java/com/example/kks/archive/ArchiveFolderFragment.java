package com.example.kks.archive;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kks.pattern.SpendpatternActivity;
import com.example.kks.R;
import com.example.kks.databinding.FragmentArchiveFolderBinding;

public class ArchiveFolderFragment extends Fragment {

    private FragmentArchiveFolderBinding binding;
    private View root;

    String userId;

    public static ArchiveFolderFragment newInstance() {
        return new ArchiveFolderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArchiveFolderBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //유경님 여기에서 작업하시면 됩니다!

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}