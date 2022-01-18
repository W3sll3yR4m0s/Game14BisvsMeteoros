package dev.medi.code.bis.config;

public class Runner {

	private static boolean isGamePlaying;
	private static boolean isGamePaused;

	static Runner runner = null;

	// Class Constructor
    private Runner() {

    }

    public static Runner check() {
        if (runner != null) {
            runner = new Runner();
        }

        return runner;
    }

    public static boolean isIsGamePlaying() {
        return isGamePlaying;
    }

    public static boolean isIsGamePaused() {
        return isGamePaused;
    }

    public static void setGamePlaying(boolean isGamePlaying) {
        Runner.isGamePlaying = isGamePlaying;
    }

    public static void setIsGamePaused(boolean isGamePaused) {
        Runner.isGamePaused = isGamePaused;
    }

}
