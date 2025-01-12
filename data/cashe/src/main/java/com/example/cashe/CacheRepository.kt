package com.example.cashe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinCacheRepository @Inject constructor(
    private val dao: BinCacheDao
) {

    // Получение всех объектов BinCache
    fun getAllBinCaches(): Flow<List<BinCache>> {
        return dao.getAllBinCaches()
    }

    // Добавление нового объекта BinCache
    suspend fun addBinCache(binCache: BinCache) {
        dao.insertBinCache(binCache)
    }

    // Удаление объекта BinCache
    suspend fun deleteBinCache(binCache: BinCache) {
        dao.deleteBinCache(binCache)
    }
}