package com.uwece651.medicationapp;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.firebase.auth.FirebaseUser;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class GoogleCalendarServiceModule {

    private static final String APPLICATION_NAME = "ECE651MedicationAppv2";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = /*Environment.getExternalStorageDirectory() +
            File.separator +*/ "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static GoogleCredentialsUtilityModule googleCredentialsUtilityModule;
    private static final int STORAGE_PERMISSION_CODE = 101;

    public GoogleCalendarServiceModule(Context context, FirebaseUser user) {
        this.googleCredentialsUtilityModule = new GoogleCredentialsUtilityModule(context, user);
    }






    public static void addSchedule(MedicationSchedule medicationSchedule, PrescriptionData prescriptionData) throws IOException, GeneralSecurityException, ParseException {


        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dt.format(prescriptionData.getStartDate())+processTimeOfDay(medicationSchedule.getTimeOfDayCode());
        String endDate = dt.format(prescriptionData.getEndDate()) + processTimeOfDay(medicationSchedule.getTimeOfDayCode());
        String recurrence = medicationSchedule.getDailyFrequency(); //for now assume no hourly frequency set... just go by daily frequency


        TimeZone zone = TimeZone.getDefault();
        /*SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dt1.setTimeZone(zone);
        Date startDate1 = dt1.parse(startDate);
        Date endDate1 = dt1.parse(endDate);*/

        DateTime startDateTime = DateTime.parseRfc3339(startDate);// + processTimeOfDay(medicationSchedule.getTimeOfDayCode()));
        DateTime endDateTime = DateTime.parseRfc3339(endDate);// + processTimeOfDay(medicationSchedule.getTimeOfDayCode()));

        Log.d("GCSM", startDateTime.toString());



        // Build a new authorized API client service.

        final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();//GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleCredentialsUtilityModule.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        Event event = new Event()
                .setSummary("Take Drug: " + prescriptionData.getMedicationName());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(zone.getID());
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(zone.getID());
        event.setEnd(end);

        String[] recurrenceCalendar = new String[] {"RRULE:FREQ=DAILY;COUNT=" + recurrence}; //currently set for daily recurrence. Adjust afterwards to adjust for alternate days... HR
        event.setRecurrence(Arrays.asList(recurrenceCalendar));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(5)
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();


    }

    public static String processTimeOfDay(String timeOfDay) {

        if (timeOfDay == null) {
            return "T08:00:00";
        }

        switch (timeOfDay) {
            case "Morning":
                return "T08:00:00";
            case "Afternoon":
                return "T13:00:00";
            case "Evening":
                return "T17:00:00";
            case "Night":
                return "T20:00:00";
            case "Bedtime":
                return "T23:00:00";
            default:
                return "T08:00:00";

        }
    }





}
