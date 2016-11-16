package vn.itplus.sonhv.diary_09e;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FogotPassFragment extends Fragment {
    EditText edt_answer;
    TextView tv_question;
    Button btn_retrieve;
    SQLConst sqlConst;
    String get_question, get_answer, get_receive_answer;
    AutoCompleteTextView edt_auto;
    ArrayList<String> arr = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fogot_pass, container, false);
        edt_answer = (EditText) view.findViewById(R.id.edt_answer);
        tv_question = (TextView) view.findViewById(R.id.tv_question);
        btn_retrieve = (Button) view.findViewById(R.id.btn_retrieve);
        edt_auto = (AutoCompleteTextView) view.findViewById(R.id.edtAutoName);
        sqlConst = new SQLConst(getContext());
        dataEdtAuto();


        edt_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = edt_auto.getText().toString();
                Cursor cursor = sqlConst.database.query("Account", null, null, null, null, null, null, null);
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    if (name.equals(cursor.getString(0))) {
                        get_question = cursor.getString(2);
                        get_answer = cursor.getString(3);
                        break;
                    }
                    cursor.moveToNext();
                }
                tv_question.setText(get_question + "");
            }
        });
        btn_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_answer.getText().toString().equals(get_answer)) {
                    AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                    b.setTitle("Your Password Here:");
                    b.setMessage("" + get_answer);
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            AcountFragment acountFragment = new AcountFragment();
                            transaction.replace(R.id.rlt, acountFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                    b.create().show();
                } else {
                    Toast.makeText(getContext(), "Your password is wrong, Pleas input again!", Toast.LENGTH_LONG).show();
                    edt_answer.setText("");
                    edt_answer.requestFocus();
                }
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

}
