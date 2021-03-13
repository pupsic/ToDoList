package com.example.todolist.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.todolist.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

interface makeTag{
    String run(String str);
}

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewGroup rootView;
    private int amount_of_elements=0;
    private Button del_task;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button add_task = (Button) root.findViewById(R.id.add_task);
        rootView = (ViewGroup) root.findViewById(R.id.tasks);
        recover();
        amount_of_elements=0;
        for(int key=0;key<amount_of_elements;key++){
            addTask(key);

        }

        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
                rebootFragment();

            }
        });

        return root;
    }


    private String makeKey(String type ,int id){
        String unique_id = String.valueOf(id);
        return type + unique_id;
    }


    public void addTask(){
        Button button = new Button(getActivity()); // Need to provide the context, the Activity
        EditText editText = new EditText(getActivity());
        LinearLayout field = new LinearLayout(getActivity());
        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams editText_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams field_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        field.setId(amount_of_elements);
        button.setId(amount_of_elements);
        editText.setId(amount_of_elements);
        button_params.weight = 5.0f;
        editText_params.weight = 1.0f;
        field.addView(editText, editText_params);
        field.addView(button, button_params);
        amount_of_elements++;
        rootView.addView(field, field_params);

    }

    public void addTask(int key){
        Button button = new Button(getActivity()); // Need to provide the context, the Activity
        EditText editText = new EditText(getActivity());
        LinearLayout field = new LinearLayout(getActivity());
        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams editText_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams field_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        field.setId(key);
        editText.setId(key);
        button.setId(key);
        button_params.weight = 5.0f;
        editText_params.weight = 1.0f;
        field.addView(editText, editText_params);
        field.addView(button, button_params);
        rootView.addView(field, field_params);

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
        editor.putInt("amount_of_elements", amount_of_elements);
        editor.commit();
    }

    private void recover(){
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        amount_of_elements = settings.getInt("amount_of_elements",0);
    }



    public void delTask(int id_of_task){

    }

}