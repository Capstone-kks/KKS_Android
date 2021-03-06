package com.example.kks;

import android.content.Context;

import androidx.databinding.ObservableField;

public class NameViewModel {
    private ObservableField<String> newName;

    public NameViewModel(){
        this.newName = new ObservableField<>("new Nickname");
    }

    public ObservableField<String> getText(){
        return newName;
    }
}
