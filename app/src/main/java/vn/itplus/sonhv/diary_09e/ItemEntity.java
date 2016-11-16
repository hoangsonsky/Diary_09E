package vn.itplus.sonhv.diary_09e;

/**
 * Created by Administrator on 13/03/2016.
 */
public class ItemEntity {

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private int mIcon;
    private String mTime;
    private String mContent;
    private String mdate;
    private String mTitle;
    private String mLocation;
    private int mImg;

    public int getImg() {
        return mImg;
    }

    public void setImg(int mImg) {
        this.mImg = mImg;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getdate() {
        return mdate;
    }

    public void setdate(String mdate) {
        this.mdate = mdate;
    }
}
