package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical;

@Canonical
public abstract class Attendee {

    String name;

    String email;

    public void sendEmail(String msg) {
        System.out.println("Sending email to "+name+":\n "+msg);
    }
}
