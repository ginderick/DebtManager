package com.example.debtmanager.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "info_table")
public class DebtInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "debtAmount")
    private int debtAmount;

    public DebtInfo(String name, int debtAmount) {
        this.id = id;
        this.name = name;
        this.debtAmount = debtAmount;
    }

    public String getName() {
        return name;
    }

    public int getDebtAmount() {
        return debtAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
