package com.shruti.todoroomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TodoId")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = ""
)


