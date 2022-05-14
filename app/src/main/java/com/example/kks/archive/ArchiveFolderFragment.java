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

import com.example.kks.MyProfileActivity;
import com.example.kks.pattern.SpendpatternActivity;
import com.example.kks.R;
import com.example.kks.databinding.FragmentArchiveFolderBinding;

public class ArchiveFolderFragment extends Fragment {

    private ArchiveFolderViewModel folderViewModel;
    private FragmentArchiveFolderBinding binding;
    String userId;

    public static ArchiveFolderFragment newInstance() {
        return new ArchiveFolderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        folderViewModel =
                new ViewModelProvider(this).get(ArchiveFolderViewModel.class);


        //binding = FragmentArchiveFolderBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_archive_folder, container, false);
        binding.setArchiveFolderFragment(this);
        View view = binding.getRoot();


        //val num1 = arguments?.getInt("num1")
        Bundle extra = this.getArguments();
        if (extra != null) {
            extra = getArguments();
            userId = extra.getString("userId");
        }


        return view;
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

    public void goProfile(View view){
        Intent intent = new Intent(getActivity(), MyProfileActivity.class);
        //intent.putExtra("user_id", userId);
        //intent.putExtra("nickname", nickname);
        //intent.putExtra("userImage", userImg);
        startActivity(intent);
        //getActivity().finish();
    }

    public void goPattern(View view){
        //Intent intent = new Intent(getApplicationContext(), PatternContent.class);
        Intent intent = new Intent(getActivity(), SpendpatternActivity.class);
        //intent.putExtra("user_id", userId);
        //intent.putExtra("nickname", nickname);
        //intent.putExtra("userImage", userImg);
        startActivity(intent);
        //getActivity().finish();
    }
}