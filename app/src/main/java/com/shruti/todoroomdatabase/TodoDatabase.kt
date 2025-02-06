package com.shruti.todoroomdatabase

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(version = 2, entities = [TodoEntity::class, SubTaskEntity::class], exportSchema = true)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        private var todoDatabase: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            if (todoDatabase == null) {
                todoDatabase = Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java,
                    context.resources.getString(R.string.app_name),
                ).addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build()

            }
            return todoDatabase!!
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
