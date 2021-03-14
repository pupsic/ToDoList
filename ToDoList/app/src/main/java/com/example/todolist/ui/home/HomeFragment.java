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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.todolist.R;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private ViewGroup rootView;
    private int amountOfTasks =1;
    private static final String PREFS_NAME = "SavedValuesHomeFragment";
    private static final String KEY_SHARED_PREFERENCES = "AmountOfTask";
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
        //amountOfTasks = 5;
        System.out.println("aofTask: "+amountOfTasks);
        for(int key = 0; key< amountOfTasks; key++){
            addTask(key);

        }
        add_task.setOnClickListener(this);
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
                rebootFragment();
                System.out.println("aofTask: "+amountOfTasks);
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();
        delTask(Id);
        System.out.println("id: "+Id);
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

        field.setId(amountOfTasks);

        button.setId(amountOfTasks);
        button.setOnClickListener(this);
        editText.setId(amountOfTasks);

        button_params.weight = 5.0f;
        editText_params.weight = 1.0f;
        field.addView(editText, editText_params);
        field.addView(button, button_params);
        amountOfTasks++;
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
        button.setOnClickListener(this);
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
        editor.putInt(KEY_SHARED_PREFERENCES, amountOfTasks);
        editor.commit();
    }

    private void recover(){
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        amountOfTasks = settings.getInt(KEY_SHARED_PREFERENCES,0);
    }



    public void delTask(int id_of_task){
        rootView.removeViewAt(id_of_task);
        amountOfTasks--;
        store();
        //rebootFragment();
    }

}