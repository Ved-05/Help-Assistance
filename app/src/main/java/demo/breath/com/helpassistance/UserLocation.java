package demo.breath.com.helpassistance;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserLocation {

    private User user;
    private GeoPoint geo_point;
    private @ServerTimestamp Date timestamp;
    private boolean assistanceRequired;
    private boolean need_Accident_Assistance;
    private boolean need_Medical_Assistance;
    private boolean need_Terrorism_Assistance;
    private boolean need_Harassment_Assistance;

    public UserLocation(User user, GeoPoint geo_point, Date timestamp) {
        this.user = user;
        this.geo_point = geo_point;
        this.timestamp = timestamp;
        assistanceRequired = false;
        need_Accident_Assistance = false;
        need_Harassment_Assistance = false;
        need_Medical_Assistance = false;
        need_Terrorism_Assistance = false;
        assistanceRequired = false;
    }

    public boolean isAssistanceRequired() {
        return assistanceRequired;
    }

    public void setAssistanceRequired(boolean assistanceRequired) {
        this.assistanceRequired = assistanceRequired;
    }

    public boolean isNeed_Accident_Assistance() {
        return need_Accident_Assistance;
    }

    public void setNeed_Accident_Assistance(boolean need_Accident_Assistance) {
        this.need_Accident_Assistance = need_Accident_Assistance;
    }

    public boolean isNeed_Medical_Assistance() {
        return need_Medical_Assistance;
    }

    public void setNeed_Medical_Assistance(boolean need_Medical_Assistance) {
        this.need_Medical_Assistance = need_Medical_Assistance;
    }

    public boolean isNeed_Terrorism_Assistance() {
        return need_Terrorism_Assistance;
    }

    public void setNeed_Terrorism_Assistance(boolean need_Terrorism_Assistance) {
        this.need_Terrorism_Assistance = need_Terrorism_Assistance;
    }

    public boolean isNeed_Harassment_Assistance() {
        return need_Harassment_Assistance;
    }

    public void setNeed_Harassment_Assistance(boolean need_Harassment_Assistance) {
        this.need_Harassment_Assistance = need_Harassment_Assistance;
    }

    public UserLocation() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GeoPoint getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "user=" + user +
                ", geo_point=" + geo_point +
                ", timestamp=" + timestamp +
                ", assistanceRequired=" + assistanceRequired +
                ", need_Accident_Assistance=" + need_Accident_Assistance +
                ", need_Medical_Assistance=" + need_Medical_Assistance +
                ", need_Terrorism_Assistance=" + need_Terrorism_Assistance +
                ", need_Harassment_Assistance=" + need_Harassment_Assistance +
                '}';
    }
}
