package com.example.debtmanager.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.debtmanager.R;
import com.example.debtmanager.utils.DateConverter;

import java.util.Calendar;

public class DebtActivity extends AppCompatActivity{

    public static final String TAG = DebtActivity.class.getSimpleName();
    public static final String EXTRA_ID = "com.example.debtmanager.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.debtmanager.EXTRA_NAME";
    public static final String EXTRA_AMOUNT = "com.example.debtmanager.EXTRA_AMOUNT";
    public static final String EXTRA_DESCRIPTION = "com.example.debtmanager.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE_CREATE = "com.example.debtmanager.EXTRA_DATE_CREATE";
    public static final String EXTRA_DATE_DUE = "com.example.debtmanager.EXTRA_DATE_DUE";

    private EditText mEditName;
    private EditText mEditDebtAmount;

    private EditText mEditDescription;
    private Button mCreateDateButton;
    private Button mDueDateButton;

    private long mCreateDate;
    private long mDueDate;

    private Calendar mCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);
        Log.d(TAG, "onCreate: called");

        mEditName = findViewById(R.id.edit_name);
        mEditDebtAmount = findViewById(R.id.edit_debt_amount);
        mEditDescription = findViewById(R.id.edit_debt_description);
        mCreateDateButton= findViewById(R.id.date_created_button);
        mDueDateButton = findViewById(R.id.date_due_button);

        //Set createDate and due dueDate to current date
        mCreateDate = System.currentTimeMillis();
        mDueDate = System.currentTimeMillis();

        //Set Button to current date
        mCreateDateButton.setText(String.format("%s%s", getString(R.string.created_date), DateConverter.millisToString(mCreateDate)));
        mCreateDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view, mCreateDateButton.getId());

            }
        });

        mDueDateButton.setText(String.format("%s%s", getString(R.string.due_date), DateConverter.millisToString(mDueDate)));
        mDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view, mDueDateButton.getId());

            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Info");
            mEditName.setText(intent.getStringExtra(EXTRA_NAME));
            mEditDebtAmount.setText(intent.getStringExtra(EXTRA_AMOUNT));
            mEditDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            mCreateDateButton.setText(String.format("%s%s", getString(R.string.created_date), DateConverter.millisToString(intent.getLongExtra(EXTRA_DATE_CREATE, -1))));
            mDueDateButton.setText(String.format("%s%s", getString(R.string.due_date), DateConverter.millisToString(intent.getLongExtra(EXTRA_DATE_DUE, -1))));
            mCreateDate = intent.getLongExtra(EXTRA_DATE_CREATE, -1);
            mDueDate = intent.getLongExtra(EXTRA_DATE_DUE, -1);

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
        long createDAte = mCreateDate;
        if (TextUtils.isEmpty(mEditName.getText()) || TextUtils.isEmpty(mEditDebtAmount.getText())) {

            Toast.makeText(this, "Please enter data to all required fields", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick:  RESULT CANCELLED");
            return;
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_NAME, name);
        replyIntent.putExtra(EXTRA_AMOUNT, debtAmount);
        replyIntent.putExtra(EXTRA_DESCRIPTION, description);
        replyIntent.putExtra(EXTRA_DATE_CREATE, mCreateDate);
        replyIntent.putExtra(EXTRA_DATE_DUE, mDueDate);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            replyIntent.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, replyIntent);

        Log.d(TAG, "onClick:  RESULT OK");

        Toast.makeText(this, "Info saved", Toast.LENGTH_SHORT);
        finish();
    }

    public void showDatePickerDialog(View v, int buttonId) {

        int mYear, mMonth, mDayOfMonth;

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mCalendar.set(year, month, dayOfMonth);

                String dateInString = DateConverter.dateToString(year, month, dayOfMonth);

                if (buttonId == R.id.date_created_button) {
                    mCreateDateButton.setText(String.format("%s%s", getString(R.string.created_date), dateInString));
                    mCreateDate = mCalendar.getTimeInMillis();
                    Log.d(TAG, "onDateSet: millis2String created date " + DateConverter.millisToString(mCreateDate));
                } else if (buttonId == R.id.date_due_button) {
                    mDueDateButton.setText(String.format("%s%s", getString(R.string.due_date), dateInString));
                    mDueDate = mCalendar.getTimeInMillis();
                    Log.d(TAG, "onDateSet: millis2String due date " + DateConverter.millisToString(mDueDate));
                }
            }
        }, mYear, mMonth, mDayOfMonth);
        datePickerDialog.show();
    }

}
