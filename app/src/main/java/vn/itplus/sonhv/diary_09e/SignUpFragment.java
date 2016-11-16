package vn.itplus.sonhv.diary_09e;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpFragment extends Fragment {
    EditText edt_user, edt_pass, edt_confirmPass, edt_question, edt_answer;
    Button btn_creat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        edt_user = (EditText) view.findViewById(R.id.edt_user);
        edt_pass = (EditText) view.findViewById(R.id.edt_pass);
        edt_question = (EditText) view.findViewById(R.id.edt_question);
        edt_answer = (EditText) view.findViewById(R.id.edt_answer);
        edt_confirmPass = (EditText) view.findViewById(R.id.edt_confirmPass);
        btn_creat = (Button) view.findViewById(R.id.btn_creat);


        btn_creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAcount();
            }

        });
        // Inflate the layout for this fragment
        return view;
    }

    private void createAcount() {
        String get_user = edt_user.getText().toString();
        String get_pass = edt_pass.getText().toString();
        String get_question = edt_question.getText().toString();
        String get_confirm = edt_confirmPass.getText().toString();
        String get_answer = edt_answer.getText().toString();
        SQLConst sqlConst = new SQLConst(getContext());

        if (get_pass.equals(get_confirm) && sqlConst.checkUser(get_user) == false) {

            sqlConst.insertUser(get_user, get_pass, get_question, get_answer);

            Toast.makeText(getContext(), "Xin chao " + get_user, Toast.LENGTH_LONG).show();
            String user_main = get_user;

            Intent intent = new Intent(getActivity(),Main2Activity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString(SignInFragment.USER_NAME, user_main);
            intent.putExtras(bundle1);
            startActivity(intent);
            getActivity().finish();

        } else {

            Toast.makeText(getContext(), "password is incorrect, please re-enter", Toast.LENGTH_LONG).show();
            edt_pass.setText("");
            edt_confirmPass.setText("");
            edt_pass.requestFocus();

        }
    }

}
