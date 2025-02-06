package com.shruti.todoroomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubTaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val name : String ?= "",
    val todoId : Int ?= 0)
