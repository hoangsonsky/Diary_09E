package vn.itplus.sonhv.diary_09e;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class WriteFragment extends Fragment {

    RecyclerView mRecyclerView;
    SQLConst sqlConst;
    String mUser;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_IMAGE_SELECT = 2;
    String check = "";
    EditText edt_write, edt_title;
    Button btn_save, btn_edit;
    TextView tv_location;
    int idItem;
    ImageView imv_keybroad, imv_takephoto, imv_picture, imv_location, imv_icon, imv_share, imgIcon;
    LinearLayout ll_tool;
    public static int mIcon = R.drawable.iconn4;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    int mIdImg;
    String str;
    ArrayList<Entity> imageViews = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlConst = new SQLConst(getContext());
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        ((Main2Activity) getActivity())
                .setActionBarTitle("New post");
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        init(view);

        imv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_IMAGE_SELECT);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentAddItem();
            }
        });

        imv_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        imv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker(getContext());
                if (gps.canGetLocation()) {
                    tv_location.setText(gps.getAddress());
                } else
                    gps.showSettingsAlert();
            }
        });

        imv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData();
            }
        });

        imv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GridView123(getContext(), imgIcon);
            }
        });

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GridView123(getContext(), imgIcon);
            }
        });

        sizeLinear();

        return view;


    }

    private void init(View view) {
        edt_write = (EditText) view.findViewById(R.id.edt_write);
        edt_title = (EditText) view.findViewById(R.id.edt_title);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_edit = (Button) view.findViewById(R.id.btn_Edit);
        ll_tool = (LinearLayout) view.findViewById(R.id.ll_tool);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        imv_keybroad = (ImageView) view.findViewById(R.id.imv_keybroad);
        imv_takephoto = (ImageView) view.findViewById(R.id.imv_takephoto);
        imv_picture = (ImageView) view.findViewById(R.id.imv_picture);
        imv_location = (ImageView) view.findViewById(R.id.imv_location);
        imv_icon = (ImageView) view.findViewById(R.id.imv_icon);
        imv_share = (ImageView) view.findViewById(R.id.imv_share);
        imgIcon = (ImageView) view.findViewById(R.id.imgIcon);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(llm);

        Bundle bundle = getArguments();
        mUser = bundle.getString(DiaryListFragment.USER);
        idItem = bundle.getInt(DiaryListFragment.ID);
        if (idItem == -1) {
            setFragmentAddItem();
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnClickSave();
                }
            });
        } else {
            setFragmentUpdate();
        }

        Cursor cursor = sqlConst.database.query("DSDiary", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            mIdImg = cursor.getInt(0);
            cursor.moveToNext();
        }
    }

    private void setFragmentAddItem() {
        btn_edit.setVisibility(View.GONE);
        ll_tool.setVisibility(View.VISIBLE);
        imv_keybroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_title.equals(""))
                    edt_title.requestFocus();
                else
                    edt_write.requestFocus();
            }
        });
    }


    private void dispatchTakePictureIntent() {

        check = "";
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

    }


    //===============================================

    private void setOnClickSave() {
        String title, content;
        title = edt_title.getText().toString();
        content = edt_write.getText().toString();
        String location = tv_location.getText().toString();
        if (title.equals("")) {
            Toast.makeText(getContext(), "Tiêu đề bài viết không được để trống", Toast.LENGTH_LONG).show();
            edt_title.requestFocus();
        } else if (content.equals("")) {
            Toast.makeText(getContext(), "Nội dung bài viết không được để trống", Toast.LENGTH_LONG).show();
            edt_write.requestFocus();
        } else {
            if (sqlConst.checkTitle(title)) {
                edt_title.requestFocus();
            } else {
                DateTime time = new DateTime();
                time.showDate();
                time.showTime();
                Log.e("setOnClickSave", mIcon + "");
                sqlConst.insertData(title, content, "" + mIdImg, location, mIcon, time.getDate(), time.getTime(), mUser);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

                mIdImg++;
            }

        }
    }

    public void setFragmentUpdate() {
        ll_tool.setVisibility(View.INVISIBLE);
        final Cursor cursor = sqlConst.database.query("DSDiary", null, "id=?",
                new String[]{idItem + ""}, null, null, null, null);
        cursor.moveToFirst();
        String ids = "";
        while (cursor.isAfterLast() == false) {
            ids = cursor.getString(0);
            edt_title.setText(cursor.getString(1));
            edt_write.setText(cursor.getString(2));
            imgIcon.setImageResource(cursor.getInt(5));
            str = cursor.getString(3);

            Cursor cursor1 = sqlConst.database.query("DSImg", null, "imgage=?",
                    new String[]{cursor.getString(3)}, null, null, null, null);
            cursor1.moveToFirst();
            while (cursor1.isAfterLast() == false) {
                if (cursor.getString(3).equals(cursor1.getString(0))) {
                    Entity entity = new Entity();
                    ConvertBitmapAndString convert = new ConvertBitmapAndString();
                    entity.setImage(convert.StringToBitMap(cursor1.getString(1)));//cursor1.getString(1)
                    imageViews.add(entity);
                }
                cursor1.moveToNext();

            }
            Adapter adapter = new Adapter(imageViews, getContext());
            mRecyclerView.setAdapter(adapter);

            tv_location.setText(cursor.getString(4));
            cursor.moveToNext();
        }
        final String finalId = ids;
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlConst.updateData(finalId, edt_title.getText().toString(),
                        edt_write.getText().toString(), tv_location.getText().toString());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }

    DiaryListFragment diaryListFragment;

    //==========================================
    //share facebook
    private void shareData() {
        check = "share";
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this, permissionNeeds);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //
    private void sharePhotoToFacebook() {

        String a = edt_title.getText().toString();
        String b = edt_write.getText().toString();
        String c = tv_location.getText().toString();

        Cursor cursor1 = sqlConst.database.query("DSImg", null, null, null, null, null, null, null);
        cursor1.moveToFirst();
        while (cursor1.isAfterLast() == false) {
            if (cursor1.getString(0).equals(str)) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setImageUrl(Uri.parse(cursor1.getString(1)))
                        .setCaption("Title : " + a + "\n" + c + "\nContent : " + b)
                        .build();

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                ShareApi.share(content, null);
            }
            cursor1.moveToNext();
        }


    }
    //=============================================


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (check.equals("share")) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == MainActivity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ConvertBitmapAndString string =new ConvertBitmapAndString();
//                Log.e("bbbbbbbbb", data.getData().toString() + "");
                addImage(bitmap);
                sqlConst.insertDataAnh("" + mIdImg,
                        string.BitMapToString(bitmap));
            } else {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
//                Log.e("aaaaaaaaa", data.getData().toString() + "");
                addImage(BitmapFactory.decodeFile(picturePath));
                ConvertBitmapAndString string =new ConvertBitmapAndString();
                sqlConst.insertDataAnh("" + mIdImg,
                        string.BitMapToString(BitmapFactory.decodeFile(picturePath)));
            }

        }
        check = "";


    }

    private void sizeLinear() {
        DisplayMetrics display = this.getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_tool.getLayoutParams();
        params.height = display.heightPixels / 13;
        params.width = display.widthPixels;
    }

    private void addImage(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            Entity entity = new Entity();
            entity.setImage(imageBitmap);
            imageViews.add(entity);
            Adapter adapter = new Adapter(imageViews, getContext());
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}