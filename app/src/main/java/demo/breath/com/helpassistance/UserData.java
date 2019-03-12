package demo.breath.com.helpassistance;

public class UserData {

    public String name;
    public String gender;
    public String date_of_birth;
    public String mobile_number;
    public String id;

    public UserData(String name, String gender, String date_of_birth, String mobile_number, String ID) {
        this.name = name;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.mobile_number = mobile_number;
        this.id = ID;
    }

    public UserData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }
}
