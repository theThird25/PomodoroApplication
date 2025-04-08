package entity;

public class Pomodoro {

    private int pomodoroTime; // in seconds
    private int breakTimeMinutes; // in minutes
    private int breakTimeSeconds; // in seconds
    private int minutes;
    private int seconds;
    private boolean paused;
    private int pomCompleted;

    // Constructor to initialize Pomodoro with a custom time and break time
    public Pomodoro(int pomodoroTimeInSeconds, int breakTimeMinutes) {
        this.pomodoroTime = pomodoroTimeInSeconds;
        this.breakTimeMinutes = breakTimeMinutes;
        this.breakTimeSeconds = breakTimeMinutes * 60;
        this.minutes = pomodoroTimeInSeconds / 60;
        this.seconds = pomodoroTimeInSeconds % 60;
        this.paused = false;
        this.pomCompleted = 0;
    }

    // Default constructor for a 25-minute Pomodoro and 5-minute break
    public Pomodoro() {
        this(25 * 60, 5);  // default to 25 minutes for Pomodoro and 5 minutes for breaks
    }

    public Pomodoro(int initialTime) {
    }

    // Getters and Setters
    public int getPomodoroTime() {
        return pomodoroTime;
    }

    public void setPomodoroTime(int pomodoroTime) {
        this.pomodoroTime = pomodoroTime;
    }

    public int getBreakTimeMinutes() {
        return breakTimeMinutes;
    }

    public void setBreakTimeMinutes(int breakTimeMinutes) {
        this.breakTimeMinutes = breakTimeMinutes;
        this.breakTimeSeconds = breakTimeMinutes * 60;
    }

    public int getBreakTimeSeconds() {
        return breakTimeSeconds;
    }

    public void setBreakTimeSeconds(int breakTimeSeconds) {
        this.breakTimeSeconds = breakTimeSeconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getPomCompleted() {
        return pomCompleted;
    }

    public void setPomCompleted(int pomCompleted) {
        this.pomCompleted = pomCompleted;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // Method to manage break intervals after completing each Pomodoro cycle
    public void pomIntervals() {
        System.out.println("Pomodoro count: " + getPomCompleted());

        if (getPomCompleted() == 4) {
            // After 4 Pomodoros, increase break to 10 minutes
            setBreakTimeMinutes(10);
            setBreakTimeSeconds(10 * 60);
            setPomCompleted(0);  // Reset Pomodoro count after every 4 cycles
            System.out.println("Long break after 4 Pomodoros. Break time: " + breakTimeMinutes + " minutes.");
        } else {
            // Normal break time is 5 minutes after each Pomodoro
            setBreakTimeMinutes(5);
            setBreakTimeSeconds(5 * 60);
            setPomCompleted(getPomCompleted() + 1);  // Increment Pomodoro count
            System.out.println("Short break. Break time: " + breakTimeMinutes + " minutes.");
        }
    }

    // Utility methods for countdown display
    public String getFormattedTime() {
        return String.format("%02d:%02d", minutes, seconds);
    }
}   
