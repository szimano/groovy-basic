package com.softwaremill.groovy.data;

import java.math.BigDecimal;

public class BusinessAttendee extends Attendee {

    private BigDecimal howMuchMoney;

    public BusinessAttendee() {
    }

    public BusinessAttendee(String name, String email, BigDecimal howMuchMoney) {
        super(name, email);
        this.howMuchMoney = howMuchMoney;
    }

    public BigDecimal getHowMuchMoney() {
        return howMuchMoney;
    }

    public void setHowMuchMoney(BigDecimal howMuchMoney) {
        this.howMuchMoney = howMuchMoney;
    }

    @Override
    public String toString() {
        return "BusinessAttendee{" +
                "howMuchMoney=" + howMuchMoney +
                '}';
    }
}
