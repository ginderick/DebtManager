package com.example.debtmanager.dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.debtmanager.model.DebtInfo;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface DebtDao {

    @Insert

    void insertDebt(DebtInfo debtInfo);

    @Delete
    void deleteDebt(DebtInfo debtInfo);

    //Use LiveData to respond to data changes
    @Query("SELECT * FROM info_table")
    Flowable<List<DebtInfo>> getAllDebtInfo();
}
