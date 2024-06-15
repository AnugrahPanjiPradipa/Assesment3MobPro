package id.Panji.Assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.Panji.Assesment2.model.Nominal

@Database(entities = [Nominal::class], version = 4, exportSchema = false)
abstract class NominalDb : RoomDatabase() {
    abstract val dao: NominalDao

    companion object {
        @Volatile
        private var INSTANCE: NominalDb? = null

        fun getInstance(context: Context): NominalDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NominalDb::class.java,
                        "nominal.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}