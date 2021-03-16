package com.example.todolist.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.todolist.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private ViewGroup groupTask;
    private static final String PREFS_NAME = "SavedValuesHomeFragment";
    private static final String KEY_SHARED_PREFERENCES = "AmountOfTask";
    private static final String KEY_SHARED_PREFERENCES_STRING = "TextForTask_";
    private int amountOfTasks;
    private ArrayList<String> textsOfTasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button add_task = (Button) root.findViewById(R.id.add_task);
        groupTask = (ViewGroup) root.findViewById(R.id.tasks);
        textsOfTasks = new ArrayList<String>();
        recover();

        for(int key = 0; key < amountOfTasks; key++){

            addTask(key);

        }
        //add_task.setOnClickListener(this);
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textsOfTasks.add("");
                addTask();
                System.out.println("aofTask: "+amountOfTasks);
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();
        System.out.println("id: "+Id);
        if (Id == -1) {
            String tag = v.getTag().toString();

            System.out.println("Tag: "+tag);
        }
        else delTask(Id);
    }


    public void addTask(){
        packLayoutTask(amountOfTasks);
        amountOfTasks++;
        rebootFragment();
    }

    public void addTask(int key){
        packLayoutTask(key);
    }

    private void saveIntoArray(final String text,int id){
        textsOfTasks.set(id,text);
    }

    private void packLayoutTask(final int id){

        ImageButton buttonDone = new ImageButton(getActivity());
        ImageButton buttonDel = new ImageButton(getActivity());
        LinearLayout field = new LinearLayout(getActivity());
        final EditText editText = new EditText(getActivity());

        LinearLayout.LayoutParams buttons_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams editText_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams field_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        field.setId(id);
        editText.setId(id);
        buttonDone.setId(id);
        buttonDel.setId(id);
        editText.setText(textsOfTasks.get(id));
        buttonDone.setTag(addStringAndId("button_done_",id));
        buttonDel.setTag(addStringAndId("button_del_",id));
        buttonDone.setImageResource(R.drawable.ic_baseline_done_24);
        buttonDel.setImageResource(R.drawable.ic_baseline_delete_24);

        buttonDone.setOnClickListener(this);
        buttonDel.setOnClickListener(this);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
            @Override
            public void afterTextChanged(Editable s) {  saveIntoArray(editText.getText().toString(),id);       }
        });

        field.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.border));
        field_params.setMargins(0,0,0,8);
        buttons_params.weight = 5.0f;
        editText_params.weight = 1.0f;

        field.addView(editText, editText_params);
        field.addView(buttonDone, buttons_params);
        field.addView(buttonDel, buttons_params);
        groupTask.addView(field, field_params);
    }



    public void delTask(int id_of_task){
        View field = groupTask.getChildAt(id_of_task);
        groupTask.removeView(field);
        amountOfTasks--;
        textsOfTasks.remove(id_of_task);
        store();
        rebootFragment();
    }

    private String addStringAndId(String input , int id){
        String temp = input;
        String unique_id = String.valueOf(id);
        return temp + unique_id;
    }


    private void rebootFragment(){
        HomeFragment fragment = (HomeFragment)
                getFragmentManager().findFragmentById(((ViewGroup)getView().getParent()).getId());
        getFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
        store();
    }

    private void store(){
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_SHARED_PREFERENCES, amountOfTasks);
        for(int index = 0; index < textsOfTasks.size(); index++){
            editor.putString(addStringAndId(KEY_SHARED_PREFERENCES_STRING,index), textsOfTasks.get(index));
        }
        editor.commit();
    }

    private void recover(){
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        amountOfTasks = settings.getInt(KEY_SHARED_PREFERENCES,0);
        settings.getString(KEY_SHARED_PREFERENCES_STRING,"");
        for(int index=0; index<amountOfTasks;index++){
            textsOfTasks.add(index,settings.getString(addStringAndId(KEY_SHARED_PREFERENCES_STRING,index),"")); ;
        }
    }

}