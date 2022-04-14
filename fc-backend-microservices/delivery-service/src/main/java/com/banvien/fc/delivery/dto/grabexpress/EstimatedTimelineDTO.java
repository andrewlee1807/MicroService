package com.banvien.fc.delivery.dto.grabexpress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedTimelineDTO  implements Serializable {
    private Timestamp pickup;
    private Timestamp dropoff;
}
