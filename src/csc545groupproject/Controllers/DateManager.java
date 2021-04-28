/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Controllers;

/**
 *
 * @author Jadon
 */
public class DateManager {
    private static final String[] MONTHS = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };
    private static final int[] DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] WEEKDAYS = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    };
    
    public static int getNumDays(String month, int year) {
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equals(month)) {
                int numDays = DAYS[i];
                if (month.equals("February") && year % 4 == 0) {
                    numDays += 1;
                }
                return numDays;
            }
        }
        return -1;
    }
    
    public static String getWeekday(int day) {
        return WEEKDAYS[day];
    }
    
    public static String[] getMonths() {
        return MONTHS;
    }
    
    public static String getMonth(int month) {
        return MONTHS[month];
    }
}