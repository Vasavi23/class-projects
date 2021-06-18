package com.example.docsecure;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pass.class},version = 1,exportSchema = false)
public abstract class Password_database extends RoomDatabase {

    public abstract PasswordDAO myDao();
    public static Password_database database;
    public static String databasename;

    //Live data
    public static synchronized Password_database getDatabase(Context context){
        if(database==null){

            //create database
            database = Room.databaseBuilder(context, Password_database.class,""+databasename)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        //else return database
        return database;
    }

}