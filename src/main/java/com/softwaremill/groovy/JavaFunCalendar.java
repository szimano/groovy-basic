package com.softwaremill.groovy;

import com.softwaremill.groovy.data.Business;
import com.softwaremill.groovy.data.BusinessAttendee;
import com.softwaremill.groovy.data.CrazyAttendee;
import com.softwaremill.groovy.data.CrazyNight;
import com.softwaremill.groovy.data.Meeting;
import com.softwaremill.groovy.data.RomanticAttendee;
import com.softwaremill.groovy.data.RomanticDate;
import com.softwaremill.groovy.data.Sex;
import com.softwaremill.groovy.io.MeetingsSerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JavaFunCalendar {

    private List<Meeting> meetings = new ArrayList<>();

    public JavaFunCalendar() {

    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public void readMeetings(String filePath) {
        meetings = new MeetingsSerializer(filePath).readCalendar();

        Collections.sort(meetings);
    }

    public void writeMeetings(String filePath) {
        new MeetingsSerializer(filePath).writeCalendar(meetings);
    }

    public void showUpcomingMeetings() {
        System.out.println("Upcoming meetings:");
        for (Meeting meeting : meetings) {
            if (meeting.getDateStart().compareTo(new Date()) > 0) {
                System.out.println("\t" + meeting);
            }
        }
    }

    @Override
    public String toString() {
        return "JavaFunCalendar{" +
                "meetings=" + meetings +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

        JavaFunCalendar calendar = new JavaFunCalendar();

        calendar.getMeetings().add(new Business(new Date(), new Date(),
                Arrays.asList(new BusinessAttendee("John", "john@doe.com", BigDecimal.ONE))));

        calendar.getMeetings().add(new RomanticDate(new Date(), new Date(),
                Arrays.asList(new RomanticAttendee("Jola", "jola@someemail.com", Sex.FEMALE, Sex.DOESNT_MATTER),
                        new RomanticAttendee("Wiola", "wiola@someemail.com", Sex.FEMALE, Sex.MALE),
                        new RomanticAttendee("Wojtek", "wojtek@someemail.com", Sex.FEMALE, Sex.DOESNT_MATTER))));

        calendar.getMeetings().add(new CrazyNight(new Date(), new Date(),
                Arrays.asList(new CrazyAttendee("Waldek", "waldek@from", 7, 8, 2),
                        new CrazyAttendee("Piotrek", "piotrek@from", 1, 2, 10),
                        new CrazyAttendee("Mariolka", "mariolka@from", 2, 0, 0),
                        new CrazyAttendee("Rysiek", "rysiek@from", 20, 40, 0))));

        calendar.writeMeetings("src/main/resources/calendar.json");

        calendar = new JavaFunCalendar();

        calendar.readMeetings("src/main/resources/calendar.json");

        System.out.println(calendar);
    }
}
