package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFilter implements Serializable {

    public static final int UNUSED = -1;

    private final int day, month, year;
    private final boolean isDayOfWeek;

    public DateFilter(Builder builder) {
        this.day = builder.day;
        this.month = builder.month;
        this.year = builder.year;
        this.isDayOfWeek = builder.isDayOfWeek;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isDay() {
        return year == UNUSED && month == UNUSED && day != UNUSED;
    }

    public boolean isMonth() {
        return year == UNUSED && month != UNUSED && day == UNUSED;
    }

    public boolean isYear() {
        return year != UNUSED && month == UNUSED && day == UNUSED;
    }

    public boolean isMonthYear() {
        return year != UNUSED && month != UNUSED && day == UNUSED;
    }

    public boolean isMonthDay() {
        return year == UNUSED && month != UNUSED && day != UNUSED;
    }

    public boolean isYearDay() {
        return year != UNUSED && month == UNUSED && day != UNUSED;
    }

    public boolean isDayOfWeek() {
        return isDayOfWeek;
    }

    public boolean isUsable() {
        return year != UNUSED || month != UNUSED || day != UNUSED;
    }

    public boolean isExactDate() {
        return day != UNUSED && month != UNUSED && year != UNUSED;
    }

    public long getTimestamp() {
        if (isExactDate()) {
            return new GregorianCalendar(year, month -1, day).getTimeInMillis();
        }
        return UNUSED;
    }

    public long getStartTimestamp() {
        if (isExactDate()) return getTimestamp();
        if (year != UNUSED && month != UNUSED) {
            return new GregorianCalendar(year, month - 1, 1).getTimeInMillis();
        }
        if (year != UNUSED) {
            return new GregorianCalendar(year, 0, 1).getTimeInMillis();
        }
        return UNUSED;
    }

    public long getEndTimestamp() {
        if (isExactDate()) return getTimestamp();
        if (year != UNUSED && month != UNUSED) {
            int daysInMonth = new GregorianCalendar(year, month - 1, 1)
                    .getActualMaximum(Calendar.DAY_OF_MONTH);
            return new GregorianCalendar(year, month - 1, daysInMonth).getTimeInMillis();
        }
        if (year != UNUSED) {
            return new GregorianCalendar(year, 11, 31).getTimeInMillis();
        }
        return UNUSED;
    }

    // compare date filters based on duration
    public int compareTo(@NonNull DateFilter dateFilter) {
        long d1 = this.getEndTimestamp() - this.getStartTimestamp();
        long d2 = dateFilter.getEndTimestamp() - dateFilter.getStartTimestamp();
        if (d1 > d2) return 1;
        else if (d1 < d2) return -1;
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        // exact date
        if (isExactDate()) {
            return new Date(getTimestamp()).toString();
        }
        // year
        if (isYear()) {
            return String.valueOf(year);
        }
        // month
        if (isMonth()) {
            return GeneralUtil.getMonthString(month);
        }
        // month year
        if (isMonthYear()) {
            return GeneralUtil.getMonthString(month) + " " + year;
        }
        if (isDay()) {
            // day of week
            if (isDayOfWeek()) {
                return GeneralUtil.getDayOfWeekString(day) + "s";
            }
            // day of month
            else {
                return GeneralUtil.ordinal(day);
            }
        }
        if (isYearDay()) {
            // year day of week
            if (isDayOfWeek()) {
                return GeneralUtil.getDayOfWeekString(day) + "s of " + year;
            }
            // year day of month
            else {
                return "every " + GeneralUtil.ordinal(day) + " of " + year;
            }
        }
        if (isMonthDay()) {
            // month day of week
            if (isDayOfWeek()) {
                return GeneralUtil.getDayOfWeekString(day) + "s of "
                        + GeneralUtil.getMonthString(month);
            }
            // month day
            else {
                return GeneralUtil.getMonthString(month) + " " + GeneralUtil.ordinal(day);
            }
        }
        return super.toString();
    }

    public static class Builder {

        private int day, month, year;
        private boolean isDayOfWeek;

        public Builder() {
            day = UNUSED;
            month = UNUSED;
            year = UNUSED;
            isDayOfWeek = false;
        }

        public Builder day(int day) {
            this.day = day;
            return this;
        }

        public Builder month(int month) {
            this.month = month;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder dayOfWeek(boolean isDayOfWeek) {
            this.isDayOfWeek = isDayOfWeek;
            return this;
        }

        public DateFilter build() {
            DateFilter dateFilter = new DateFilter(this);
            validateDateFilter(dateFilter);
            return dateFilter;
        }

        public void validateDateFilter(DateFilter dateFilter) {
            if (!dateFilter.isUsable())
                throw new IllegalArgumentException("At least one date parameter has to be provided (year/month/day");
            if (dateFilter.getDay() != UNUSED) {
                if (dateFilter.isDayOfWeek() && (dateFilter.getDay() < 1 || dateFilter.getDay() > 7))
                    throw new IllegalArgumentException("Invalid day of week (1-7)");
                if (!dateFilter.isDayOfWeek() && (dateFilter.getDay() < 1 || dateFilter.getDay() > 31))
                    throw new IllegalArgumentException("Invalid day value (1-31)");
            }
            if (dateFilter.getMonth() != UNUSED && (dateFilter.getMonth() < 1 || dateFilter.getMonth() > 12))
                throw new IllegalArgumentException("Invalid month value (1-12)");
            if (dateFilter.getYear() != UNUSED && dateFilter.getYear() < 1970)
                throw new IllegalArgumentException("Invalid year value (1970+)");
            if (dateFilter.isExactDate()) {
                Calendar cal = Calendar.getInstance();
                cal.setLenient(false);
                cal.set(dateFilter.getYear(), dateFilter.getMonth() - 1, dateFilter.getDay());
                try {
                    cal.getTime();
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid date");
                }
            }
        }

    }

}
