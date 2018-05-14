package www.seotoolzz.com.Ask.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import www.seotoolzz.com.Ask.Helper.Helper;
import www.seotoolzz.com.Ask.Fragment.FirstFragment;
import www.seotoolzz.com.Ask.R;
import www.seotoolzz.com.Ask.Fragment.SecondFragment;
import www.seotoolzz.com.Ask.Fragment.ThirdFragment;
import android.content.SharedPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import www.seotoolzz.com.Ask.RequestController.AsksController;

public class MainActivity extends AppCompatActivity
{
    private TabLayout mTabLayout;
    private String getUserUrl = "http://laravel-demo-deploy.herokuapp.com/api/v0/auth/user";

    private int[] mTabsIcons = {
            R.drawable.message,
            R.drawable.chat,
            R.drawable.notification};

    FloatingActionButton btnFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Helper.isLogin((AppCompatActivity) MainActivity.this)) {
            android.content.SharedPreferences sharePrefs = MainActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
            Log.d("TEST_COND", sharePrefs.getString("username", "EMPTY").equals("EMPTY") + "");
            if (sharePrefs.getString("username", "EMPTY").equals("EMPTY")) {
                getUserInfo();
            }
        }

        btnFab = (FloatingActionButton) findViewById(R.id.fab);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Helper.isLogin(MainActivity.this)) {
                    Toast.makeText(getApplicationContext(),
                            "Please login before do this",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Intent changeView = new Intent(MainActivity.this, CreatNewQuestion.class);
                    startActivity(changeView);
                }

            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

    }

    private void getUserInfo() {
        com.android.volley.toolbox.StringRequest stringRequest = new StringRequest(Request.Method.GET, this.getUserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getJSONObject("meta").getInt("status");

                    if (code == 700) {
                        // Get token and save in local storage
                        JSONObject data = res.getJSONObject("data");
                        Log.d("USER_PROFILE", data.getString("username"));
                        SharedPreferences.Editor sharePrefs = MainActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE).edit();
                        sharePrefs.putInt("userId", data.getInt("id"));
                        sharePrefs.putString("username", data.getString("username"));
                        sharePrefs.putString("email", data.getString("email"));
                        sharePrefs.putString("role", data.getString("role"));
                        sharePrefs.commit();
                    } else {
                        Toast.makeText(MainActivity.this.getApplicationContext(), res.getJSONObject("meta").getJSONObject("message").getString("main"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    Log.d("VOLLEY_ERROR", "Unknow error");
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Unknow error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("VOLLEY_ERROR", "ERROR: " + error.getMessage());
                    Toast.makeText(MainActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharePrefs = MainActivity.this.getApplicationContext().getSharedPreferences("ASKS", MODE_PRIVATE);
                params.put("Authorization", sharePrefs.getString("token", null));

                return params;
            }
        };
        AsksController.getmInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 3;

        private final String[] mTabsTitle = {"Question", "Notifications","User"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            Log.d("POS_TAB", "getTabView: " + position);
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.toolbar, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);

            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FirstFragment.newInstance(1);
                case 1:
                    return SecondFragment.newInstance(2);
                case 2:
                    return ThirdFragment.newInstance(3);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }
}
