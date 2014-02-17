package com.softwaremill.groovy;

import com.softwaremill.groovy.data.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JavaFunCalendar {

    private List<Meeting> meetings = new ArrayList<>();

    public JavaFunCalendar() {

    }

    public void readMeetings(String filePath) {
        meetings = new ArrayList<>();

        // todo read meetings

        Collections.sort(meetings);
    }

    public void showUpcomingMeetings() {
        System.out.println("Upcoming meetings:");
        for (Meeting meeting : meetings) {
            if (meeting.getDateStart().compareTo(new Date()) > 0) {
                System.out.println("\t"+meeting);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
