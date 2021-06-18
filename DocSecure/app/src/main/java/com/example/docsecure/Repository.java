package com.example.docsecure;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    Password_database database;
    LiveData<List<Pass>> live_list;
    public Repository(Application application) {
        database=Password_database.getDatabase(application);
        live_list=database.myDao().read_data();
    }
    //Methods for tasks
    public void insert(Pass pass){
        new InsertTask().execute(pass);
    }
    public LiveData<List<Pass>> readALLData(){
        return live_list;
    }
    public void update(Pass pass){
        new UpdateTask().execute(pass);
    }
    public void delete(Pass pass){
        new DeleteTask().execute(pass);
    }
    //Tasks Classes
    class InsertTask extends AsyncTask<Pass,Void,Void>{
        @Override
        protected Void doInBackground(Pass... passes) {
            database.myDao().insert(passes[0]);
            return null;
        }
    }
    class UpdateTask extends AsyncTask<Pass,Void,Void>{
        @Override
        protected Void doInBackground(Pass... passes) {
            database.myDao().update(passes[0]);
            return null;
        }
    }
    class DeleteTask extends AsyncTask<Pass,Void,Void>{
        @Override
        protected Void doInBackground(Pass... passes) {
            database.myDao().delete(passes[0]);
            return null;
        }
    }


}