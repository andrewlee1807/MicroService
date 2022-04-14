package com.banvien.fc.order.dto.delivery.grabexpress;

import java.io.Serializable;
import java.sql.Timestamp;


public class EstimatedTimelineDTO implements Serializable {
    private Timestamp pickup;
    private Timestamp dropoff;

    public Timestamp getPickup() {
        return pickup;
    }

    public void setPickup(Timestamp pickup) {
        this.pickup = pickup;
    }

    public Timestamp getDropoff() {
        return dropoff;
    }

    public void setDropoff(Timestamp dropoff) {
        this.dropoff = dropoff;
    }
}
