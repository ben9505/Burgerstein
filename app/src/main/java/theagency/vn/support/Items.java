package theagency.vn.support;

/**
 * Created by Administrator PC on 3/18/2015.
 */
public class Items {

    boolean isSelect;
    String mID;
    String mName;

    public Items(boolean isSelect, String mName, String mID) {
        this.isSelect = isSelect;
        this.mName = mName;
        this.mID = mID;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
