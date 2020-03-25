package com.uwece651.medicationapp;

import android.content.ContentResolver;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.content.ContentValues;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class GoogleCalendarModule {

    private static Context context;
    private Calendar calendar;

    public GoogleCalendarModule(Context context, Calendar calendar) {
        this.context = context;
        this.calendar = calendar;
    }

    public static void addReminderInCalendar(PrescriptionData prescription, MedicationSchedule schedule) {
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = context.getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        int numberOfTimes;
        int timeBetweenDoses;

        try {
            numberOfTimes  = Integer.parseInt(schedule.getDailyFrequency());
        } catch (Exception e) {
            numberOfTimes = 2; //default value assigned
        }

        try {
            timeBetweenDoses = Integer.parseInt(schedule.getHoursFrequency());
        } catch (Exception e) {
            timeBetweenDoses = 8; //default value assigned
        }

        int[] doseTimes = getDoseTimes(numberOfTimes);
        List<Date> dates = getDatesBetween(prescription.getStartDate(), prescription.getEndDate());

        for (int i = 0; i < doseTimes.length; i++) {
            for (int j = 0; j < dates.size(); j++) {
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.CALENDAR_ID, 3);
                values.put(CalendarContract.Events.TITLE, "Take Medicine " + prescription.getMedicationName());
                values.put(CalendarContract.Events.ALL_DAY,0);
                values.put(CalendarContract.Events.DTSTART, timeInMillis(dates.get(j), doseTimes[i]));
                values.put(CalendarContract.Events.DTEND, timeInMillis(dates.get(j), doseTimes[i] + 1));
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                values.put(CalendarContract.Events.HAS_ALARM, 1);
                Uri event = cr.insert(EVENTS_URI, values);

                // Display event id
                Toast.makeText(context.getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

                /** Adding reminder for event added. */
                Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
                values = new ContentValues();
                values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
                values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                values.put(CalendarContract.Reminders.MINUTES, 10);
                cr.insert(REMINDERS_URI, values);
            }


        }


    }

    private static String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

    private static int[] getDoseTimes(int numberOfDoses) {
        int[] doseTimes = null;
        int time = 8;
        int increment = 12/numberOfDoses;

        for (int i = 0; i < numberOfDoses; i++) {
            doseTimes = appArrayHandling.add(doseTimes, time);
            time += increment;
        }

        return doseTimes;
    }

    private static long timeInMillis (Date date, int hour) {
        long time = date.getTime();
        long addition = (long) hour * 3600000;
        long ret = time + addition;
        return ret;
    }

    public static List<Date> getDatesBetween(
            Date startDate, Date endDate) {
        LocalDate start = convertToLocalDate(startDate);
        LocalDate end = convertToLocalDate(endDate);
        Date dateHolder;
        List<Date> dates = new ArrayList<>();

        while (!start.isAfter(end)) {
            dates.add(convertToDate(start));
            Log.d("date", start.toString());
            start = start.plusDays(1);
        }

        return dates;
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
