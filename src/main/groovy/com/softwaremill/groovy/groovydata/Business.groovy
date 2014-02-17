package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical

@Canonical
public class Business extends Meeting<BusinessAttendee> {
    Business() {
        type = MeetingType.BUSINESS
    }
}
