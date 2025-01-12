package com.example.cashe
import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinCacheRepository @Inject constructor(
    val dao: BinCacheDao
) {

    // Получение всех объектов BinCache
    fun getAllBinCaches(): Flow<List<BinCache>> {
        return dao.getAllBinCaches()
    }

    // Добавление нового объекта BinCache
    suspend fun addBinCache(binCache: BinCache) {
        Log.e("add new bin", "$binCache")
        dao.insertBinCache(binCache)
    }

    // Удаление объекта BinCache
    suspend fun deleteBinCache(binCache: BinCache) {
        dao.deleteBinCache(binCache)
    }
}