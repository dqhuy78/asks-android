package www.seotoolzz.com.Ask.Helper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

public class Helper {
    public static boolean isLogin(AppCompatActivity activity)
    {
        SharedPreferences sharePrefs = activity.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
        String token = sharePrefs.getString("token", null);
        if (token == null) {
            return false;
        } else {
            return true;
        }
    }
}
