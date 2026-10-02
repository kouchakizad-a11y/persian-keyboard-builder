package com.example.persiankeyboard;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PersianKeyboardService extends InputMethodService {

    @Override
    public View onCreateInputView() {

        LinearLayout keyboard = new LinearLayout(this);
        keyboard.setOrientation(LinearLayout.VERTICAL);

        String[][] keys = {
                {"ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "چ"},
                {"ش", "س", "ی", "ب", "ل", "ا", "ت", "ن", "م", "ک", "گ"},
                {"ظ", "ط", "ز", "ر", "ذ", "د", "پ", "و", "ژ"}
        };

        for (String[] row : keys) {

            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (String key : row) {

                Button button = new Button(this);
                button.setText(key);

                button.setOnClickListener(v -> {
                    Button b = (Button) v;
                    getCurrentInputConnection()
                            .commitText(b.getText().toString(), 1);
                });

                rowLayout.addView(button,
                        new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1
                        ));
            }

            keyboard.addView(rowLayout);
        }

        // Space
        Button space = new Button(this);
        space.setText("فاصله");
        space.setOnClickListener(v ->
                getCurrentInputConnection().commitText(" ", 1)
        );

        keyboard.addView(space);

        // Backspace
        Button backspace = new Button(this);
        backspace.setText("⌫");
        backspace.setOnClickListener(v -> {
            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection().deleteSurroundingText(1, 0);
            }
        });

        keyboard.addView(backspace);

        // Enter
        Button enter = new Button(this);
        enter.setText("↵");
        enter.setOnClickListener(v -> {
            if (getCurrentInputConnection() != null) {
                getCurrentInputConnection().sendKeyEvent(
                        new android.view.KeyEvent(
                                android.view.KeyEvent.ACTION_DOWN,
                                android.view.KeyEvent.KEYCODE_ENTER
                        )
                );
            }
        });

        keyboard.addView(enter);

        return keyboard;
    }
            }
