package se.ade.sportsmanpi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;

import java.io.*;
import java.util.Date;

public class UpdateRunner {
    public void update() {
        Log.d("Running update...");
        HttpClient httpClient = new DefaultHttpClient();

        File versionFile = new File("version.dat");

        HttpHead head = new HttpHead(Main.BASE_URL + Main.SELF_JAR);
        HttpResponse headResponse = null;
        String lastModified = null;
        boolean update = true;
        try {
            headResponse = httpClient.execute(head);

            Header[] headers = headResponse.getHeaders("last-modified");
            if(headers != null && headers.length > 0) {
                //Got last modif date
                lastModified = headers[0].getValue();
                Log.d("Last modified: " + lastModified);
                Date serverDate = DateUtils.parseDate(lastModified);

                if(versionFile.exists()) {
                    String previousLastModified = FileUtils.readFileToString(versionFile);
                    Date oldDate = DateUtils.parseDate(previousLastModified);
                    if(oldDate.getTime() == serverDate.getTime()) {
                        Log.d("Current version is up to date");
                        update = false;
                    } else {
                        Log.d("Current version: " + oldDate.getTime() + ", server version: " + serverDate.getTime());
                    }
                }
            }
        } catch (Exception e) {
            Log.d("Error occured getting version info: " + e.getMessage());
        }

        if(update) {
            Log.d("Downloading update...");
            HttpUriRequest request = new HttpGet(Main.BASE_URL + Main.SELF_JAR);
            HttpResponse response = null;
            try {
                response = httpClient.execute(request);
            } catch (Exception e) {
                Log.d("Error occured");
            }

            if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Log.d("Download ok, size: " + response.getEntity().getContentLength());
            } else {
                Log.d("Download failed.");
            }

            try {
                System.out.println("Saving new JAR.");
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("update.jar.tmp")));
                IOUtils.copy(in, out);
                out.flush();
                out.close();

                if(lastModified != null) {
                    Log.d("Saving version file.");
                    FileUtils.writeStringToFile(versionFile, lastModified);
                }
                Log.d("Save OK.");
            } catch (Exception e) {
                Log.d("Save failed.");
            }
        }
    }

}
