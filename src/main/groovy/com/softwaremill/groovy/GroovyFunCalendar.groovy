package com.softwaremill.groovy
import com.softwaremill.groovy.groovydata.Business
import com.softwaremill.groovy.groovydata.BusinessAttendee
import com.softwaremill.groovy.groovydata.CrazyAttendee
import com.softwaremill.groovy.groovydata.CrazyNight
import com.softwaremill.groovy.groovydata.Meeting
import com.softwaremill.groovy.groovydata.MeetingType
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
        meetings.findAll {meetings.dateStart > new Date()}.each {println "\t$it"}
    }

    public void removeInmoralDates() {

        def isInmoral = {
            if (size() > 2) return true

            def firstSex = get(0).sex
            def secondSex = get(1).sex
            return (Sex.DOESNT_MATTER in [firstSex, secondSex] || firstSex == secondSex)
        }

        meetings.removeAll {it.type == MeetingType.ROMANTIC && it.attendeeList.with(isInmoral)}
    }

    public void removeBadGuests() {
        def crazyNights = meetings.findAll {it.type == MeetingType.CRAZY};

        crazyNights.each {
            def badAttendees = it.attendeeList.findAll {it.getBottlesOfBeer() + it.getBottlesOfVodka() < 10 || it.getSnacks() == 0}

            badAttendees.each {it.sendEmail("Bring booze and snacks next time!")}

            it.attendeeList -= badAttendees;
        }
    }


    public void removeCheapBusinessMeetings(BigDecimal enoughMoney$$$) {
        meetings.removeAll {
            if (it.type == MeetingType.BUSINESS && it.attendeeList.howMuchMoney.sum() < enoughMoney$$$) {
                it.attendeeList.each {
                    it.sendEmail("I am sorry, but Mr XYZ has to help someone cross the street.")
                }
                return true
            }
            return false
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