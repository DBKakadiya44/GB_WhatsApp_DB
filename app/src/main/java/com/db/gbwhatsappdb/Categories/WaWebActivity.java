package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.db.gbwhatsappdb.HelperUtils;
import com.db.gbwhatsappdb.PrefManager;
import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityWaWebBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

public class WaWebActivity extends AppCompatActivity {
    ActivityWaWebBinding binding;
    String KEY_ENABLE = "KEY_ENABLE";
    private TextView error_message_textview;
    boolean isKeyboardEnable;
    ImageView keyboard;
    private LinearLayout linearLayout_of_error;
    private WebView mWebView;
    PrefManager pref;
    ProgressDialog progressDialog;
    ImageView refresh;
    private AppCompatButton retryBtton;
    ConstraintLayout root_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        bindViews();
        bindWebView();
        this.pref = new PrefManager(this);
        this.retryBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindWebView();
            }
        });
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mWebView.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");
            }
        });
        this.keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isKeyboardEnable = pref.getBoolean(KEY_ENABLE);
                Log.d("HACK", "onCreate: " + isKeyboardEnable);
                try {
                    setKeyboardEnable(!isKeyboardEnable);
                } catch (Exception e) {
                    Log.d("HACK", "onCreate: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    private void setKeyboardEnable(boolean z) {
        this.isKeyboardEnable = z;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (z) {
            this.root_view.setDescendantFocusability(262144);
            this.keyboard.setImageResource(R.drawable.ic_baseline_keyboard_hide_24);
            this.pref.setBoolean(this.KEY_ENABLE, true);
            showSnackBar("Keyboard is unblocked.");
            return;
        }
        this.root_view.setDescendantFocusability(393216);
        this.mWebView.getRootView().requestFocus();
        this.keyboard.setImageResource(R.drawable.ic_baseline_keyboard_24);
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(this);
        }
        showSnackBar("Keyboard is blocked.");
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        this.pref.setBoolean(this.KEY_ENABLE, false);
    }

    public void showSnackBar(String str) {
        Snackbar.make(this.root_view, str, -1).show();
    }

    private void showProgressDialog() {
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading...");
        this.progressDialog.setCanceledOnTouchOutside(false);
        try {
            if (!this.progressDialog.isShowing()) {
                this.progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideProgressDialog() {
        try {
            ProgressDialog progressDialog2 = this.progressDialog;
            if (progressDialog2 != null && progressDialog2.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindWebView() {
        WebSettings settings = this.mWebView.getSettings();
        String userAgentString = settings.getUserAgentString();
        String replace = settings.getUserAgentString().replace(settings.getUserAgentString().substring(userAgentString.indexOf("("), userAgentString.indexOf(")") + 1), "(X11; Linux x86_64)");
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUserAgentString(replace);
        this.mWebView.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");
        this.mWebView.setWebChromeClient(new IWebClient());
        this.mWebView.setWebViewClient(new WebViewClient() {
            @SuppressLint("WrongConstant")
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                showProgressDialog();
                mWebView.setVisibility(8);
            }

            @SuppressLint("WrongConstant")
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                mWebView.setVisibility(0);
                hideProgressDialog();


            }


            @SuppressLint("WrongConstant")
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                mWebView.setVisibility(8);
                linearLayout_of_error.setVisibility(0);
                TextView textView = error_message_textview;
                textView.setText(i + IOUtils.LINE_SEPARATOR_UNIX + str);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void bindViews() {
        this.mWebView = (WebView) findViewById(R.id.webview);
        this.linearLayout_of_error = (LinearLayout) findViewById(R.id.linear_layout_error_layout_webview);
        this.retryBtton = (AppCompatButton) findViewById(R.id.btn_webview_retry);
        this.error_message_textview = (TextView) findViewById(R.id.text_view_error_web_view);
        this.refresh = (ImageView) findViewById(R.id.reload_webview);
        this.keyboard = (ImageView) findViewById(R.id.keyboard_disable);
        this.root_view = (ConstraintLayout) findViewById(R.id.root_layut_whatseb);
    }

    public void backPress(View view) {
        onBackPressed();
    }

    public class IWebClient extends WebChromeClient {
        private IWebClient() {
        }

        public void onPermissionRequest(PermissionRequest permissionRequest) {
            permissionRequest.grant(permissionRequest.getResources());
        }

        public void onProgressChanged(WebView webView, int i) {
            try {
                String encodeToString = Base64.encodeToString(HelperUtils.getCssValues(WaWebActivity.this).getBytes(), 2);
                mWebView.loadUrl("javascript:(function() {var parent = document.getElementsByTagName('head').item(0);var style = document.createElement('style');style.type = 'text/css';style.innerHTML = window.atob('" + encodeToString + "');parent.appendChild(style)})();");
            } catch (Exception unused) {
            }
        }
    }

}