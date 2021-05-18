package Model;

public class CustomMonth {
    private int Year, Month, Days;
    private int WeekStart;

    public int getYear() {
        return Year;
    }

    public int getMonth() {
        return Month;
    }

    public int getDays() {
        return Days;
    }

    public int getWeekStart() {
        return WeekStart;
    }

    public void setWeekStart(int start) {
        WeekStart = start;
    }

    public CustomMonth(int year, int month, int weekStart) {
        Year = year;
        Month = month;
        WeekStart = weekStart;

        Regulate();

        CalcDetails();
    }

    private void Regulate() {
        if (Month > 12) {
            Month -= 12;
            Year++;
        } else if (Month <= 0) {
            Month += 12;
            Year--;
        }
    }

    private void CalcDetails() {
        if (Month <= 6) {
            Days = 31;
        } else if (Month <= 11) {
            Days = 30;
        } else {
            Days = (IsLeapYear()) ? 30 : 29;
        }
    }

    private boolean IsLeapYear() {
        return (Year % 33 == 1 || Year % 33 == 5 || Year % 33 == 9 || Year % 33 == 13 || Year % 33 == 17
                || Year % 33 == 22 || Year % 33 == 26 || Year % 33 == 30);
    }

    public String GetPersianForm() {
        switch (Month) {
            case 1:
                return "فروردین";
            case 2:
                return "اردیبهشت";
            case 3:
                return "خرداد";
            case 4:
                return "تیر";
            case 5:
                return "مرداد";
            case 6:
                return "شهریور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آذر";
            case 10:
                return "دی";
            case 11:
                return "بهمن";
            case 12:
                return "اسفند";
            default:
                return "";
        }
    }
}
