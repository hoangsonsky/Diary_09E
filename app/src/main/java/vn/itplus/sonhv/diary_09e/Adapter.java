package vn.itplus.sonhv.diary_09e;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by MyPC on 31/07/2016.
 */
public class Adapter extends RecyclerView.Adapter<MyViewHoder> {
    ArrayList<Entity> arrayList = new ArrayList<>();
    Context mContext;

    public Adapter(ArrayList<Entity> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_layout, null);
        return new MyViewHoder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHoder holder, final int position) {
        int widthSCreen = mContext.getResources().getDisplayMetrics().widthPixels;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthSCreen / 3,
                ViewGroup.LayoutParams.MATCH_PARENT);
        holder.img1.setLayoutParams(params);
//        Glide.with(mContext).load(arrayList.get(position).getImage())
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.img1);
        holder.img1.setImageBitmap(arrayList.get(position).getImage());
//        Picasso.with(mContext).load(arrayList.get(position).getImage())
//                .into(holder.img1);
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = holder.img1.getDrawingCache();

                showBigImage(arrayList.get(position).getImage());
            }
        });
    }

    public void showBigImage(Bitmap bitmap) {
        Dialog builder = new Dialog(mContext);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmap);

        Display display = ((Main2Activity)mContext).getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // ((display.getWidth()*20)/100)
        int height = display.getHeight();// ((display.getHeight()*30)/100)
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                width*2/3,
                height*2/3));//ViewGroup.LayoutParams.MATCH_PARENT
        Log.e("SIZE",ViewGroup.LayoutParams.MATCH_PARENT+"++++"+width);
        builder.show();

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void getFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        arrayList.clear();
        for (Entity entity : arrayList) {
            if (charText.length() != 0 && entity.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                arrayList.add(entity);
            }
        }
        notifyDataSetChanged();
    }
}

class MyViewHoder extends RecyclerView.ViewHolder {
    public ImageView img1;
    //    public TextView tv1;
    public CardView cardView;

    public MyViewHoder(View itemView) {
        super(itemView);
        img1 = (ImageView) itemView.findViewById(R.id.img);
//        tv1 = (TextView) itemView.findViewById(R.id.tv);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
    }

}
