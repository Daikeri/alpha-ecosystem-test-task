package com.example.bininfo

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinInfoRepository @Inject constructor(
    val binInfoRds: BinlistApi
) {
    private val cashedBin: MutableMap<String, BinlistResponse> = mutableMapOf()

    suspend fun getBinInfo(bin: String): BinlistResponse {
        return cashedBin.getOrPut(bin) {
            with(Dispatchers.IO) {
                binInfoRds.getBinInfo(bin)
            }
        }
    }
}