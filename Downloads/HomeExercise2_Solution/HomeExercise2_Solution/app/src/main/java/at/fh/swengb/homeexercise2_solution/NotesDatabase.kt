package at.fh.swengb.homeexercise2_solution

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Note::class,Student::class], version = 9)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao
    abstract val studentDao: StudentDao

    companion object {
        private var INSTANCE: NotesDatabase? = null


        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
            }
        }

        private fun buildDatabase(context: Context): NotesDatabase {
            return Room.databaseBuilder(context, NotesDatabase::class.java, "notes-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
