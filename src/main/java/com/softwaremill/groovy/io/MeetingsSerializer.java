package com.softwaremill.groovy.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.softwaremill.groovy.data.Business;
import com.softwaremill.groovy.data.CrazyNight;
import com.softwaremill.groovy.data.Meeting;
import com.softwaremill.groovy.data.RomanticDate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MeetingsSerializer {

    private final String calendarFile;

    private Gson gson;

    public MeetingsSerializer(String calendarFile) {
        this.calendarFile = calendarFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Meeting> readCalendar() {

        try (Reader reader = new FileReader(calendarFile)) {
            JsonArray array = new JsonParser().parse(reader).getAsJsonArray();

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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeCalendar(List<Meeting> meetings) {
        try (Writer writer = new FileWriter(calendarFile)) {
            gson.toJson(meetings, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
