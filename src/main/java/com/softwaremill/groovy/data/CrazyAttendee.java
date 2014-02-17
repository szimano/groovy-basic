package com.softwaremill.groovy.data;

public class CrazyAttendee extends Attendee {

    private Integer bottlesOfBeer;

    private Integer bottlesOfVodka;

    private Integer snacks;

    public CrazyAttendee() {
    }

    public CrazyAttendee(String name, String email, Integer bottlesOfBeer, Integer bottlesOfVodka, Integer snacks) {
        super(name, email);
        this.bottlesOfBeer = bottlesOfBeer;
        this.bottlesOfVodka = bottlesOfVodka;
        this.snacks = snacks;
    }

    public Integer getBottlesOfBeer() {
        return bottlesOfBeer;
    }

    public void setBottlesOfBeer(Integer bottlesOfBeer) {
        this.bottlesOfBeer = bottlesOfBeer;
    }

    public Integer getBottlesOfVodka() {
        return bottlesOfVodka;
    }

    public void setBottlesOfVodka(Integer bottlesOfVodka) {
        this.bottlesOfVodka = bottlesOfVodka;
    }

    public Integer getSnacks() {
        return snacks;
    }

    public void setSnacks(Integer snacks) {
        this.snacks = snacks;
    }

    @Override
    public String toString() {
        return "CrazyAttendee{" +
                "bottlesOfBeer=" + bottlesOfBeer +
                ", bottlesOfVodka=" + bottlesOfVodka +
                ", snacks=" + snacks +
                '}';
    }
}
