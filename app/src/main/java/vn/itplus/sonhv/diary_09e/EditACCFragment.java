package vn.itplus.sonhv.diary_09e;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditACCFragment extends android.support.v4.app.Fragment {
    EditText edt_userEdit,edt_OldPass,edt_NewPass,edt_confirmNewPass;
    Button btn_edit;
    SQLConst sqlConst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_acc, container, false);
        sqlConst = new SQLConst(getContext());
        edt_userEdit=(EditText)view.findViewById(R.id.edt_userEdit);
        edt_OldPass=(EditText)view.findViewById(R.id.edt_OldPass);
        edt_NewPass=(EditText)view.findViewById(R.id.edt_NewPass);
        edt_confirmNewPass=(EditText)view.findViewById(R.id.edt_confirmNewPass);
        btn_edit=(Button)view.findViewById(R.id.btn_EditACC);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateACC();
            }
        });
        return view;
    }

    private void updateACC(){
        String newPass=edt_NewPass.getText().toString();
        String confirm=edt_confirmNewPass.getText().toString();
        if (newPass.equals(confirm)){
            String userName=edt_userEdit.getText().toString();
            String oldPass=edt_OldPass.getText().toString();
            Cursor cursor = sqlConst.database.query("Account", null, null, null, null, null, null, null);
            cursor.moveToFirst();
            int cout=0;
            while (cursor.isAfterLast() == false) {
                if (userName.equals(cursor.getString(0))&&oldPass.equals(cursor.getString(1))){
                    sqlConst.updateAccount(userName,newPass);
                    Toast.makeText(getContext(),"Edit successful",Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    DiaryListFragment contentItemFragment = new DiaryListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(SignInFragment.USER_NAME, edt_userEdit.getText().toString());
                    contentItemFragment.setArguments(bundle);
                    transaction.replace(R.id.rltNavigation, contentItemFragment);
                    transaction.commit();
                    cout=1;
                }
                cursor.moveToNext();
            }
            if (cout!=1){
                Toast.makeText(getContext(),"Wrong account",Toast.LENGTH_SHORT).show();
            }
        }else
            Toast.makeText(getContext(),"The new password is not confirmed",Toast.LENGTH_SHORT).show();
    }

}
