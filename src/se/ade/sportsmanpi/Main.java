package se.ade.sportsmanpi;

public class Main {
    public static final String BASE_URL = "http://ade.se/projects/spi/";
    public static final String SELF_JAR = "SportsmanPi.jar";

    public static void main(String[] args) {
        Log.setup();
        Log.d("------- Starting ------");

        if(args != null && args.length > 0 && args[0].equals("update")) {
            new UpdateRunner().update();
        } else if(args != null && args.length > 0 && args[0].equals("test")) {
            new DroneRunner(true).run();
        } else {
            new DroneRunner(false).run();
        }
    }
}
