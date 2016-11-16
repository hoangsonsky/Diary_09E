package vn.itplus.sonhv.diary_09e;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 13/03/2016.
 */
public class AdapterGridView extends BaseAdapter {

    ArrayList<ItemEntity> arrayList;

    public AdapterGridView(ArrayList<ItemEntity> arrayList) {
        this.arrayList = arrayList;
    }

    private class MyViewHolder {
        ImageView iconTT;
        TextView tvTT;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_gridview_layout, null);

            viewHolder = new MyViewHolder();
            viewHolder.iconTT = (ImageView) convertView.findViewById(R.id.iconTT);
            viewHolder.tvTT = (TextView) convertView.findViewById(R.id.tvTT);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (MyViewHolder) convertView.getTag();

        ItemEntity item = (ItemEntity) getItem(position);
        item.getId();
        viewHolder.iconTT.setImageResource(item.getIcon());
        viewHolder.tvTT.setText(item.getTitle());
        return convertView;
    }

}
