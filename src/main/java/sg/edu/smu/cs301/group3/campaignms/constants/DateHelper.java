package sg.edu.smu.cs301.group3.campaignms.constants;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateHelper {

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static SimpleDateFormat spendDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }


}
