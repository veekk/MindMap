package com.example.veek.mindmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.util.CustomFragmentManager;
import com.example.veek.mindmap.util.CustomPreferenceManager;

/**
 * Crafted by veek on 22.12.15 with love ♥
 */
public class SignUpFragment extends Fragment {
    Button buttonReg;
    TextView etLogReg;
    TextView etMail;
    TextView etPassReg;
    TextView etRePass;

    CustomFragmentManager fragmentManager = CustomFragmentManager.getInstance();
    CustomPreferenceManager preferenceManager = CustomPreferenceManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sign_up_screen, container, false);

        buttonReg = (Button) rootView.findViewById(R.id.btnReg);
        etLogReg = (EditText) rootView.findViewById(R.id.etLogReg);
        etMail = (EditText) rootView.findViewById(R.id.etMail);
        etPassReg = (EditText) rootView.findViewById(R.id.etPassReg);
        etRePass = (EditText) rootView.findViewById(R.id.etRePass);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLogReg.getText().toString().equals("") || etLogReg.getText().toString().equals(" ")) {
                    Toast.makeText(getActivity(), "Enter your login", Toast.LENGTH_SHORT).show();
                } else if (etMail.getText().toString().equals("") || etMail.getText().toString().equals(" ")) {
                    Toast.makeText(getActivity(), "Enter your mail", Toast.LENGTH_SHORT).show();
                } else if (etPassReg.getText().toString().equals("") || etPassReg.getText().toString().equals(" ")) {
                    Toast.makeText(getActivity(), "Enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    if (etRePass.getText().toString().equals("") || etRePass.getText().toString().equals(" ")) {
                        Toast.makeText(getActivity(), "Repeat your password", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!etPassReg.getText().toString().equals(etRePass.getText().toString())) {
                            Toast.makeText(getActivity(), "Password and RePassword not equals", Toast.LENGTH_SHORT).show();
                        } else {
                            preferenceManager.init(getActivity().getApplicationContext(), etLogReg.getText().toString());
                            if (!preferenceManager.getString("Password").equals("")) {
                                Toast.makeText(getActivity(), "User already exist", Toast.LENGTH_SHORT).show();
                            } else {
                                preferenceManager.putString("Password", etPassReg.getText().toString());
                                preferenceManager.init(getActivity().getApplicationContext(), "GlobalPreferences");
                                preferenceManager.putString("LastLogin", etLogReg.getText().toString());
                                fragmentManager.setFragment(new MindMapListFragment(), false);
                            }

                        }
                    }
                }
            }
        });





        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    fragmentManager.setFragment(new LoginScreen(), false);
                    return true ;
                }
                return false;
            }
        });
    }

}