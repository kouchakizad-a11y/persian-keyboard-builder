package com.example.persiankeyboard;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PersianKeyboardService extends InputMethodService {

    @Override
    public View onCreateInputView() {

        LinearLayout keyboard = new LinearLayout(this);
        keyboard.setOrientation(LinearLayout.VERTICAL);

        addRow(keyboard, new String[]{
                "ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "چ"
        });

        addRow(keyboard, new String[]{
                "ش", "س", "ی", "ب", "ل", "ا", "ت", "ن", "م", "ک", "گ"
        });

        addRow(keyboard, new String[]{
                "ظ", "ط", "ز", "ر", "ذ", "د", "پ", "و", "ژ"
        });

        addRow(keyboard, new String[]{
                "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹", "۰"
        });

        addRow(keyboard, new String[]{
                ".", "،", "؟", "!", ":", ";", "(", ")", "-", "_"
        });

        // ردیف پایین
        LinearLayout bottomRow = new LinearLayout(this);
        bottomRow.setOrientation(LinearLayout.HORIZONTAL);

        Button backspace = createButton("⌫");
        backspace.setOnClickListener(v -> {
            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection().deleteSurroundingText(1, 0);
            }
        });

        Button space = createButton("فاصله");
        space.setOnClickListener(v -> {
            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection().commitText(" ", 1);
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

        bottomRow.addView(backspace, buttonParams(1));
        bottomRow.addView(space, buttonParams(3));
        bottomRow.addView(enter, buttonParams(1));

        keyboard.addView(bottomRow);

        return keyboard;
    }

    private void addRow(LinearLayout keyboard, String[] keys) {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

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

    private Button createButton(String text) {

        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(18);
        return button;
    }

    private LinearLayout.LayoutParams buttonParams(float weight) {

        return new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                weight
        );
    }
}
