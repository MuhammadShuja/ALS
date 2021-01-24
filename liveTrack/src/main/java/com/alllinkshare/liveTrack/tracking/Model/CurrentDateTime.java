package com.alllinkshare.liveTrack.tracking.Model;

import java.util.Date;

public class CurrentDateTime extends CurrentLocation {
    Date startDateTime,endDateTime;
    public CurrentDateTime(String ride_id, String longitude, String latitude,
                           Date startDateTime,Date endDateTime) {
        super(ride_id, longitude, latitude);
        this.startDateTime= startDateTime;
        this.endDateTime =endDateTime;
    }
    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
}
