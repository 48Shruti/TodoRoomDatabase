package com.shruti.todoroomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "SubTask",
    foreignKeys = [ForeignKey(
        entity = TodoEntity::class,
        parentColumns = ["TodoId"],
        childColumns = ["todoId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SubTaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SubTaskId")
    val subTaskId: Int = 0,

    @ColumnInfo(name = "todoId")
    val todoId: Int,

    @ColumnInfo(name = "SubTaskName")
    val subTaskName: String,
    @ColumnInfo(name = "Completed")
    var completed : Boolean = false,
    var date : String ?= ""
)
