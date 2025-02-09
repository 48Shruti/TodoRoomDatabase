package com.shruti.todoroomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TodoDao {
    @Insert
    fun insertTodo(todoEntity: TodoEntity)
    @Query ("SELECT * FROM Todo")
    fun getTodo(): List<TodoEntity>
    @Update
    fun updateTodo(todoEntity: TodoEntity)
    @Delete
    fun deleteTodo(todoEntity: TodoEntity)
    @Insert
    fun insertSub(subTaskEntity: SubTaskEntity)
    @Query("SELECT * FROM SubTask where todoId = :todoId")
    fun getSub(todoId : Int): List<SubTaskEntity>
    @Update
    fun updateSub(subTaskEntity: SubTaskEntity)
    @Delete
    fun deleteSub(subTaskEntity: SubTaskEntity)

}