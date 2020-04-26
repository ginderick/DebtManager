package com.example.debtmanager.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.debtmanager.R;
import com.example.debtmanager.adapter.DebtListAdapter;
import com.example.debtmanager.model.DebtInfo;
import com.example.debtmanager.view.DebtActivity;
import com.example.debtmanager.view.MainActivity;
import com.example.debtmanager.viewmodel.DebtInfoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class DebtFragment extends Fragment implements DebtListAdapter.OnClickListener{
    
    public static final String TAG = "DebtFragment";

    public static final int ADD_DEBT_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_DEBT_ACTIVITY_REQUEST_CODE = 2;

    private DebtInfoViewModel debtInfoViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DebtListAdapter debtListAdapter;
    private ArrayList<DebtInfo> mDebts = new ArrayList<>();
    private RecyclerView recyclerView;


    public DebtFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: DebtFragment called");

        View view = inflater.inflate(R.layout.fragment_debt, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton buttonAddDebt = view.findViewById(R.id.fab_add_debt);
        buttonAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DebtActivity.class);
                startActivityForResult(intent, ADD_DEBT_ACTIVITY_REQUEST_CODE);
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: DebtFragment Called");

        if (requestCode == ADD_DEBT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(DebtActivity.EXTRA_NAME);
            String amount = data.getStringExtra(DebtActivity.EXTRA_AMOUNT);
            int debtAmount = Integer.parseInt(amount);

            DebtInfo debtInfo = new DebtInfo(name, debtAmount);
            debtInfoViewModel.insert(debtInfo);

            Toast.makeText(getActivity(), "debt saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_DEBT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(DebtActivity.EXTRA_ID, -1);
            String name = data.getStringExtra(DebtActivity.EXTRA_NAME);
            String amount = data.getStringExtra(DebtActivity.EXTRA_AMOUNT);
            int debtAmount = Integer.parseInt(amount);
            DebtInfo debtInfo = new DebtInfo(name, debtAmount);
            debtInfo.setId(id);
            debtInfoViewModel.update(debtInfo);

        } else {
            Toast.makeText(getActivity(), "debt not saved", Toast.LENGTH_SHORT).show();
        }
    }

    public static DebtFragment newInstance() {
        return new DebtFragment();
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: DebtFragment Called");

        debtInfoViewModel = ViewModelProviders.of(this).get(DebtInfoViewModel.class);
        Disposable disposable = debtInfoViewModel.getAllDebtInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DebtInfo>>() {
                    @Override
                    public void accept(List<DebtInfo> debtInfo) throws Exception {
                        debtListAdapter = new DebtListAdapter(debtInfo, DebtFragment.this);
                        debtListAdapter.setDebtInfoList(debtInfo);
                        recyclerView.setAdapter(debtListAdapter);

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDeleteButtonClicked(DebtInfo debtInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attention")
                .setMessage("Are you sure to delete it?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        debtInfoViewModel.delete(debtInfo);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    @Override
    public void onEditClicked(DebtInfo debtInfo) {

        Log.d(TAG, "onEditClicked: DebtFragment Called");
        Intent intent = new Intent(getActivity(), DebtActivity.class);
        intent.putExtra(DebtActivity.EXTRA_ID, debtInfo.getId());
        intent.putExtra(DebtActivity.EXTRA_NAME, debtInfo.getName());
        intent.putExtra(DebtActivity.EXTRA_AMOUNT, String.valueOf(debtInfo.getDebtAmount()));
        startActivityForResult(intent, EDIT_DEBT_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
