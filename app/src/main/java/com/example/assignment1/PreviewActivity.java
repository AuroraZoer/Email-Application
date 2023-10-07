package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PreviewActivity extends AppCompatActivity {
    private final String TAG = "PreviewActivity";
    private static final String FROM_TEXT = "fromText";
    private static final String TO_TEXT = "toText";
    private static final String CC_TEXT = "ccText";
    private static final String BCC_TEXT = "bccText";
    private static final String SUBJECT_TEXT = "subjectText";
    private static final String MESSAGE_TEXT = "messageText";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Log.d(TAG, "onCreate execute");

        final TextView previewText = findViewById(R.id.previewText);
        Button editButton = findViewById(R.id.editButton);
        Button sendButton = findViewById(R.id.sendButton);

        Intent intent = getIntent();
        final String fromText = intent.getStringExtra(FROM_TEXT);
        final String toText = intent.getStringExtra(TO_TEXT);
        final String ccText = intent.getStringExtra(CC_TEXT);
        final String bccText = intent.getStringExtra(BCC_TEXT);
        final String subjectText = intent.getStringExtra(SUBJECT_TEXT);
        final String messageText = intent.getStringExtra(MESSAGE_TEXT);

        previewText.setText("From: " + fromText + "\n" +
                "To: " + toText + "\n" +
                "CC: " + ccText + "\n" +
//                "BCC: " + bccText + "\n" +
                "Subject: " + subjectText + "\n" +
                "Message: " + messageText + "\n");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromText.trim().isEmpty()) {
                    Toast.makeText(PreviewActivity.this, "Please enter the 'From' field", Toast.LENGTH_LONG).show();
                } else if (toText.trim().isEmpty()) {
                    Toast.makeText(PreviewActivity.this, "Please enter the 'To' field", Toast.LENGTH_LONG).show();
                } else if (subjectText.trim().isEmpty()) {
                    Toast.makeText(PreviewActivity.this, "Please enter the 'Subject' field", Toast.LENGTH_LONG).show();
                } else if (messageText.trim().isEmpty()) {
                    Toast.makeText(PreviewActivity.this, "Please enter the 'Message' field", Toast.LENGTH_LONG).show();
                } else {
//                    EmailSender.sendEmail(fromText, toText, ccText, bccText, subjectText, messageText);
//                    Toast.makeText(PreviewActivity.this, "Email sent successfully", Toast.LENGTH_LONG).show();

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toText});
                    emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ccText});
                    emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{bccText});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, messageText);
                    try {
                        startActivity(emailIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(PreviewActivity.this, "No email client available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
