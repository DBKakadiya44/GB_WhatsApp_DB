package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityTextRepeaterBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class TextRepeaterActivity extends AppCompatActivity {
    ActivityTextRepeaterBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private SharedPreferences data;
    private boolean firstRepeat = false;
    private double historyIndex = 0.0d;
    private Intent intent = new Intent();
    private double lineNumber = 0.0d;
    private double repeat = 0.0d;
    private String textRepeat = "";
    private String textToRepeat = "";
    private Vibrator vibrator;

    @SuppressLint("WrongConstant")
    private void initialize() {

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        data = getSharedPreferences("data", 0);

        binding.lnRepeat.setOnClickListener(view -> {
            _vibrate();
            if (binding.repeatInput.getText().toString().isEmpty() || binding.howManyInput.getText().toString().isEmpty()) {
                showMessage("Enter your text and how many repetitions");
                return;
            }
            textToRepeat = "";
            textRepeat = "";
            lineNumber = 1.0d;
            firstRepeat = true;
            repeat = Double.parseDouble(binding.howManyInput.getText().toString());
            textToRepeat = binding.repeatInput.getText().toString();

            if ((textToRepeat.length() * repeat) > 100000.0d) {
                showMessage("Character limit will be exceeded, try fewer repetitions");
                return;
            }

            if (!binding.lineCheckbox.isChecked()) {
                for (int i = 0; i < repeat; i++) {
                    if (firstRepeat) {
                        textRepeat = textToRepeat;
                        firstRepeat = false;
                    } else {
                        textRepeat = textRepeat.concat("").concat(textToRepeat);
                    }
                }
            } else {
                for (int i = 0; i < repeat; i++) {
                    if (firstRepeat) {
                        textRepeat = textToRepeat;
                        firstRepeat = false;
                    } else {
                        textRepeat = textRepeat.concat(System.lineSeparator()).concat(textToRepeat);
                    }
                }
            }

            binding.repeatedOutput.setText(textRepeat);
            _saveHistory();
        });

        binding.howManyInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().contains("-")) {
                    binding.howManyInput.setText("");
                }
            }
        });

        binding.lineCheckbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                data.edit().putString("newline", "on").commit();
            } else {
                data.edit().putString("newline", "off").commit();
            }
        });

        binding.ivDeleteText.setOnClickListener(view -> {
            binding.repeatInput.setText("");
            binding.howManyInput.setText("");
            binding.repeatedOutput.setText("");
            repeat = 0.0d;
            textToRepeat = "";
            textRepeat = "";
            lineNumber = 1.0d;
            firstRepeat = true;
        });

        binding.ivCopyText.setOnClickListener(view -> {
            if (binding.repeatedOutput.getText().toString().length() > 0) {
                getApplicationContext();
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", binding.repeatedOutput.getText().toString()));
                Toast.makeText(this, "Text Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.icWhatsShare.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.setPackage("com.whatsapp");
            intent.putExtra("android.intent.extra.TEXT", binding.repeatedOutput.getText().toString());
            if (intent.resolveActivity(getPackageManager()) == null) {
                Toast.makeText(this, "Whatsapp not installed.", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextRepeaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        initialize();
        initializeLogic();

    }

    private void initializeLogic() {
        lineNumber = 1.0d;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent2) {
        super.onActivityResult(i, i2, intent2);
    }

    public void _vibrate() {
        vibrator.vibrate(30);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (data.getString("historyindex", "").isEmpty()) {
            historyIndex = 0.0d;
        } else {
            historyIndex = Double.parseDouble(data.getString("historyindex", ""));
        }
    }

    public void _saveHistory() {
        calendar = Calendar.getInstance();
        data.edit().putString(String.valueOf((long) historyIndex).concat("text"), binding.repeatInput.getText().toString()).commit();
        data.edit().putString(String.valueOf((long) historyIndex).concat("reps"), binding.howManyInput.getText().toString()).commit();
        data.edit().putString(String.valueOf((long) historyIndex).concat("date"), new SimpleDateFormat("dd MMM yyyy").format(calendar.getTime()).concat(" ".concat(new SimpleDateFormat("HH:mm").format(calendar.getTime())))).commit();
        historyIndex += 1.0d;
        data.edit().putString("historyindex", String.valueOf((long) historyIndex)).commit();
    }

    @Deprecated
    public void showMessage(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationY(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[1];
    }

    @Deprecated
    public int getLocationX(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[0];
    }

    @Deprecated
    public int getRandom(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    @SuppressLint("WrongConstant")
    @Deprecated
    public float getDip(int i) {
        return TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView listView) {
        ArrayList<Double> arrayList = new ArrayList<>();
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        for (int i = 0; i < checkedItemPositions.size(); i++) {
            if (checkedItemPositions.valueAt(i)) {
                arrayList.add(Double.valueOf((double) checkedItemPositions.keyAt(i)));
            }
        }
        return arrayList;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

}