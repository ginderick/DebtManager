package com.example.debtmanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.TypeConverters;

import com.example.debtmanager.utils.DateConverter;

import java.util.Date;

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
    private Date dueDate;

    @ColumnInfo(name = "createdDate")
    @TypeConverters({DateConverter.class})
    private Date createdDate;

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

    public Date getDueDate() {
        return dueDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
