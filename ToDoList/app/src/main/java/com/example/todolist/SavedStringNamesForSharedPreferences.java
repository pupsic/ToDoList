package com.example.todolist;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class SavedStringNamesForSharedPreferences {
    public static final String PREFS_NAME = "MyPrefsFile";

    private ArrayList<String> SavedNames = new ArrayList<String>();


    public String getItems(int item) {
        return SavedNames.get(item);
    }

    public void setItems(String name) {
        this.SavedNames.add(name);
    }


}
