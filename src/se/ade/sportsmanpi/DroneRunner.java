package se.ade.sportsmanpi;


import java.io.IOException;

public class DroneRunner {
    private boolean testMode;
    private float totalTime;

    public DroneRunner(boolean testMode) {
        this.testMode = testMode;
    }

    public void run() {
        movie("mov1", 1000 * 60 * 2);
        sleep(5);
        for(int i = 0; i < 30; i++) {
            sleep(2);
            picture("pict" + i);
        }
        movie("mov2", 60000);
        Log.d("Run ok, total time " + totalTime + "s");
    }

    private boolean picture(String filename) {
        filename += ".jpg";
        totalTime += 0.5f;

        Log.d("Capturing image: " + filename);
        if(testMode) {
            return true;
        } else {
            return command("raspistill -o /var/www/" + filename + " -t 500");
        }
    }

    private boolean movie(String filename, int durationMs) {
        filename += ".h264";
        totalTime += ((float)durationMs / 1000.0f);
        Log.d("Capturing " + durationMs + "ms movie: " + filename);

        if(testMode) {
            return true;
        } else {
            return command("raspivid -o /var/www/" + filename + " -t " + durationMs);
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
}
