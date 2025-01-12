package com.example.cashe

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Entity(tableName = "bin_cache_table")
data class BinCache(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bin: String,
    val time: String,
    val paymentSystem: String? = null,
    val brand: String? = null,
    val countryName: String? = null,
    val countryLatitude: String? = null,
    val countryLongitude: String? = null,
    val bankName: String? = null,
    val bankUrl: String? = null,
    val bankPhone: String? = null,
    val bankCity: String? = null,
    val cardTypeCategory: String? = null,
    val isPrepaidCategory: Boolean? = null
)

@Dao
interface BinCacheDao {
    @Query("SELECT * FROM bin_cache_table")
    fun getAllBinCaches(): Flow<List<BinCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBinCache(binCache: BinCache)

    @Delete
    suspend fun deleteBinCache(binCache: BinCache)
}

@Database(entities = [BinCache::class], version = 3) // Обновлена версия базы данных
abstract class AppDatabase : RoomDatabase() {
    abstract fun binCacheDao(): BinCacheDao
}

// Инициализация (с Hilt):
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "bin_cache_db")
            .fallbackToDestructiveMigration() // Это для упрощения миграций
            .build()
    }

    @Provides
    fun provideBinCacheDao(database: AppDatabase): BinCacheDao {
        return database.binCacheDao()
    }
}

