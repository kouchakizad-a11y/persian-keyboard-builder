package com.example.persiankeyboard;

import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PersianKeyboardService extends InputMethodService {

    private LinearLayout keyboard;
    private boolean englishMode = false;

    private final int KEY_HEIGHT = 58;
    private final int KEY_MARGIN = 3;

    @Override
    public View onCreateInputView() {

        keyboard = new LinearLayout(this);
        keyboard.setOrientation(LinearLayout.VERTICAL);
        keyboard.setGravity(Gravity.CENTER);
        keyboard.setPadding(12, 10, 12, 10);

        buildKeyboard();

        return keyboard;
    }

    private void buildKeyboard() {

        keyboard.removeAllViews();

        if (englishMode) {

            addRow(new String[]{
                    "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"
            });

            addRow(new String[]{
                    "A", "S", "D", "F", "G", "H", "J", "K", "L"
            });

            addRow(new String[]{
                    "Z", "X", "C", "V", "B", "N", "M"
            });

        } else {

            addRow(new String[]{
                    "ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "چ"
            });

            addRow(new String[]{
                    "ش", "س", "ی", "ب", "ل", "ا", "ت", "ن", "م", "ک", "گ"
            });

            addRow(new String[]{
                    "ظ", "ط", "ز", "ر", "ذ", "د", "پ", "و", "ژ"
            });
        }

        addRow(new String[]{
                "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹", "۰"
        });

        addRow(new String[]{
                ".", "،", "؟", "!", ":", ";", "(", ")", "-", "_"
        });

        addBottomRow();
    }

    private void addRow(String[] keys) {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);

        for (String key : keys) {

            Button button = createButton(key);

            button.setOnClickListener(v -> {

                if (getCurrentInputConnection() != null) {
                    getCurrentInputConnection().commitText(
                            ((Button) v).getText().toString(),
                            1
                    );
                }
            });

            row.addView(button, buttonParams(1));
        }

        keyboard.addView(row);
    }

    private void addBottomRow() {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);

        Button language = createButton(englishMode ? "فارسی" : "EN");

        language.setOnClickListener(v -> {
            englishMode = !englishMode;
            buildKeyboard();
        });

        Button backspace = createButton("⌫");

        backspace.setOnClickListener(v -> {

            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection()
                        .deleteSurroundingText(1, 0);
            }
        });

        Button space = createButton("فاصله");

        space.setOnClickListener(v -> {

            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection()
                        .commitText(" ", 1);
            }
        });

        Button enter = createButton("↵");

        enter.setOnClickListener(v -> {

            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_ENTER
                        )
                );
            }
        });

        row.addView(language, buttonParams(1));
        row.addView(backspace, buttonParams(1));
        row.addView(space, buttonParams(4));
        row.addView(enter, buttonParams(1));

        keyboard.addView(row);
    }

    private Button createButton(String text) {

        Button button = new Button(this);

        button.setText(text);
        button.setText
