package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical;

@Canonical
public class RomanticAttendee extends Attendee {

    Sex sex;

    Sex lookingFor;
}
