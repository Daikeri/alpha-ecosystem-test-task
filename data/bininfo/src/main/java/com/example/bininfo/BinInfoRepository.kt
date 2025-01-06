package com.example.bininfo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinInfoRepository @Inject constructor(
    val binInfoRds: BinlistApi
) {
    private val cashedBin: MutableMap<String, BinlistResponse> = mutableMapOf()

    suspend fun getBinInfo(bin: String) {
        cashedBin.getOrPut(bin) { binInfoRds.getBinInfo(bin) }
    }
}