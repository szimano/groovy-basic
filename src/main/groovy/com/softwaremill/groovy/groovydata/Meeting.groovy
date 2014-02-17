package com.softwaremill.groovy.groovydata

import groovy.transform.Canonical

@Canonical
public abstract class Meeting<T extends Attendee> implements Comparable<Meeting> {

    Date dateStart;
    Date dateEnd;

    List<T> attendeeList;

    MeetingType type;

    public void changeTime(int newTimeInMinutes) {
        if (newTimeInMinutes <= 0) {
            throw new RuntimeException("Meeting length has to be greater then 0");
        }
        dateEnd = getEndDate(dateStart, newTimeInMinutes);
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

    private Date getEndDate(Date date, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.MINUTE, minutes);

        return c.getTime();
    }

    @Override
    public int compareTo(Meeting other) {
        return dateStart.compareTo(other.dateStart);
    }

}
