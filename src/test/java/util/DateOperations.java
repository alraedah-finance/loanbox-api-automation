package util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateOperations {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static LocalDate getCurrentDate() {

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new Date());
        System.out.println("currentDate  --> " + date);
        LocalDate currentDate = LocalDate.parse(date,dtf);
        return currentDate;
    }

    public static LocalDate convertToSpecificDateFormat(String date, String currentFormat) throws ParseException {

        String formattedDate = null;
        if (currentFormat.equalsIgnoreCase("yyyy-MM-ddTHH:mm:ss.SSSZ")) {

            DateFormat to = new SimpleDateFormat("dd-MM-yyyy"); // wanted format
            DateFormat from = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ"); // current format
            formattedDate = to.format(from.parse(date));
            System.out.println("Formatted date --->"+formattedDate);

        } else if (currentFormat.equalsIgnoreCase("yyyy-MM-dd")) {
            DateFormat to = new SimpleDateFormat("dd-MM-yyyy"); // wanted format
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd"); // current format
            formattedDate = to.format(from.parse(date));
            System.out.println("Formatted date --->"+formattedDate);
        } else if (currentFormat.equalsIgnoreCase("yyyy-MM-dd'T'HH:mm:ss")) {
            DateFormat to = new SimpleDateFormat("dd-MM-yyyy"); // wanted format
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // current format
            formattedDate = to.format(from.parse(date));
            System.out.println("Formatted date --->"+formattedDate);
        }else if(currentFormat.equalsIgnoreCase("dd-MM-yyyy")){
            DateFormat to = new SimpleDateFormat("dd-MM-yyyy"); // wanted format
            DateFormat from = new SimpleDateFormat("dd-MM-yyyy"); // current format
            formattedDate = to.format(from.parse(date));
            System.out.println("Formatted date --->"+formattedDate);
        }
        else {
            System.out.println("Enter valid format date");
        }
        return LocalDate.parse(formattedDate,dtf);
    }

//    public static long dateDifference(LocalDate date1, LocalDate date2) {
//
//        Period period = Period.between(date1, date2);
//        long dateDiffInDays = period.getDays();
//        long dateDiffInMonths = period.getMonths();
//        long dateDiffInYears = period.getYears();
//        System.out.println("Difference between two dates in days" + dateDiffInDays);
////        System.out.println("Difference between two dates in months" + dateDiffInMonths);
////        System.out.println("Difference between two dates in years" + dateDiffInYears);
//
//        return dateDiffInDays;
//    }

    public static long dateDifference(LocalDate date1,LocalDate date2){

        long dateDiffInDays = ChronoUnit.DAYS.between(date2,date1);
        System.out.println("Date difference "+dateDiffInDays);
        return dateDiffInDays;
    }


}
