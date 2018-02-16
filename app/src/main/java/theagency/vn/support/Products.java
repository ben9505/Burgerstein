package theagency.vn.support;

/**
 * Created by Administrator PC on 3/16/2015.
 */
public class Products {
    String mSymptomID;
    String mNahrstoffID;
    String mWirkung;
    String mDosierung;

    public Products(String mSymptomID, String mNahrstoffID, String mWirkung, String mDosierung) {
        this.mSymptomID = mSymptomID;
        this.mNahrstoffID = mNahrstoffID;
        this.mWirkung = mWirkung;
        this.mDosierung = mDosierung;
    }

    public String getmSymptomID() {
        return mSymptomID;
    }

    public void setmSymptomID(String mSymptomID) {
        this.mSymptomID = mSymptomID;
    }

    public String getmNahrstoffID() {
        return mNahrstoffID;
    }

    public void setmNahrstoffID(String mNahrstoffID) {
        this.mNahrstoffID = mNahrstoffID;
    }

    public String getmWirkung() {
        return mWirkung;
    }

    public void setmWirkung(String mWirkung) {
        this.mWirkung = mWirkung;
    }

    public String getmDosierung() {
        return mDosierung;
    }

    public void setmDosierung(String mDosierung) {
        this.mDosierung = mDosierung;
    }
}
