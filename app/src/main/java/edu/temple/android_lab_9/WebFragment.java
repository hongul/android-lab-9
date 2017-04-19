package edu.temple.android_lab_9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static edu.temple.android_lab_9.R.id.webView;

public class WebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Position";
    private static final String URL_KEY = "urlKey";

    // TODO: Rename and change types of parameters
    private int position;
    private String url;

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment WebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(int position, String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putString(URL_KEY, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate", "---------------------- onCreate Called ------------------");
        if (getArguments() != null) {
            Log.d("Arguments", "getArguments is has args: " + getArguments().getString(URL_KEY));
            position = getArguments().getInt(ARG_PARAM1);
            this.url = getArguments().getString(URL_KEY);
        } else {
            Log.d("Arguments", "getArguments doesn't have args");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", "---------------------- OnCreateView Called ----------------");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        WebView wv = (WebView) v.findViewById(webView);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Toast.makeText(getContext(), request.getUrl().toString(), Toast.LENGTH_SHORT).show();
                view.loadUrl(request.getUrl().toString());
                url = request.getUrl().toString();
                return true;
            }
        });

        if (this.url != null) {
            wv.loadUrl(this.url);
        } else {
            //wv.loadUrl("https://google.com");      // Homepage
        }

        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("url", url);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy", "---------------------- OnDestroy Called ----------------");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d("onDestoryView", "---------------------- OnDestoryView Called ----------------");
        super.onDestroyView();
    }


}
