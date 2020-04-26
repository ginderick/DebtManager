package com.example.debtmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.debtmanager.model.DebtInfo;
import com.example.debtmanager.repository.DebtInfoRepository;

import java.util.List;

import io.reactivex.Flowable;


public class DebtInfoViewModel extends AndroidViewModel {

    private DebtInfoRepository debtInfoRepository;

    public DebtInfoViewModel(@NonNull Application application) {
        super(application);
        debtInfoRepository = new DebtInfoRepository(application);
    }

    //get all debt info
    public Flowable<List<DebtInfo>> getAllDebtInfo() {
        return debtInfoRepository.getAllDebtInfo();
    }

    public void insert(DebtInfo debtInfo) {
        debtInfoRepository.insertDebtInfo(debtInfo);
    }

    public void delete(DebtInfo debtInfo) {
        debtInfoRepository.deleteDebtInfo(debtInfo);
    }

    public void update(DebtInfo debtInfo) {
        debtInfoRepository.updateDebtInfo(debtInfo);
    }
}
