package com.softwaremill.groovy

import com.softwaremill.groovy.groovydata.Business
import com.softwaremill.groovy.groovydata.BusinessAttendee
import com.softwaremill.groovy.groovydata.CrazyAttendee
import com.softwaremill.groovy.groovydata.CrazyNight
import com.softwaremill.groovy.groovydata.Meeting
import com.softwaremill.groovy.groovydata.RomanticAttendee
import com.softwaremill.groovy.groovydata.RomanticDate
import com.softwaremill.groovy.groovydata.Sex
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
                    } else if (attendee.getSnacks() == 0) {
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

        calendar.meetings.add(new Business(dateStart: new Date(), dateEnd: new Date(),
                attendeeList: Arrays.asList(new BusinessAttendee(name: "John", email: "john@doe.com", howMuchMoney: 1))));

        calendar.meetings.add(new RomanticDate(dateStart: new Date(), dateEnd: new Date(),
                attendeeList: Arrays.asList(new RomanticAttendee(name: "Jola", email: "jola@someemail.com",
                        sex: Sex.FEMALE, lookingFor: Sex.DOESNT_MATTER),
                        new RomanticAttendee(name: "Wiola", email: "wiola@someemail.com",
                                sex: Sex.FEMALE, lookingFor: Sex.MALE),
                        new RomanticAttendee(name: "Wojtek", email: "wojtek@someemail.com", sex: Sex.FEMALE,
                                lookingFor: Sex.DOESNT_MATTER))));

        calendar.meetings.add(new RomanticDate(dateStart: new Date(), dateEnd: new Date(),
                attendeeList: Arrays.asList(new RomanticAttendee(name: "Ta Jedyna", email: "tajedyna@someemail.com",
                        sex: Sex.FEMALE, lookingFor: Sex.MALE),
                        new RomanticAttendee(name: "Ten Jedyny", email: "tenjedyny@someemail.com",
                                sex: Sex.MALE, lookingFor: Sex.FEMALE))));

        calendar.meetings.add(new CrazyNight(dateStart: new Date(), dateEnd: new Date(),
                attendeeList: Arrays.asList(
                        new CrazyAttendee(name: "Waldek", email: "waldek@from", bottlesOfBeer: 7, bottlesOfVodka: 8, snacks: 2),
                        new CrazyAttendee(name: "Piotrek", email: "piotrek@from", bottlesOfBeer: 1, bottlesOfVodka: 2, snacks: 10),
                        new CrazyAttendee(name: "Mariolka", email: "mariolka@from", bottlesOfBeer: 2, bottlesOfVodka: 0, snacks: 0),
                        new CrazyAttendee(name: "Rysiek", email: "rysiek@from", bottlesOfBeer: 20, bottlesOfVodka: 40, snacks: 1))));

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