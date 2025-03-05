public class Pomodoro {

    private int pomodoroTime;
    private int breakTimeMinutes;
    private int breakTimeSeconds;
    private int minutes;
    private int seconds;
    private boolean paused;
    private int pomCompleted;

    public Pomodoro() {
        this.pomodoroTime = 25 * 60;
        this.breakTimeMinutes = 5;
        this.breakTimeSeconds = breakTimeMinutes * 60;
        this.minutes = 25;
        this.seconds = 0;
        this.paused = false;
        this.pomCompleted = 0;
    }

    public void playPom() {

    }

    public void pomIntervals() {
        System.out.println(getPomCompleted());
        if(getPomCompleted() == 4) {
            setBreakTimeMinutes(10);
            setBreakTimeSeconds(10 * 60);
            setPomCompleted(0);
            System.out.println(getPomCompleted());
        } else {
            setBreakTimeMinutes(5);
            setBreakTimeSeconds(5 * 60);
            setPomCompleted(getPomCompleted() + 1);
            System.out.println(getPomCompleted());
        }

    }

    public void setBreakTimeMinutes(int breakTimeMinutes) {
        this.breakTimeMinutes = breakTimeMinutes;
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


}
