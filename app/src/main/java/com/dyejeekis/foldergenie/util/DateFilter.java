package com.dyejeekis.foldergenie.util;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.DayOfWeek;

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

    public boolean isMonth() {
        return month != UNUSED && year == UNUSED && day == UNUSED;
    }

    public boolean isYear() {
        return year != UNUSED && month == UNUSED && day == UNUSED;
    }

    public boolean isDayOfWeek() {
        return isDayOfWeek;
    }

    public boolean isValid() {
        return day != UNUSED || month != UNUSED || year != UNUSED;
    }

    public boolean isExactDate() {
        return day != UNUSED && month != UNUSED && year != UNUSED;
    }

    public long getTimestamp() {
        // TODO: 8/17/2021
        return -1;
    }

    public int compareTo(@NonNull DateFilter dateFilter) {
        // TODO: 8/14/2021
        return 0;
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
            if (dateFilter.isDayOfWeek() && (dateFilter.getDay() < 1 || dateFilter.getDay() > 7))
                throw new IllegalArgumentException("Invalid day of week");
        }
    }
}
