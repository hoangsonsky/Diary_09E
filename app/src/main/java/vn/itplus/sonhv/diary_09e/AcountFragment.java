package vn.itplus.sonhv.diary_09e;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class AcountFragment extends Fragment {
    Button btn_signup, btn_signin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_acount, container, false);
        btn_signin=(Button)view.findViewById(R.id.btn_signin);
        btn_signup=(Button)view.findViewById(R.id.btn_signup);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessUser();
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void accessUser(){
        FragmentManager manager= getActivity().getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        SignInFragment signInFragment = new SignInFragment();
        transaction.replace(R.id.rlt, signInFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void createUser(){
        FragmentManager manager= getActivity().getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        SignUpFragment signUpFragment = new SignUpFragment();
        transaction.replace(R.id.rlt, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
