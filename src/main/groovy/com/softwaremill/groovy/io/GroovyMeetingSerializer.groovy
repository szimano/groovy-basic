package com.softwaremill.groovy.io

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.softwaremill.groovy.data.Business
import com.softwaremill.groovy.data.CrazyNight
import com.softwaremill.groovy.data.Meeting
import com.softwaremill.groovy.data.RomanticDate

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
                String str = jsonElement.toString();

                if (str.contains("\"type\":\"BUSINESS\"")) {
                    meetings.add(gson.fromJson(jsonElement, Business.class));
                }
                else if (str.contains("\"type\":\"CRAZY\"")) {
                    meetings.add(gson.fromJson(jsonElement, CrazyNight.class));
                }
                else if (str.contains("\"type\":\"ROMANTIC\"")) {
                    meetings.add(gson.fromJson(jsonElement, RomanticDate.class));
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
