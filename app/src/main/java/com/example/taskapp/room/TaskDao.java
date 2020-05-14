package com.example.taskapp.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task")
    LiveData<List<Task>>getAllLive();


    @Query("UPDATE task Set title = :newTitle, `desc` = :newDesc WHERE id IN (:idList)")
    void update(int idList ,String newTitle, String newDesc);


    @Query("DELETE from task WHERE id IN (:idList)")
    void deleteByIdList(int idList);

    @Insert
    void insert(Task task);

}
