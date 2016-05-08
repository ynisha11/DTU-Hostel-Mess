package utils;

import android.content.Intent;

/**
 * Created by vivek on 08/05/16.
 */
public class AppUtils {

    public static Intent SendFeedBack()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ynisha11@gmail.com", "maskaravivek@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback: DTU Hostel Mess App");
        intent.putExtra(Intent.EXTRA_TEXT, "We would love to hear your feedback!");
        return intent;
    }
}
