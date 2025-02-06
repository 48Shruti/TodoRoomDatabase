package com.shruti.todoroomdatabase

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(version = 2, entities = [TodoEntity::class, SubTaskEntity::class], exportSchema = true)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                )
                    .addMigrations(MIGRATION_1_2) // Add migration for schema changes
                    .allowMainThreadQueries() // Not recommended, but keeping it as per your code
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migration from version 1 to version 2 (modify as needed)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example: Add a new column in TodoEntity (modify according to your changes)
                database.execSQL("ALTER TABLE TodoEntity ADD COLUMN new_column TEXT DEFAULT ''")
            }
        }
    }
}
