package com.example.debtmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.debtmanager.R;

public class DebtActivity extends AppCompatActivity {

    public static final String TAG = DebtActivity.class.getSimpleName();
    public static final String EXTRA_ID = "com.example.debtmanager.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.debtmanager.EXTRA_NAME";
    public static final String EXTRA_AMOUNT = "com.example.debtmanager.EXTRA_AMOUNT";

    private EditText mEditName;
    private EditText mEditDebtAmount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);

        mEditName = findViewById(R.id.edit_name);
        mEditDebtAmount = findViewById(R.id.edit_debt_amount);


        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDebtInfo();
            }
        });

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: " + intent.getStringExtra(EXTRA_NAME));
        Log.d(TAG, "onCreate: " + intent.getStringExtra(EXTRA_AMOUNT));
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Info");
            mEditName.setText(intent.getStringExtra(EXTRA_NAME));
            mEditDebtAmount.setText(intent.getStringExtra(EXTRA_AMOUNT));
        } else {
            setTitle("Debt Manager");
        }
    }

    private void saveDebtInfo() {

        String name = mEditName.getText().toString();
        String debtAmount = mEditDebtAmount.getText().toString();


        if (TextUtils.isEmpty(mEditName.getText()) || TextUtils.isEmpty(mEditDebtAmount.getText())) {

            Toast.makeText(this, "Please enter data to all fields", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick:  RESULT CANCELLED");
            return;
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_NAME, name);
        replyIntent.putExtra(EXTRA_AMOUNT, debtAmount);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            replyIntent.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, replyIntent);

        Log.d(TAG, "onClick:  RESULT OK");
        finish();


    }
}
