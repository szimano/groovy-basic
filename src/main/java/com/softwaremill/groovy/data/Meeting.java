package com.softwaremill.groovy.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Meeting<T extends Attendee> implements Comparable<Meeting> {

    private Date dateStart;
    private Date dateEnd;

    private List<T> attendeeList;

    private MeetingType type;

    public Meeting() {
    }

    public Meeting(Date dateStart, Date dateEnd, MeetingType type) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.type = type;

        this.attendeeList = new ArrayList<>();
    }

    public Meeting(Date dateStart, Date dateEnd, MeetingType type, List<T> attendeeList) {
        this(dateStart, dateEnd, type);

        this.attendeeList = attendeeList;
    }

    public void moveByDays(int howMany) {
        dateStart = moveDateByDays(dateStart, howMany);
        dateEnd = moveDateByDays(dateEnd, howMany);
    }

    private Date moveDateByDays(Date date, int howMany) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DAY_OF_MONTH, howMany);

        return c.getTime();
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<? extends Attendee> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(List<T> attendeeList) {
        this.attendeeList = attendeeList;
    }

    @Override
    public int compareTo(Meeting other) {
        return dateStart.compareTo(other.dateStart);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", attendeeList=" + attendeeList +
                '}';
    }
}
