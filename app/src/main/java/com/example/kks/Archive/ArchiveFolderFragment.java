package com.example.kks.Archive;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kks.R;
import com.example.kks.databinding.FragmentHomeBinding;
import com.example.kks.info.InfoViewModel;

public class ArchiveFolderFragment extends Fragment {

    private ArchiveFolderViewModel folderViewModel;
    private FragmentHomeBinding binding;

    public static ArchiveFolderFragment newInstance() {
        return new ArchiveFolderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /*
        folderViewModel =
                new ViewModelProvider(this).get(ArchiveFolderViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
        */

        return inflater.inflate(R.layout.fragment_archive_folder, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        folderViewModel = new ViewModelProvider(this).get(ArchiveFolderViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}