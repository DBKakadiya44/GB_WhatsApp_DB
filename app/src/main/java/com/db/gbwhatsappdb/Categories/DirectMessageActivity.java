package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityDirectMessageBinding;
import com.hbb20.CountryCodePicker;

public class DirectMessageActivity extends AppCompatActivity {
    ActivityDirectMessageBinding binding;
    String TAG = "alam_directchat";
    CountryCodePicker countryCodePicker;
    EditText messageBody;
    AppCompatEditText phoneNumber;
    TextView sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bindViews();
        bindAction();

    }

    private void bindViews() {
        this.countryCodePicker = findViewById(R.id.ccp);
        this.phoneNumber = findViewById(R.id.editText_carrierNumber);
        this.messageBody = findViewById(R.id.directchat_edit_text_message_box);
        this.sendBtn = findViewById(R.id.direc_chat_send_message_button);
    }

    private void bindAction() {
        this.sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str = "+" + countryCodePicker.getSelectedCountryCode();
                String obj = phoneNumber.getText().toString();
                String obj2 = messageBody.getText().toString();
                Log.d(TAG, "bindAction: " + str + " -cell- " + obj + "-msg-" + obj2);
                if (obj.isEmpty()) {
                    phoneNumber.setError("Empty field!");
                } else if (obj2.isEmpty()) {
                    messageBody.setError("Type something!");
                } else if (isWhatsAppInstalled()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + str + obj + "&text=" + obj2));
                    DirectMessageActivity directChat = DirectMessageActivity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(obj);
                    directChat.saveChatToDB(sb.toString(), obj2);
                    startActivity(intent);
                } else {
                    Toast.makeText(DirectMessageActivity.this, "WhatsApp not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveChatToDB(String str, String str2) {
        // Implement your code to save chat to the database
    }

    private boolean isWhatsAppInstalled() {
        try {
            getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

}