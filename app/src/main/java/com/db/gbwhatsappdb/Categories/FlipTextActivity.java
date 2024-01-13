package com.db.gbwhatsappdb.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.db.gbwhatsappdb.R;
import com.db.gbwhatsappdb.databinding.ActivityFlipTextBinding;

public class FlipTextActivity extends AppCompatActivity {
    ActivityFlipTextBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlipTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));

        binding.imageView3.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.reverse.setOnClickListener(view -> {
            String originalString = binding.editText.getText().toString();
            StringBuilder reversedString = new StringBuilder(originalString).reverse();
            String result = reversedString.toString();
            binding.tvgenerated.setText(""+result);
        });

        binding.upsidedown.setOnClickListener(view -> {
            String originalString = binding.editText.getText().toString();
            StringBuilder mirroredString = new StringBuilder();

            for (int i = originalString.length() - 1; i >= 0; i--) {
                char c = originalString.charAt(i);
                char mirroredChar;

                switch (c) {
                    case 'a':
                        mirroredChar = 'ɐ';
                        break;
                    case 'b':
                        mirroredChar = 'q';
                        break;
                    case 'c':
                        mirroredChar = 'ɔ';
                        break;
                    case 'd':
                        mirroredChar = 'p';
                        break;
                    case 'e':
                        mirroredChar = 'ə';
                        break;
                    case 'f':
                        mirroredChar = 'ɟ';
                        break;
                    case 'g':
                        mirroredChar = 'ƃ';
                        break;
                    case 'h':
                        mirroredChar = 'ɥ';
                        break;
                    case 'i':
                        mirroredChar = 'ı';
                        break;
                    case 'j':
                        mirroredChar = 'ɾ';
                        break;
                    case 'k':
                        mirroredChar = 'ʞ';
                        break;
                    case 'l':
                        mirroredChar = 'l';
                        break;
                    case 'm':
                        mirroredChar = 'ɯ';
                        break;
                    case 'n':
                        mirroredChar = 'u';
                        break;
                    case 'o':
                        mirroredChar = 'o';
                        break;
                    case 'p':
                        mirroredChar = 'd';
                        break;
                    case 'q':
                        mirroredChar = 'b';
                        break;
                    case 'r':
                        mirroredChar = 'ɹ';
                        break;
                    case 's':
                        mirroredChar = 's';
                        break;
                    case 't':
                        mirroredChar = 'ʇ';
                        break;
                    case 'u':
                        mirroredChar = 'n';
                        break;
                    case 'v':
                        mirroredChar = 'ʌ';
                        break;
                    case 'w':
                        mirroredChar = 'ʍ';
                        break;
                    case 'x':
                        mirroredChar = 'x';
                        break;
                    case 'y':
                        mirroredChar = 'ʎ';
                        break;
                    case 'z':
                        mirroredChar = 'z';
                        break;

                    case 'A':
                        mirroredChar = '∀';
                        break;
                    case 'B':
                        mirroredChar = 'ꓭ';
                        break;
                    case 'C':
                        mirroredChar = 'Ͻ';
                        break;
                    case 'D':
                        mirroredChar = 'ᗡ';
                        break;
                    case 'E':
                        mirroredChar = 'Ǝ';
                        break;
                    case 'F':
                        mirroredChar = '⅁';
                    break;
                    case 'G':
                        mirroredChar = 'Ә';
                        break;
                    case 'H':
                        mirroredChar = 'H';
                        break;
                    case 'I':
                        mirroredChar = 'I';
                        break;
                    case 'J':
                        mirroredChar = 'ᒋ';
                        break;
                    case 'K':
                        mirroredChar = 'ꓘ';
                        break;
                    case 'L':
                        mirroredChar = '⅂';
                        break;
                    case 'M':
                        mirroredChar = 'ꟽ';
                        break;
                    case 'N':
                        mirroredChar = 'N';
                        break;
                    case 'O':
                        mirroredChar = 'O';
                        break;
                    case 'P':
                        mirroredChar = 'Ԁ';
                        break;
                    case 'Q':
                        mirroredChar = 'Ꝺ';
                        break;
                    case 'R':
                        mirroredChar = 'ꓤ';
                        break;
                    case 'S':
                        mirroredChar = 'S';
                        break;
                    case 'T':
                        mirroredChar = 'ꓕ';
                        break;
                    case 'U':
                        mirroredChar = 'Ո';
                        break;
                    case 'V':
                        mirroredChar = 'Ʌ';
                        break;
                    case 'W':
                        mirroredChar = 'Ϻ';
                        break;
                    case 'X':
                        mirroredChar = 'X';
                        break;
                    case 'Y':
                        mirroredChar = '⅄';
                        break;
                    case 'Z':
                        mirroredChar = 'Z';
                        break;

                    default:
                        mirroredChar = c;
                }

                mirroredString.append(mirroredChar);
            }

            String result = mirroredString.toString();

            binding.tvgenerated.setText(""+result);
        });

        binding.mirror.setOnClickListener(view -> {
            String originalString = binding.editText.getText().toString();
            StringBuilder mirroredString = new StringBuilder();

            for (int i = originalString.length() - 1; i >= 0; i--) {
                char c = originalString.charAt(i);
                char mirroredChar;

                switch (c) {
                    case 'a':
                        mirroredChar = 'ɒ';
                        break;
                    case 'b':
                        mirroredChar = 'd';
                        break;
                    case 'c':
                        mirroredChar = 'ɔ';
                        break;
                    case 'd':
                        mirroredChar = 'b';
                        break;
                    case 'e':
                        mirroredChar = 'ɘ';
                        break;
                    case 'f':
                        mirroredChar = 'ʇ';
                        break;
                    case 'g':
                        mirroredChar = 'ϱ';
                        break;
                    case 'h':
                        mirroredChar = 'ʜ';
                        break;
                    case 'i':
                        mirroredChar = 'i';
                        break;
                    case 'j':
                        mirroredChar = 'į';
                        break;
                    case 'k':
                        mirroredChar = 'ʞ';
                        break;
                    case 'l':
                        mirroredChar = 'l';
                        break;
                    case 'm':
                        mirroredChar = 'm';
                        break;
                    case 'n':
                        mirroredChar = 'n';
                        break;
                    case 'o':
                        mirroredChar = 'o';
                        break;
                    case 'p':
                        mirroredChar = 'q';
                        break;
                    case 'q':
                        mirroredChar = 'p';
                        break;
                    case 'r':
                        mirroredChar = 'ɿ';
                        break;
                    case 's':
                        mirroredChar = 'ƨ';
                        break;
                    case 't':
                        mirroredChar = 'Ɉ';
                        break;
                    case 'u':
                        mirroredChar = 'υ';
                        break;
                    case 'v':
                        mirroredChar = 'v';
                        break;
                    case 'w':
                        mirroredChar = 'w';
                        break;
                    case 'x':
                        mirroredChar = 'x';
                        break;
                    case 'y':
                        mirroredChar = 'γ';
                        break;
                    case 'z':
                        mirroredChar = 'z';
                        break;

                    case 'A':
                        mirroredChar = 'A';
                        break;
                    case 'B':
                        mirroredChar = 'ꓭ';
                        break;
                    case 'C':
                        mirroredChar = 'C';
                        break;
                    case 'D':
                        mirroredChar = 'ꓷ';
                        break;
                    case 'E':
                        mirroredChar = 'Ǝ';
                        break;
                    case 'F':
                        mirroredChar = 'ᖷ';
                        break;
                    case 'G':
                        mirroredChar = 'Ә';
                        break;
                    case 'H':
                        mirroredChar = 'H';
                        break;
                    case 'I':
                        mirroredChar = 'I';
                        break;
                    case 'J':
                        mirroredChar = 'Ⴑ';
                        break;
                    case 'K':
                        mirroredChar = 'ꓘ';
                        break;
                    case 'L':
                        mirroredChar = '⅃';
                        break;
                    case 'M':
                        mirroredChar = 'M';
                        break;
                    case 'N':
                        mirroredChar = 'И';
                        break;
                    case 'O':
                        mirroredChar = 'O';
                        break;
                    case 'P':
                        mirroredChar = 'ᕋ';
                        break;
                    case 'Q':
                        mirroredChar = 'Ϙ';
                        break;
                    case 'R':
                        mirroredChar = 'Я';
                        break;
                    case 'S':
                        mirroredChar = 'Ƨ';
                        break;
                    case 'T':
                        mirroredChar = 'T';
                        break;
                    case 'U':
                        mirroredChar = 'U';
                        break;
                    case 'V':
                        mirroredChar = 'V';
                        break;
                    case 'W':
                        mirroredChar = 'W';
                        break;
                    case 'X':
                        mirroredChar = 'X';
                        break;
                    case 'Y':
                        mirroredChar = 'Y';
                        break;
                    case 'Z':
                        mirroredChar = 'Z';
                        break;

                    default:
                        mirroredChar = c;
                }

                mirroredString.append(mirroredChar);
            }

            String result = mirroredString.toString();

            binding.tvgenerated.setText(""+result);
        });

        binding.flipp.setOnClickListener(view -> {
            String originalString = binding.editText.getText().toString();
            StringBuilder mirroredString = new StringBuilder();

            for (int i = originalString.length() - 1; i >= 0; i--) {
                char c = originalString.charAt(i);
                char mirroredChar;

                switch (c) {
                    case 'a':
                        mirroredChar = 'ɑ';
                        break;
                    case 'b':
                        mirroredChar = 'p';
                        break;
                    case 'c':
                        mirroredChar = 'c';
                        break;
                    case 'd':
                        mirroredChar = 'q';
                        break;
                    case 'e':
                        mirroredChar = 'ԍ';
                        break;
                    case 'f':
                        mirroredChar = 'ɻ';
                        break;
                    case 'g':
                        mirroredChar = 'მ';
                        break;
                    case 'h':
                        mirroredChar = 'μ';
                        break;
                    case 'i':
                        mirroredChar = 'ᴉ';
                        break;
                    case 'j':
                        mirroredChar = 'ᒉ';
                        break;
                    case 'k':
                        mirroredChar = 'ĸ';
                        break;
                    case 'l':
                        mirroredChar = 'ɼ';
                        break;
                    case 'm':
                        mirroredChar = 'w';
                        break;
                    case 'n':
                        mirroredChar = 'u';
                        break;
                    case 'o':
                        mirroredChar = 'o';
                        break;
                    case 'p':
                        mirroredChar = 'b';
                        break;
                    case 'q':
                        mirroredChar = 'd';
                        break;
                    case 'r':
                        mirroredChar = 'ʁ';
                        break;
                    case 's':
                        mirroredChar = 'ƨ';
                        break;
                    case 't':
                        mirroredChar = 'ϝ';
                        break;
                    case 'u':
                        mirroredChar = 'n';
                        break;
                    case 'v':
                        mirroredChar = 'ʌ';
                        break;
                    case 'w':
                        mirroredChar = 'ʍ';
                        break;
                    case 'x':
                        mirroredChar = 'x';
                        break;
                    case 'y':
                        mirroredChar = 'λ';
                        break;
                    case 'z':
                        mirroredChar = 'z';
                        break;

                    case 'A':
                        mirroredChar = 'Ɐ';
                        break;
                    case 'B':
                        mirroredChar = 'B';
                        break;
                    case 'C':
                        mirroredChar = 'C';
                        break;
                    case 'D':
                        mirroredChar = 'D';
                        break;
                    case 'E':
                        mirroredChar = 'E';
                        break;
                    case 'F':
                        mirroredChar = 'ᖶ';
                        break;
                    case 'G':
                        mirroredChar = 'ᘓ';
                        break;
                    case 'H':
                        mirroredChar = 'H';
                        break;
                    case 'I':
                        mirroredChar = 'I';
                        break;
                    case 'J':
                        mirroredChar = 'ᒉ';
                        break;
                    case 'K':
                        mirroredChar = 'K';
                        break;
                    case 'L':
                        mirroredChar = 'Γ';
                        break;
                    case 'M':
                        mirroredChar = 'W';
                        break;
                    case 'N':
                        mirroredChar = 'И';
                        break;
                    case 'O':
                        mirroredChar = 'O';
                        break;
                    case 'P':
                        mirroredChar = 'b';
                        break;
                    case 'Q':
                        mirroredChar = '⥀';
                        break;
                    case 'R':
                        mirroredChar = 'ᖉ';
                        break;
                    case 'S':
                        mirroredChar = 'Ƨ';
                        break;
                    case 'T':
                        mirroredChar = 'ꓕ';
                        break;
                    case 'U':
                        mirroredChar = 'ꓵ';
                        break;
                    case 'V':
                        mirroredChar = 'Λ';
                        break;
                    case 'W':
                        mirroredChar = 'M';
                        break;
                    case 'X':
                        mirroredChar = 'X';
                        break;
                    case 'Y':
                        mirroredChar = '⅄';
                        break;
                    case 'Z':
                        mirroredChar = 'Z';
                        break;

                    default:
                        mirroredChar = c;
                }

                mirroredString.append(mirroredChar);
            }

            String result = mirroredString.toString();

            binding.tvgenerated.setText(""+result);
        });

        binding.btngenerate.setOnClickListener(view -> {
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Text", binding.tvgenerated.getText().toString()));
            Toast.makeText(FlipTextActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
        });

        binding.btncopy.setOnClickListener(view -> {
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Text", binding.tvgenerated.getText().toString()));
            Toast.makeText(FlipTextActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
        });

        binding.btndelete.setOnClickListener(view -> {
            binding.tvgenerated.setText("");
            binding.editText.setText("");
        });
    }
}