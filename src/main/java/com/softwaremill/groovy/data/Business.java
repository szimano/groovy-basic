package com.softwaremill.groovy.data;

import java.util.Date;
import java.util.List;

public class Business extends Meeting<BusinessAttendee> {
    public Business() {
    }

    public Business(Date dateStart, Date dateEnd, List<BusinessAttendee> attendeeList) {
        super(dateStart, dateEnd, MeetingType.BUSINESS, attendeeList);
    }
}
