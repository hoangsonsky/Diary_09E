package vn.itplus.sonhv.diary_09e;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by MyPC on 24/08/2016.
 */
public class GridView123 {
    Context mContext;
    ImageView img;
    public static String TAG = "GridView123";
    public static String ID = "id";

    public GridView123(Context mContext, ImageView img) {
        this.mContext = mContext;
        this.img = img;
        create();
    }

    public void create() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Icon");
        builder.setIcon(android.R.drawable.ic_input_add);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.gridview_layout, null);

        final ArrayList<ItemEntity> playLists = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemEntity entity = new ItemEntity();
            entity.setIcon(R.drawable.iconn1+i);
//            entity.setTitle("funy");
            playLists.add(entity);
        }
        AdapterGridView gridView = new AdapterGridView(playLists);
        GridView gridView1 = (GridView) view.findViewById(R.id.gridView);
        gridView1.setAdapter(gridView);
        builder.setView(view);
//        builder.create().show();
        final AlertDialog ad = builder.show();

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < playLists.size(); i++) {
                    if (position == i) {
                        Log.e("gridView1",WriteFragment.mIcon+"");
                        Handler handler =new Handler();
                        final int finalI = i;
                        Runnable runnable =new Runnable() {
                            @Override
                            public void run() {
                                WriteFragment.mIcon = playLists.get(finalI).getIcon();
                                img.setImageResource(WriteFragment.mIcon);
                                ad.dismiss();
                                Log.e("gridView1",WriteFragment.mIcon+"");
                            }
                        };
                        handler.postDelayed(runnable,100);
                    }
                }
            }
        });
    }
}
