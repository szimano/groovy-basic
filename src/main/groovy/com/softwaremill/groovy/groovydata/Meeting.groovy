package com.softwaremill.groovy.groovydata

import groovy.time.TimeCategory
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

        use(TimeCategory) {
            dateEnd = dateStart + newTimeInMinutes.minutes
        }
    }

    public void moveByDays(int howMany) {
        use(TimeCategory) {
            dateStart += howMany.days
            dateEnd += howMany.days
        }
    }

    @Override
    public int compareTo(Meeting other) {
        return dateStart.compareTo(other.dateStart);
    }

}
