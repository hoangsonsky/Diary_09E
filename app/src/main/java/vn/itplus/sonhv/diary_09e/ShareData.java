package vn.itplus.sonhv.diary_09e;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MyPC on 23/08/2016.
 */
public class ShareData extends AsyncTask<String,String, String> {

    Context mContext;
    SQLConst mSqlConst;
    String mTitle, mWrite, mLocation;



    @Override
    protected String doInBackground(final String... params) {
        FacebookSdk.sdkInitialize(mContext);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions((Activity) mContext, permissionNeeds);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook(params[0].toString());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return null;
    }

    private void sharePhotoToFacebook(String str) {

//        String a = edt_title.getText().toString();
//        String c = edt_write.getText().toString();
//        String c = tv_location.getText().toString();
        Bitmap bitmap = null;
//        SharePhoto photo;

        Cursor cursor1 = mSqlConst.database.query("DSImg", null, null, null, null, null, null, null);
        cursor1.moveToFirst();
        while (cursor1.isAfterLast() == false) {
            if (cursor1.getString(0).equals(str)) {
                Log.e("mID ", str + "");
                byte[] encodeByte = Base64.decode(cursor1.getBlob(1), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .setCaption("Title : " + mTitle + "\n" + mLocation + "\nContent : " + mWrite)
                        .build();

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                ShareApi.share(content, null);
            }
            cursor1.moveToNext();
        }


    }
}
