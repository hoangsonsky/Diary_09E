package vn.itplus.sonhv.diary_09e;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SignInFragment extends Fragment {
    String get_user, get_password;
    public static String TAG = "SignInFragment";
    public static String USER_NAME = "name";
    EditText edt_password;
    AutoCompleteTextView edt_auto;
    TextView tv_fogot;
    Button btn_signin;
    ArrayList<String> arr = new ArrayList<>();
    SQLConst sqlConst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        sqlConst = new SQLConst(getContext());

        tv_fogot = (TextView) view.findViewById(R.id.tv_fogot);
        edt_password = (EditText) view.findViewById(R.id.edt_password);
        edt_auto = (AutoCompleteTextView) view.findViewById(R.id.edit_auto);
        btn_signin = (Button) view.findViewById(R.id.btn_signin);

        dataEdtAuto();

        tv_fogot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fogotAccount();
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginACC();
            }
        });


        return view;
    }

    private void dataEdtAuto() {
        Cursor cursor = sqlConst.database.query("Account", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arr.add(cursor.getString(0));
            cursor.moveToNext();
        }
        edt_auto.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arr));
    }

    private void fogotAccount() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        FogotPassFragment fogotPassFragment = new FogotPassFragment();
        transaction.replace(R.id.rlt, fogotPassFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loginACC() {
        Cursor cursor = sqlConst.database.query("Account", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        int cout = 0;
        String name = edt_auto.getText().toString();
        String pass = edt_password.getText().toString();

        while (cursor.isAfterLast() == false) {
            get_user = cursor.getString(0);
            get_password = cursor.getString(1);
            if (name.equalsIgnoreCase(get_user) && pass.equals(get_password)) {
                Toast.makeText(getContext(), "Xin chao " + get_user, Toast.LENGTH_LONG).show();
                String user_main = get_user;

                Intent intent = new Intent(getActivity(),Main2Activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString(USER_NAME, user_main);
                intent.putExtras(bundle1);
                startActivity(intent);
                getActivity().finish();
                cout = 1;
            }
            if (cout != 1) {
                Toast.makeText(getContext(), "User or Password are wrong, Please input again!", Toast.LENGTH_LONG).show();
                edt_auto.requestFocus();
            }
            cursor.moveToNext();
        }
    }

}
