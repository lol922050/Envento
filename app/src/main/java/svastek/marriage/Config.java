package svastek.marriage;

/**
 * Created by Tatson on 08-01-2016.
 */
public class Config {
    //URL of my API
    public static final String DATA_URL = "http://svastek.in/sevento/php/sub_cat.php";

    //Tags for my JSON
    public static final String TAG_IMAGE_URL_work = "pro_url";
    public static final String TAG_NAME = "name";
    public static final String TAG_Add = "addrs";
    public static final String TAG_IMAGE_URL_logo = "logo_url";


    //SMS

    public static final String URL_REQUEST_SMS = "http://www.svastek.in/brasham/otp/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://www.svastek.in/brasham/otp/verify_otp.php";
   // public static final String DATA_URL = "http://www.svastek.in/brasham/city.php";
    public static final String SUB_CITY_URL = "http://www.svastek.in/brasham/subcity.php";
    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "BRFOOD";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}