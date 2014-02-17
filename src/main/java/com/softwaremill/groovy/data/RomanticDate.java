package com.softwaremill.groovy.data;

import java.util.Date;

public class RomanticDate extends Meeting {

    public RomanticDate(Date dateStart, Date dateEnd) {
        super(dateStart, dateEnd);
    }

    public boolean isMoral() {
        return getAttendeeList().size() <= 2;
    }

    public boolean isForeverAlone() {
        return getAttendeeList().size() == 1;
    }

    public boolean isNotReallyADate() {
        return getAttendeeList().size() == 0;
    }

    public boolean isFun() {
        return getAttendeeList().size() > 2;
    }

    public boolean validDate() {
        for (Attendee attendee : getAttendeeList()) {
            if (!(attendee instanceof RomanticAttendee)) {
                return false;
            }
        }

        return true;
    }
}
