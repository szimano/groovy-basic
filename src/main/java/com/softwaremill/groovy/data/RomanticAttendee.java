package com.softwaremill.groovy.data;

public class RomanticAttendee extends Attendee {

    private Sex sex;

    private Sex lookingFor;

    public RomanticAttendee(String name, String email, Sex sex, Sex lookingFor) {
        super(name, email);
        this.sex = sex;
        this.lookingFor = lookingFor;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Sex getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(Sex lookingFor) {
        this.lookingFor = lookingFor;
    }
}
