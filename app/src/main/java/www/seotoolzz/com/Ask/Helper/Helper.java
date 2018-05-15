package www.seotoolzz.com.Ask.Helper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import www.seotoolzz.com.Ask.Model.User;

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
