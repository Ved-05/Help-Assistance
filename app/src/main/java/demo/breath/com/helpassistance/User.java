package demo.breath.com.helpassistance;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name;
    private String ID;
    private String dateOfBirth;
    private String gender;
    private String mobileNumber;

    public User(String name, String user_id, String date_of_birth, String gender, String mobile_number) {

        this.name = name;
        this.ID = user_id;
        this.dateOfBirth = date_of_birth;
        this.gender = gender;
        this.mobileNumber = mobile_number;
    }

    public User() {

    }

    protected User(Parcel in) {
        name = in.readString();
        ID = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        mobileNumber = in.readString();
    }
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String user_id) {
        this.ID = user_id;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.dateOfBirth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobile_number(String mobile_number) {
        this.mobileNumber = mobile_number;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(ID);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(mobileNumber);
    }
}