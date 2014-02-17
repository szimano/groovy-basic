package com.softwaremill.groovy.data;

import java.util.Date;
import java.util.List;

public class CrazyNight extends Meeting<CrazyAttendee> {
    public CrazyNight() {
    }

    public CrazyNight(Date dateStart, Date dateEnd, List<CrazyAttendee> attendees) {
        super(dateStart, dateEnd, MeetingType.CRAZY, attendees);
    }
}
