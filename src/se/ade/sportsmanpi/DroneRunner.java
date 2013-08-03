package se.ade.sportsmanpi;


import java.io.File;
import java.io.IOException;

public class DroneRunner {
    public final static String SAVE_PATH = "/var/www/";
    public final static String MARKER_IMAGE = "marker";
    public final static String IMAGE_SUFFIX = ".jpg";

    private boolean testMode;
    private float totalTime;

    public DroneRunner(boolean testMode) {
        this.testMode = testMode;
    }

    public void run() {
        File marker = new File(SAVE_PATH + MARKER_IMAGE + IMAGE_SUFFIX);
        if(marker.exists()) {
            Log.d("Marker image exists: exiting");
        } else {
            picture(MARKER_IMAGE, false);

            script2();
            Log.d("Run ok, total time " + totalTime + "s");
        }
    }

    private void script1() {
        movie("mov1", 1000 * 60 * 2);
        sleep(5);
        for(int i = 0; i < 30; i++) {
            sleep(2);
            picture("pict" + i);
        }
        movie("mov2", 60000);

    }

    private void script2() {
        for(int i = 0; i < 180; i++) {
            sleep(1);
            picture("pict" + i);
        }
        shutdown();
    }

    private boolean picture(String filename) {
        return picture(filename, testMode);
    }
    private boolean picture(String filename, boolean testMode) {
        totalTime += 0.5f;

        String filePath = SAVE_PATH + filename + IMAGE_SUFFIX;
        Log.d("Capturing image: " + filePath);

        if(testMode) {
            return true;
        } else {
            return command("raspistill -o " + filePath + " -t 500");
        }
    }

    private boolean movie(String filename, int durationMs) {
        filename += ".h264";
        totalTime += ((float)durationMs / 1000.0f);
        Log.d("Capturing " + durationMs + "ms movie: " + filename);

        if(testMode) {
            return true;
        } else {
            return command("raspivid -o " + SAVE_PATH + filename + " -t " + durationMs);
        }
    }

    private boolean sleep(int durationSeconds) {
        Log.d("Delaying for " + durationSeconds + "seconds");
        totalTime += durationSeconds;

        if(testMode) {
            return true;
        } else {
            return command("sleep " + durationSeconds);
        }
    }

    private boolean command(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            return true;
        } catch (IOException | InterruptedException e) {
            Log.d("Exception running command: \"" + cmd + "\" (" + e.getMessage() + ")");
            return false;
        }
    }

    private boolean shutdown() {
        if(testMode) {
            Log.d("Shutdown triggered! (test mode - ignoring)");
            return true;
        } else {
            Log.d("Shutdown triggered! (test mode - ignoring)");
            return command("sudo shutdown -hP 0");
        }
    }
}
