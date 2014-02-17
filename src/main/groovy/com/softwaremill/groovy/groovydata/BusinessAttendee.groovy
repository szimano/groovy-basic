package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical

@Canonical
public class BusinessAttendee extends Attendee {
    BigDecimal howMuchMoney;
}
