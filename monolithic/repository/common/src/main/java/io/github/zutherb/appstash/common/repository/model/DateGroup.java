package io.github.zutherb.appstash.common.repository.model;

import org.apache.commons.lang3.NotImplementedException;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zutherb
 */
public class DateGroup implements Serializable {

    private Integer day;
    private Integer month;
    private Integer year;

    public DateGroup() {/* MONGO-DB */}

    public DateGroup(Integer year) {
        this.year = year;
    }

    public DateGroup(Integer month, Integer year) {
        this.month = month;
        this.year = year;
    }

    public DateGroup(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date getDate() {

        if (day != null){
            return new DateTime(year, month, day, 0 , 0)
                    .toDate();
        } else if (month != null){
            return new DateTime(year, month, 1, 0, 0)
                    .toDate();
        } else if (year != null){
            return new DateTime(year, 1, 1, 0, 0)
                    .toDate();
        } else{
            return null;
        }
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }


    public Integer getDay() {
        return day;
    }

    public Choice getChoice() {
        if (day != null) return Choice.DAY;
        if (month != null) return Choice.MONTH;
        if (year != null) return Choice.YEAR;
        throw new IllegalStateException("year must be set in a dategroup");
    }

    public static enum Choice {
        DAY("day"),
        MONTH("month"),
        YEAR("year");

        private String name;

        Choice(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateGroup dateGroup = (DateGroup) o;

        if (day != null ? !day.equals(dateGroup.day) : dateGroup.day != null) return false;
        if (month != null ? !month.equals(dateGroup.month) : dateGroup.month != null) return false;
        if (!year.equals(dateGroup.year)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + year.hashCode();
        return result;
    }


    public static DateGroup create(Date date, Choice choice) {
        DateTime dateTime = new DateTime(date);
        switch (choice) {
            case DAY:
                return new DateGroup(dateTime.getDayOfMonth(), dateTime.getMonthOfYear(), dateTime.getYear());
            case MONTH:
                return new DateGroup(dateTime.getMonthOfYear(), dateTime.getYear());
            case YEAR:
                return new DateGroup(dateTime.getYear());
            default:
                throw new NotImplementedException(String.format("No implemetation for this choice: %s", choice));
        }
    }

    public static boolean isCacheable(DateGroup dateGroup) {
        if (dateGroup == null || dateGroup.getDate() == null) {
            return false;
        }
        DateTime now = nowWithGranularity(dateGroup.getChoice());
        return now.isAfter(dateGroup.getDate().getTime());
    }

    private static DateTime nowWithGranularity(Choice choice) {
        DateTime now = DateTime.now().withTimeAtStartOfDay();
        switch (choice) {
            case YEAR:
                return now.withDayOfMonth(1).withMonthOfYear(1);
            case MONTH:
                return now.withDayOfMonth(1);
            case DAY: // fall through
            default:
                return now;
        }
    }
}
