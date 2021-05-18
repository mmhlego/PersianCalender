package Model;

public class PersianCalender {
    private long Year, Month, Day, Hour, Minute, Second, Millisecond;

    public PersianCalender(long milliseconds) {
        this(0, 0, 0, 0, 0, 0, milliseconds);
    }

    public PersianCalender(long year, long month, long day) {
        this(year, month, day, 0, 0, 0, 0);
    }

    public PersianCalender(long year, long month, long day, long hour, long minute, long second) {
        this(year, month, day, hour, minute, second, 0);
    }

    public PersianCalender(long year, long month, long day, long hour, long minute, long second, long milliseconds) {
        Millisecond = milliseconds;

        Second = second + Millisecond / 1000;
        Millisecond %= 1000;

        Minute = minute + Second / 60;
        Second %= 60;

        Hour = hour + Minute / 60;
        Minute %= 60;

        Day = day + Hour / 24;
        Hour %= 24;

        Month = month + (Day - 1) / 31;
        Day = (Day - 1) % 31 + 1;

        Year = year + (Month - 1) / 12;
        Month = (Month - 1) % 12 + 1;

        checkExceptions();
    }

    private void checkExceptions() {
        if (Month > 6 && Month < 12 && Day == 31) {
            Day = 1;
            Month += 1;
        } else if (Month == 12 && Day > 29 && !IsLeapYear()) {
            Year++;
            Month = 1;
            Day = (Day - 1) % 29 + 1;
        } else if (Month == 12 && Day == 31 && IsLeapYear()) {
            Year++;
            Month = 1;
            Day = 1;
        }
    }

    public boolean IsLeapYear() {//95 99 03 08
        return (Year % 4 == 3);
    }
}
