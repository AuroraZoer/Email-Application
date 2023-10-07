package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FROM_TEXT = "fromText";
    private static final String TO_TEXT = "toText";
    private static final String CC_TEXT = "ccText";
    private static final String BCC_TEXT = "bccText";
    private static final String SUBJECT_TEXT = "subjectText";
    private static final String MESSAGE_TEXT = "messageText";
    private Button clearButton, previewButton;
    private EditText fromText, toText, ccText, bccText, subjectText, messageText;
    private SharedPreferences sharedPreferences;

    private void EmailAppInit() {
        clearButton = findViewById(R.id.clearButton);
        previewButton = findViewById(R.id.previewButton);
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        ccText = findViewById(R.id.ccText);
        bccText = findViewById(R.id.bccText);
        subjectText = findViewById(R.id.subjectText);
        messageText = findViewById(R.id.messageText);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Log.d(TAG, "EmailAppInit");
    }

    private String getPrefString(String key) {
        return sharedPreferences.getString(key, "");
    }

    private void loadPrefValuesToFields() {
        fromText.setText(getPrefString(FROM_TEXT));
        toText.setText(getPrefString(TO_TEXT));
        ccText.setText(getPrefString(CC_TEXT));
        bccText.setText(getPrefString(BCC_TEXT));
        subjectText.setText(getPrefString(SUBJECT_TEXT));
        messageText.setText(getPrefString(MESSAGE_TEXT));
        Log.d(TAG, "loadPrefValuesToFields");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate execute");
        EmailAppInit();
        loadPrefValuesToFields();
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        previewButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                                                 intent.putExtra(FROM_TEXT, fromText.getText().toString());
                                                 intent.putExtra(TO_TEXT, toText.getText().toString());
                                                 intent.putExtra(CC_TEXT, ccText.getText().toString());
                                                 intent.putExtra(BCC_TEXT, bccText.getText().toString());
                                                 intent.putExtra(SUBJECT_TEXT, subjectText.getText().toString());
                                                 intent.putExtra(MESSAGE_TEXT, messageText.getText().toString());
                                                 startActivity(intent);

                                                 editor.putString(FROM_TEXT, fromText.getText().toString());
                                                 editor.putString(TO_TEXT, toText.getText().toString());
                                                 editor.putString(CC_TEXT, ccText.getText().toString());
                                                 editor.putString(BCC_TEXT, bccText.getText().toString());
                                                 editor.putString(SUBJECT_TEXT, subjectText.getText().toString());
                                                 editor.putString(MESSAGE_TEXT, messageText.getText().toString());
                                                 editor.apply();
                                             }
                                         }
        );
        clearButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               fromText.setText("");
                                               toText.setText("");
                                               ccText.setText("");
                                               bccText.setText("");
                                               subjectText.setText("");
                                               messageText.setText("");
                                               editor.clear();
                                           }
                                       }
        );
    }
}
