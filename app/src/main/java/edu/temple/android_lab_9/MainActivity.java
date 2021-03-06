package edu.temple.android_lab_9;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String HOME_PAGE = "https://www.google.com";

    Button button;
    EditText editText;
    WebAdapter webAdapter;
    ViewPager viewPager;
    String currentUrl;
    Uri data;
    Toolbar toolbar;
    int currentFrag, maxFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up the toolbar on top of app
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();

        data = getIntent().getData();

        currentFrag = 1;
        maxFrag = 1;

        if (data != null) {
            currentUrl = data.toString();
        } else {
            currentUrl = HOME_PAGE;
        }

        button = (Button) findViewById(R.id.urlButton);
        editText = (EditText) findViewById(R.id.urlEditText);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        webAdapter = new WebAdapter(getSupportFragmentManager());
        viewPager.setAdapter(webAdapter);
        webAdapter.addUrl(currentUrl);

        viewPager.setCurrentItem(currentFrag);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loads the new URL into webView
                currentUrl = editText.getText().toString();
                WebView webView = (WebView) findViewById(R.id.webView);
                webView.loadUrl(currentUrl);

                // Updates
                webAdapter.updateUrl(currentUrl, viewPager.getCurrentItem());
                webAdapter.notifyDataSetChanged();
                Log.d("URL List", webAdapter.urls.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                if (currentFrag > 0) {
                    currentFrag--;
                    viewPager.setCurrentItem(currentFrag);
                    Log.d("Fragment Number", "Opening Fragment: " + currentFrag);
                }
                return true;
            case R.id.action_forward:
                if (currentFrag < maxFrag - 1) {
                    currentFrag++;
                    viewPager.setCurrentItem(currentFrag);
                    Log.d("Fragment Number", "Opening Fragment: " + currentFrag);
                }
                return true;
            case R.id.action_new:
                maxFrag++;
                currentFrag = maxFrag;
                webAdapter.addUrl(HOME_PAGE);
                webAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(currentFrag);
                Log.d("URL List", webAdapter.urls.toString());

                // Move back a page because adapter always add two fragments
                currentFrag--;
                viewPager.setCurrentItem(currentFrag);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class WebAdapter extends FragmentStatePagerAdapter {

        ArrayList urls = new ArrayList<>();

        public WebAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addUrl(String str) {
            urls.add(str);
        }

        public void updateUrl(String str, int position) {
            urls.set(position, str);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public int getCount() {
            return maxFrag;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("getItem", String.valueOf(position));
            return WebFragment.newInstance(position, urls.get(position).toString());
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

}

