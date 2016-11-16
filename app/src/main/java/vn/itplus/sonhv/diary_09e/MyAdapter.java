package vn.itplus.sonhv.diary_09e;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 13/03/2016.
 */
public class MyAdapter extends BaseAdapter {

    ArrayList<ItemEntity> arrayList;
    ArrayList<ItemEntity> search;
    int idLayout;

    public MyAdapter(ArrayList<ItemEntity> arrayList, int idLayout) {
        this.arrayList = arrayList;
        this.idLayout = idLayout;
        search = new ArrayList<ItemEntity>();
        search.addAll(arrayList);
    }

    private class MyViewHolder {
        ImageView icon;
        TextView tv_Content, tv_time, tv_date;
        TextView tv_location, tv_title;
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
            convertView = inflater.inflate(R.layout.layout_item, null);

            viewHolder = new MyViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.tv_Content = (TextView) convertView.findViewById(R.id.tv_Content);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (MyViewHolder) convertView.getTag();

        ItemEntity item = (ItemEntity) getItem(position);
        item.getId();
        viewHolder.icon.setImageResource(item.getIcon());
        viewHolder.tv_Content.setText(item.getContent());
        viewHolder.tv_time.setText(item.getTime());
        viewHolder.tv_date.setText(item.getdate());
        viewHolder.tv_location.setText(item.getLocation());
        viewHolder.tv_title.setText(item.getTitle());
        return convertView;
    }

    public void getFilter1(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(search);

        } else {
            for (ItemEntity entity : search) {
                if (charText.length() != 0 && entity.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrayList.add(entity);
                }
            }
        }
        notifyDataSetChanged();
    }
}
