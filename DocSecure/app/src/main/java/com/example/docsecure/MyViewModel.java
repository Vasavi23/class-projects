package com.example.docsecure;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Pass>> getAllData;
    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        getAllData=repository.readALLData();
    }
    //This is for insert Method
    public void insert(Pass password){
        repository.insert(password);
    }
    //This is for update Method
    public void update(Pass password){
        repository.update(password);
    }
    //This is for delete Method
    public void delete(Pass password){
        repository.delete(password);
    }
    //Read all LiveData
    public LiveData<List<Pass>> readData(){
        return getAllData;
    }

}