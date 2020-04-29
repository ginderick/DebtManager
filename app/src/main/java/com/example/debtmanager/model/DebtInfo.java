package com.example.debtmanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.TypeConverters;

import com.example.debtmanager.utils.DateConverter;

@Entity(tableName = "info_table")
public class DebtInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "debtAmount")
    private int debtAmount;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "dueDate")
    @TypeConverters({DateConverter.class})
    private long dueDate;

    @ColumnInfo(name = "createdDate")
    @TypeConverters({DateConverter.class})
    private long createdDate;

    public DebtInfo(String name, int debtAmount, String description) {
        this.name = name;
        this.debtAmount = debtAmount;
        this.description = description;
    }

    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public int getDebtAmount() {
        return debtAmount;
    }

    public String getDescription() {
        return description;
    }

    public long getDueDate() {
        return dueDate;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
