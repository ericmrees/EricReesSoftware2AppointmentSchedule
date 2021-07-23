package Utility;

import DataAccessObject.AppointmentDAO;
import Model.Appointment;

import java.time.*;
import java.util.TimeZone;

/**
 * Class used to convert LocalDateTime to a ZoneId and to Eastern Standard Time start and end business hours.
 */
public class TimeConversion {
    static LocalDate localDate = LocalDate.now();
    static ZoneId easternStandardTimeId = ZoneId.of("America/New_York");
    static ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

    /**
     * Method sets up the Eastern Standard Time business start time and converts it to LocalTime.
     * @return businessStartLocalTime which is business start time in LocalTime
     */
    public static LocalDateTime getLocalStartTime() {
        LocalTime startTime = LocalTime.of(8, 0);
        ZonedDateTime businessStartEasternTime = ZonedDateTime.of(localDate, startTime, easternStandardTimeId);
        ZonedDateTime businessStartLocalTime = businessStartEasternTime.withZoneSameInstant(localZoneId);
        return businessStartLocalTime.toLocalDateTime();
    }

    /**
     * Method sets up the Eastern Standard Time business end time and converts it to LocalTime.
     * End time is set to 9:30PM EST so the latest appointment ends at 10PM EST,
     * which is the end of business hours due to the 30 minute appointment length.
     * @return businessEndLocalTime which is business end time in LocalTime
     */
    public static LocalDateTime getLocalEndTime() {
        LocalTime endTime = LocalTime.of(21, 30);
        ZonedDateTime businessEndEasternTime = ZonedDateTime.of(localDate, endTime, easternStandardTimeId);
        ZonedDateTime businessEndLocalTime = businessEndEasternTime.withZoneSameInstant(localZoneId);
        return businessEndLocalTime.toLocalDateTime();
    }
}