package com.uwece651.medicationapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class GoogleCredentialsUtilityModule {

    private static final String APPLICATION_NAME = "MedicationReminder";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = /*Environment.getExternalStorageDirectory() +
            File.separator +*/ "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    private static FirebaseUser user;
    private static Context context;

    public GoogleCredentialsUtilityModule(Context context, FirebaseUser user) {
        this.context = context;
        this.user = user;
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */

    static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleCalendarServiceModule.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));


        createTokenFolderIfMissing();

        GoogleAuthorizationCodeFlow authorisationFlow = getAuthorisationFlow(HTTP_TRANSPORT, clientSecrets);
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        AuthorizationCodeInstalledApp ab = new AuthorizationCodeInstalledApp(authorisationFlow, receiver) {
            protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) throws IOException {
                String url = (authorizationUrl.build());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }};
        Credential credential = ab.authorize(user.getEmail());
        return credential;//new AuthorizationCodeInstalledApp(authorisationFlow, receiver).authorize(user.getEmail());
    }

    public static void createTokenFolderIfMissing() {
        File tokenFolder = getTokenFolder();
        if (!tokenFolder.exists()) {
            tokenFolder.mkdir();
        }
    }

    public static File getTokenFolder() {
        String pathname = context.getExternalFilesDir("").getAbsolutePath()+TOKENS_DIRECTORY_PATH;
        File tokenFolder = new File(pathname);
        return tokenFolder;
    }

    public static GoogleAuthorizationCodeFlow getAuthorisationFlow(NetHttpTransport HTTP_TRANSPORT, GoogleClientSecrets clientSecrets) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(getTokenFolder()))
                .setAccessType("offline")
                .build();

        return flow;
    }

}
