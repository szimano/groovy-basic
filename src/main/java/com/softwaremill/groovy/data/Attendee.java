package com.softwaremill.groovy.data;

public abstract class Attendee {

    private String name;

    private String email;

    public Attendee() {
    }

    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void sendEmail(String msg) {
        System.out.println("Sending email to "+name+":\n "+msg);
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
