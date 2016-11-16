package vn.itplus.sonhv.diary_09e;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MyPC on 23/08/2016.
 */
public class DateTime {

    String mDate, mTime;



    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void showDate() {
        Date date = new Date();
        String strDate1 = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strDate1);
        mDate = dateFormat.format(date);
    }

    public void showTime() {
        Date date = new Date();
        String strDate24 = "HH:mm";
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat(strDate24);
        mTime = dateFormat.format(date);
    }
}
