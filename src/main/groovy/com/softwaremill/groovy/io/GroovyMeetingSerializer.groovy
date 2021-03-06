package com.softwaremill.groovy.io

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.softwaremill.groovy.groovydata.Business
import com.softwaremill.groovy.groovydata.CrazyNight
import com.softwaremill.groovy.groovydata.Meeting
import com.softwaremill.groovy.groovydata.RomanticDate

class GroovyMeetingSerializer {
    private final String calendarFile;

    private Gson gson;

    public GroovyMeetingSerializer(String calendarFile) {
        this.calendarFile = calendarFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Meeting> readCalendar() {

        new File(calendarFile).withReader {
            JsonArray array = new JsonParser().parse(it).getAsJsonArray();

            List<Meeting> meetings = new ArrayList<>();

            for (JsonElement jsonElement : array) {
                switch (jsonElement.toString()) {
                    case ~/.*\"type\"\:\"BUSINESS\".*/:
                        meetings.add(gson.fromJson(jsonElement, Business.class))
                        break
                    case ~/.*\"type\"\:\"CRAZY\".*/:
                        meetings.add(gson.fromJson(jsonElement, CrazyNight.class))
                        break
                    case ~/.*\"type\"\:\"ROMANTIC\".*/:
                        meetings.add(gson.fromJson(jsonElement, RomanticDate.class));
                        break
                    default:
                        throw new RuntimeException("I don't understand ${jsonElement.toString()}")
                }
            }

            return meetings;
        }
    }

    public void writeCalendar(List<Meeting> meetings) {
        new File(calendarFile).withWriter {
            gson.toJson(meetings, it);
        }
    }
}
