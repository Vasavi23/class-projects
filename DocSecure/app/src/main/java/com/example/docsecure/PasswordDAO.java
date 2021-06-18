package com.example.docsecure;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDAO {
    @Insert
    public void insert(Pass password);
    @Query("SELECT * FROM Password_data")
    //public List<Student> read_data();
    public LiveData<List<Pass>> read_data();

    @Delete
    public void delete(Pass password);

    @Update
    public void update(Pass password);


}