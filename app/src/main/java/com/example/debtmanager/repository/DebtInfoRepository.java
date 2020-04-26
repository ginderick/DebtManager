package com.example.debtmanager.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.debtmanager.dao.DebtDao;
import com.example.debtmanager.database.DebtRoomDatabase;
import com.example.debtmanager.model.DebtInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class DebtInfoRepository {
    
    public static final String TAG = DebtInfoRepository.class.getSimpleName();

    private DebtDao mDebtDao;
    private Flowable<List<DebtInfo>> mDebtInfo;

    //constructor to get a handle on the database and initializes the member variables
    public DebtInfoRepository(Application application) {
        DebtRoomDatabase debtRoomDatabase = DebtRoomDatabase.getDatabase(application);
        mDebtDao = debtRoomDatabase.debtDao();
        mDebtInfo = mDebtDao.getAllDebtInfo();
    }



    //Get all DebtInfo
    public Flowable<List<DebtInfo>> getAllDebtInfo() {
        return mDebtDao.getAllDebtInfo();
    }

    //Insert DebtInfo
    public void insertDebtInfo(final DebtInfo debtInfo) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDebtDao.insertDebt(debtInfo);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                    }
                });
    }

    public void deleteDebtInfo(DebtInfo debtInfo) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDebtDao.deleteDebt(debtInfo);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public void updateDebtInfo(DebtInfo debtInfo) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDebtDao.updateDebt(debtInfo);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }
}
