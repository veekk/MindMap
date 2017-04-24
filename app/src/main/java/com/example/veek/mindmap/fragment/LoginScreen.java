package com.example.veek.mindmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.util.CustomFragmentManager;
import com.example.veek.mindmap.util.CustomPreferenceManager;

/**
 * Crafted by veek on 22.12.15 with love ♥
 */
public class LoginScreen extends Fragment {

    Button buttonSignUp;
    Button buttonLogin;

    EditText etLog;
    EditText etPass;

    CheckBox chAutoLog;


    CustomFragmentManager fragmentManager = CustomFragmentManager.getInstance();
    CustomPreferenceManager preferenceManager = CustomPreferenceManager.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_screen, container, false);


        buttonSignUp = (Button) rootView.findViewById(R.id.button6);
        buttonLogin = (Button) rootView.findViewById(R.id.button7);

        etLog = (EditText) rootView.findViewById(R.id.textLog);
        etPass = (EditText) rootView.findViewById(R.id.textPass);

        chAutoLog = (CheckBox) rootView.findViewById(R.id.chAutoLog);


        preferenceManager.init(getActivity().getApplicationContext(), "GlobalPreferences");
        chAutoLog.setChecked(preferenceManager.getState("Auto-Login"));

        if (chAutoLog.isChecked()) {
            fragmentManager.setFragment(new MindMapListFragment(), false);

        }


        chAutoLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.init(getActivity().getApplicationContext(), "GlobalPreferences");
                preferenceManager.putState("Auto-Login", chAutoLog.isChecked());
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.setFragment(new SignUpFragment(), true);

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLog.getText().toString().equals("") || etLog.getText().toString().equals(" ")){
                    Toast.makeText(getActivity(), "Enter your login", Toast.LENGTH_SHORT).show();
                } else preferenceManager.init(getActivity().getApplicationContext(), etLog.getText().toString());
                if (etPass.getText().toString().equals("") || etPass.getText().toString().equals(" ")){
                    Toast.makeText(getActivity(), "Enter your password", Toast.LENGTH_SHORT).show();
                } else if (preferenceManager.getString("Password").equals(etPass.getText().toString())){
                    fragmentManager.setFragment(new MindMapListFragment(), false);
                    preferenceManager.init(getActivity().getApplicationContext(), "GlobalPreferences");
                    preferenceManager.putState("Auto-Login", chAutoLog.isChecked());
                    preferenceManager.putString("LastLogin", etLog.getText().toString());
                } else Toast.makeText(getActivity(), "Login or password is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }




}