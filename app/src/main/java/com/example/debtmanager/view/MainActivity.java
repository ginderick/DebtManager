package com.example.debtmanager.view;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;


import com.example.debtmanager.R;

import com.example.debtmanager.view.fragment.DebtFragment;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Main Activity called");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, DebtFragment.newInstance())
                .commitNow();
    }

}
