package com.dyejeekis.foldergenie.model;

public class DateFilter {

    public static final int UNUSED = -1;

    private final int day, month, year;

    public DateFilter(Builder builder) {
        this.day = builder.day;
        this.month = builder.month;
        this.year = builder.year;
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
        return month != UNUSED;
    }

    public boolean isYear() {
        return year != UNUSED;
    }

    public boolean isExactDate() {
        return day != UNUSED && month != UNUSED && year != UNUSED;
    }

    public boolean isDayOfWeek() {
        return day != UNUSED;
    }

    public boolean isValid() {
        return day != UNUSED || month != UNUSED || year != UNUSED;
    }

    public static class Builder {

        private int day, month, year;

        public Builder() {
            day = UNUSED;
            month = UNUSED;
            year = UNUSED;
        }

        public void day(int day) {
            this.day = day;
        }

        public void month(int month) {
            this.month = month;
        }

        public void year(int year) {
            this.year = year;
        }

        public DateFilter build() {
            DateFilter dateFilter = new DateFilter(this);
            validateDateFilter(dateFilter);
            return dateFilter;
        }

        public void validateDateFilter(DateFilter dateFilter) {
            // TODO: 6/6/2021
        }
    }
}
