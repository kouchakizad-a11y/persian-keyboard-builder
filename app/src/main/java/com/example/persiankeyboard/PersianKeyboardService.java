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

        String[] keys = {
                "ض ص ث ق ف غ ع ه خ ح ج چ",
                "ش س ی ب ل ا ت ن م ک گ",
                "ظ ط ز ر ذ د پ و ژ"
        };

        for (String row : keys) {
            LinearLayout rowLayout = new LinearLayout(this);

            for (String key : row.split(" ")) {
                Button button = new Button(this);
                button.setText(key);

                button.setOnClickListener(v -> {
                    Button b = (Button) v;
                    getCurrentInputConnection()
                            .commitText(b.getText().toString(), 1);
                });

                rowLayout.addView(button);
            }

            keyboard.addView(rowLayout);
        }

        Button space = new Button(this);
        space.setText("فاصله");
        space.setOnClickListener(v ->
                getCurrentInputConnection().commitText(" ", 1)
        );

        keyboard.addView(space);

        return keyboard;
    }
}
