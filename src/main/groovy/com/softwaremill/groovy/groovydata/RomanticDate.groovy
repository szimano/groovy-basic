package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical

@Canonical
public class RomanticDate extends Meeting<RomanticAttendee> {

    RomanticDate() {
        type = MeetingType.ROMANTIC
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
}
