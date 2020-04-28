package com.example.debtmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.debtmanager.R;
import com.example.debtmanager.view.fragment.DatePickerFragment;

public class DebtActivity extends AppCompatActivity {

    public static final String TAG = DebtActivity.class.getSimpleName();
    public static final String EXTRA_ID = "com.example.debtmanager.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.debtmanager.EXTRA_NAME";
    public static final String EXTRA_AMOUNT = "com.example.debtmanager.EXTRA_AMOUNT";
    public static final String EXTRA_DESCRIPTION = "com.example.debtmanager.EXTRA_DESCRIPTION";

    private EditText mEditName;
    private EditText mEditDebtAmount;
    private EditText mEditDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);

        mEditName = findViewById(R.id.edit_name);
        mEditDebtAmount = findViewById(R.id.edit_debt_amount);
        mEditDescription = findViewById(R.id.edit_debt_description);


        Button button = findViewById(R.id.date_created_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: " + intent.getStringExtra(EXTRA_NAME));
        Log.d(TAG, "onCreate: " + intent.getStringExtra(EXTRA_AMOUNT));
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Info");
            mEditName.setText(intent.getStringExtra(EXTRA_NAME));
            mEditDebtAmount.setText(intent.getStringExtra(EXTRA_AMOUNT));
            mEditDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add Debt");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_debt_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_debt_button:
                saveDebtInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveDebtInfo() {

        String name = mEditName.getText().toString();
        String debtAmount = mEditDebtAmount.getText().toString();
        String description = mEditDescription.getText().toString();


        if (TextUtils.isEmpty(mEditName.getText()) || TextUtils.isEmpty(mEditDebtAmount.getText())) {

            Toast.makeText(this, "Please enter data to all required fields", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick:  RESULT CANCELLED");
            return;
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_NAME, name);
        replyIntent.putExtra(EXTRA_AMOUNT, debtAmount);
        replyIntent.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            replyIntent.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, replyIntent);

        Log.d(TAG, "onClick:  RESULT OK");

        Toast.makeText(this, "Info saved", Toast.LENGTH_SHORT);
        finish();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
