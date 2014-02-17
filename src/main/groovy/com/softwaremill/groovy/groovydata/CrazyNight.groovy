package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical

@Canonical
public class CrazyNight extends Meeting<CrazyAttendee> {
    CrazyNight() {
       type = MeetingType.CRAZY
    }
}
