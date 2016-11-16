package vn.itplus.sonhv.diary_09e;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Administrator on 29/03/2016.
 */
public class SQLConst {
    //    private final String TAG ="SQLConst" ;
    SQLiteDatabase database;
    Context mContext;

    public SQLConst(Context mContext) {
        this.mContext = mContext;
        initData();
        createData1();
        createData2();
        creatDataUser();
    }

    public void initData() {
        String data_name = "datatest.db";
        database = mContext.openOrCreateDatabase(data_name, Context.MODE_PRIVATE, null);
    }

    private void createData2() {
        String dataDSiary = "CREATE TABLE IF NOT EXISTS `DSDiary` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`title`\tTEXT NOT NULL,\n" +
                "\t`content`\tTEXT NOT NULL,\n" +
                "\t`imgage`\tTEXT NOT NULL CONSTRAINT anh REFERENCES DSImg(imgage) ON DELETE CASCADE,\n" +
                "\t`location`\tTEXT NOT NULL,\n" +
                "\t`icon`\tINTEGER NOT NULL,\n" +
                "\t`date`\tTEXT NOT NULL,\n" +
                "\t`time`\tTEXT NOT NULL,\n" +
                "\t`username`\tTEXT NOT NULL\n" +
                ");";
        database.execSQL(dataDSiary);
    }

    private void createData1() {
        String dataDSImg = "CREATE TABLE IF NOT EXISTS `DSImg` (\n" +
                "\t`imgage`\tTEXT NOT NULL,\n" +
                "\t`linkImg`\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(imgage,linkImg)\n" +
                ");";
        database.execSQL(dataDSImg);
    }

    private void creatDataUser() {
        String data_user = "CREATE TABLE IF NOT EXISTS `Account` (\n" +
                "\t`username`\tTEXT NOT NULL,\n" +
                "\t`password`\tTEXT NOT NULL,\n" +
                "\t`question`\tTEXT NOT NULL,\n" +
                "\t`answer`\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(username)\n" +
                ");";
        database.execSQL(data_user);
    }

    public void insertUser(String user, String pass, String question, String answer) {
        ContentValues values = new ContentValues();
        values.put("username", user);
        values.put("password", pass);
        values.put("question", question);
        values.put("answer", answer);
        int x = (int) database.insert("Account", null, values);
        if (x != -1)
            Toast.makeText(mContext, "Creat an acount success!", Toast.LENGTH_LONG).show();
        else Toast.makeText(mContext, "Failed!", Toast.LENGTH_LONG).show();
    }

    public void insertData(String TieuDe, String NoiDung, String anh, String diadiem, int icon, String ntn, String gio, String username) {
        ContentValues values = new ContentValues();
        values.put("title", TieuDe);
        values.put("content", NoiDung);
        values.put("imgage", anh);
        values.put("location", diadiem);
        values.put("icon", icon);
        values.put("date", ntn);
        values.put("time", gio);
        values.put("username", username);
        database.insert("DSDiary", null, values);
    }

    public void insertDataAnh(String anh, String tenanh) {
        ContentValues values = new ContentValues();
        values.put("imgage", anh);
        values.put("linkImg", tenanh);
        database.insert("DSImg", null, values);
    }

    public void updateData(String id, String TieuDe, String NoiDung, String location) {
        ContentValues values = new ContentValues();
        values.put("title", TieuDe);
        values.put("content", NoiDung);
        values.put("location", location);
        database.update("DSDiary", values, "id =?", new String[]{id});

    }

    public void deleteData(String title) {
        database.delete("DSDiary", "title=?", new String[]{title});
    }

    public void updateAccount(String userName, String Pass) {
        ContentValues values = new ContentValues();
        values.put("username", userName);
        values.put("password", Pass);
        database.update("Account", values, "username =?", new String[]{userName});

    }

    public boolean checkUser(String name) {
        Cursor cursor = database.query("Account", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (name.equals(cursor.getString(0))) {
                return true;
//                break;
            }
            cursor.moveToNext();
        }
        return false;
    }

    public boolean checkTitle(String title) {
        Cursor cursor = database.query("DSDiary", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (title.equals(cursor.getString(1))) {
                Toast.makeText(mContext, "Tiêu đề bài viết bị trùng. Vui lòng sửa lại tiêu đề.", Toast.LENGTH_LONG).show();
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }
}
