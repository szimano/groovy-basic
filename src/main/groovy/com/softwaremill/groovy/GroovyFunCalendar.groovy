package com.softwaremill.groovy

import com.softwaremill.groovy.data.Business
import com.softwaremill.groovy.data.BusinessAttendee
import com.softwaremill.groovy.data.CrazyAttendee
import com.softwaremill.groovy.data.CrazyNight
import com.softwaremill.groovy.data.Meeting
import com.softwaremill.groovy.data.RomanticAttendee
import com.softwaremill.groovy.data.RomanticDate
import com.softwaremill.groovy.data.Sex
import com.softwaremill.groovy.io.GroovyMeetingSerializer

class GroovyFunCalendar {

    List<Meeting> meetings = new ArrayList<>();

    public GroovyFunCalendar() {

    }

    public void readMeetings(String filePath) {
        meetings = new GroovyMeetingSerializer(filePath).readCalendar();

        Collections.sort(meetings);
    }

    public void writeMeetings(String filePath) {
        new GroovyMeetingSerializer(filePath).writeCalendar(meetings);
    }

    public void showUpcomingMeetings() {
        System.out.println("Upcoming meetings:");
        for (Meeting meeting : meetings) {
            if (meeting.getDateStart().compareTo(new Date()) > 0) {
                System.out.println("\t" + meeting);
            }
        }
    }

    public void removeInmoralDates() {
        Iterator<Meeting> it = meetings.iterator();

        while (it.hasNext()) {
            Meeting m = it.next();

            if (m instanceof RomanticDate) {
                RomanticDate date = (RomanticDate) m;

                if (date.getAttendeeList().size() > 2) {
                    it.remove();
                } else {
                    Sex firstSex = date.getAttendeeList().get(0).getSex();
                    Sex secondSex = date.getAttendeeList().get(1).getSex();
                    if (firstSex == secondSex || firstSex == Sex.DOESNT_MATTER || secondSex == Sex.DOESNT_MATTER) {
                        it.remove();
                    }
                }
            }
        }
    }

    public void removeBadGuests() {
        for (Meeting meeting : meetings) {
            if (meeting instanceof CrazyNight) {
                CrazyNight party = (CrazyNight) meeting;

                Iterator<CrazyAttendee> it = party.getAttendeeList().iterator();

                while (it.hasNext()) {
                    CrazyAttendee attendee = it.next();

                    if (attendee.getBottlesOfBeer() + attendee.getBottlesOfVodka() < 10) {
                        attendee.sendEmail("Sorry man. Not enough booze");

                        it.remove();
                    }
                    else if (attendee.getSnacks() == 0) {
                        attendee.sendEmail("Sorry man. Next time bring something to eat");

                        it.remove();
                    }
                }
            }
        }
    }


    public void removeCheapBusinessMeetings(BigDecimal enoughMoney$$$) {
        Iterator<Meeting> it = meetings.iterator();

        while (it.hasNext()) {
            Meeting m = it.next();

            if (m instanceof Business) {
                Business businessMeeting = (Business) m;

                BigDecimal totalMoney$$$ = BigDecimal.ZERO;

                for (BusinessAttendee attendee : businessMeeting.getAttendeeList()) {
                    totalMoney$$$ = totalMoney$$$.add(attendee.getHowMuchMoney());
                }

                if (totalMoney$$$.compareTo(enoughMoney$$$) < 0) {
                    for (BusinessAttendee attendee : businessMeeting.getAttendeeList()) {
                        attendee.sendEmail("I am sorry, but Mr XYZ has to help someone cross the street.");
                    }

                    it.remove();
                }
            }
        }
    }


    // ==========================

    @Override
    public String toString() {
        return "GroovyFunCalendar{" +
                "meetings=" + meetings +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

        GroovyFunCalendar calendar = new GroovyFunCalendar();

        calendar.meetings.add(new Business(new Date(), new Date(),
                Arrays.asList(new BusinessAttendee("John", "john@doe.com", BigDecimal.ONE))));

        calendar.meetings.add(new RomanticDate(new Date(), new Date(),
                Arrays.asList(new RomanticAttendee("Jola", "jola@someemail.com", Sex.FEMALE, Sex.DOESNT_MATTER),
                        new RomanticAttendee("Wiola", "wiola@someemail.com", Sex.FEMALE, Sex.MALE),
                        new RomanticAttendee("Wojtek", "wojtek@someemail.com", Sex.FEMALE, Sex.DOESNT_MATTER))));

        calendar.meetings.add(new RomanticDate(new Date(), new Date(),
                Arrays.asList(new RomanticAttendee("Ta Jedyna", "tajedyna@someemail.com", Sex.FEMALE, Sex.MALE),
                        new RomanticAttendee("Ten Jedyny", "tenjedyny@someemail.com", Sex.MALE, Sex.FEMALE))));

        calendar.meetings.add(new CrazyNight(new Date(), new Date(),
                Arrays.asList(new CrazyAttendee("Waldek", "waldek@from", 7, 8, 2),
                        new CrazyAttendee("Piotrek", "piotrek@from", 1, 2, 10),
                        new CrazyAttendee("Mariolka", "mariolka@from", 2, 0, 0),
                        new CrazyAttendee("Rysiek", "rysiek@from", 20, 40, 1))));

        calendar.writeMeetings("src/main/resources/calendar.json");

        calendar = new GroovyFunCalendar();

        calendar.readMeetings("src/main/resources/calendar.json");

        calendar.removeInmoralDates();

        calendar.writeMeetings("src/main/resources/calendar-moral.json");

        calendar.removeBadGuests();

        calendar.writeMeetings("src/main/resources/calendar-good-guests.json");

        calendar.removeCheapBusinessMeetings(1000000000000000000);

        calendar.writeMeetings("src/main/resources/calendar-expensive-business-stuff.json");
    }

}