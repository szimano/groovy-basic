package com.softwaremill.groovy.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Meeting {

    private Date dateStart;
    private Date dateEnd;

    private List<Attendee> attendeeList;

    public Meeting(Date dateStart, Date dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;

        this.attendeeList = new ArrayList<>();
    }

    public Meeting(Date dateStart, Date dateEnd, List<Attendee> attendeeList) {
        this(dateStart, dateEnd);

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

    public List<Attendee> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(List<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
    }
}
