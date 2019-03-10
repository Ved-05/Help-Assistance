package demo.breath.com.helpassistance;

public class UserData {
    public String username;
    public String gender;
    public String dob;
    public String mobileNumber;
    public Boolean set;
    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserData(String username, String gender, String dob, String mobileNumber) {
        this.username = username;
        this.gender = gender;
        this.dob = dob;
        this.mobileNumber = mobileNumber;
        this.set = true;
    }
}
