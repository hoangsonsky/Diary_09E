package vn.itplus.sonhv.diary_09e;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiaryListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView lv;
    TextView tv;
    MyAdapter adapter;
    ArrayList<ItemEntity> arrayList = new ArrayList<>();
    static String ID = "id";
    static String USER = "user";
    SQLConst sqlConst;
    DateTime time;
    String mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlConst = new SQLConst(getContext());
        setHasOptionsMenu(true);
        time = new DateTime();
        time.showDate();
        time.showTime();
        getBundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, container, false);
        lv = (ListView) view.findViewById(R.id.lv_dsDiary);
        tv = (TextView) view.findViewById(R.id.tv);
        arrayList.clear();
        showData();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        adapter = new MyAdapter(arrayList, R.layout.layout_item);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        setListViewEmpty();

        return view;
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null)
            mUser = bundle.getString(SignInFragment.USER_NAME);
    }

    private void setListViewEmpty() {
        Cursor cursor = sqlConst.database.query("DSDiary", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        int id = 0;
        while (cursor.isAfterLast() == false) {
            id = cursor.getInt(0);
            cursor.moveToNext();
        }

        if (id == 0) {
            lv.setVisibility(View.GONE);
        } else {
            lv.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        }
    }


    public void showData() {
        SQLConst sqlConst = new SQLConst(getContext());
        Cursor cursor = sqlConst.database.query("DSDiary", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (mUser != null && mUser.equals(cursor.getString(8))) {
                ItemEntity smile = new ItemEntity();
                smile.setId(cursor.getInt(0));
                smile.setTitle(cursor.getString(1));
                smile.setLocation(cursor.getString(4));
                smile.setContent(cursor.getString(2));
                smile.setTime(cursor.getString(7));
                smile.setdate(cursor.getString(6));
                smile.setIcon(cursor.getInt(5));
                arrayList.add(0, smile);
            }
            cursor.moveToNext();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        android.support.v7.widget.SearchView.OnQueryTextListener textChangeListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter1(newText);
                Toast.makeText(getContext(), newText, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        WriteFragment contentItemFragment = new WriteFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ID, arrayList.get(position).getId());
        bundle.putString(USER, mUser);
        contentItemFragment.setArguments(bundle);

        transaction.replace(R.id.rltNavigation, contentItemFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final TextView title = (TextView) view.findViewById(R.id.tv_title);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to delete : ");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                sqlConst.deleteData(title.getText().toString());
            }
        });
        builder.create().show();
        return true;
    }


}
