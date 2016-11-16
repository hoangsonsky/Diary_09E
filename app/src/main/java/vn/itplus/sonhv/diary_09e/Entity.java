package vn.itplus.sonhv.diary_09e;

import android.graphics.Bitmap;

/**
 * Created by MyPC on 31/07/2016.
 */
public class Entity {
    private Bitmap mImage;
    private String mName;

//    public String getImage() {
//        return mImage;
//    }
//
//    public void setImage(String mImage) {
//        this.mImage = mImage;
//    }


    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
