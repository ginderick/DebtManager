package com.example.debtmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.debtmanager.adapter.DebtListAdapter;

import com.example.debtmanager.model.DebtInfo;
import com.example.debtmanager.viewmodel.DebtInfoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements DebtListAdapter.OnDeleteButtonClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int ADD_DEBT_ACTIVITY_REQUEST_CODE = 1;

    private DebtInfoViewModel debtInfoViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DebtListAdapter debtListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton buttonAddDebt = findViewById(R.id.button_add_debt);
        buttonAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDebtActivity.class);
                startActivityForResult(intent, ADD_DEBT_ACTIVITY_REQUEST_CODE);
            }
        });

        debtInfoViewModel = ViewModelProviders.of(this).get(DebtInfoViewModel.class);
        Disposable disposable = debtInfoViewModel.getAllDebtInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DebtInfo>>() {
                    @Override
                    public void accept(List<DebtInfo> debtInfo) throws Exception {
                        debtListAdapter = new DebtListAdapter(debtInfo, MainActivity.this);
                        recyclerView.setAdapter(debtListAdapter);

                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DEBT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddDebtActivity.EXTRA_NAME);
            String amount = data.getStringExtra(AddDebtActivity.EXTRA_AMOUNT);
            int debtAmount = Integer.parseInt(amount);

            DebtInfo debtInfo = new DebtInfo(name, debtAmount);
            debtInfoViewModel.insert(debtInfo);

            Toast.makeText(this, "debt saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "debt not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteButtonClicked(DebtInfo debtInfo) {
        debtInfoViewModel.delete(debtInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }


}
