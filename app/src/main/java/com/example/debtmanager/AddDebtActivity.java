package com.example.debtmanager;

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

import org.w3c.dom.Text;

public class AddDebtActivity extends AppCompatActivity {

    public static final String TAG = AddDebtActivity.class.getSimpleName();

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
    }

    private void saveDebtInfo() {

        Intent replyIntent = new Intent();

        if (TextUtils.isEmpty(mEditName.getText()) || TextUtils.isEmpty(mEditDebtAmount.getText())) {
            setResult(RESULT_CANCELED, replyIntent);

            Toast.makeText(this, "Please enter data to all fields", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick:  RESULT CANCELLED");
            return;
        } else {

            String name = mEditName.getText().toString();
            String debt = mEditDebtAmount.getText().toString();

            replyIntent.putExtra(EXTRA_NAME, name);
            replyIntent.putExtra(EXTRA_AMOUNT, debt);
            setResult(RESULT_OK, replyIntent);

            Log.d(TAG, "onClick:  RESULT OK");
            finish();
        }

    }
}
