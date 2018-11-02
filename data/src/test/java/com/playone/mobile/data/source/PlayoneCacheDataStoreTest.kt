package com.playone.mobile.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.test.factory.Factory
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleJust
import org.junit.Before
import org.junit.Test

class PlayoneCacheDataStoreTest {

    private lateinit var playoneCacheDataStore: PlayoneCacheDataStore
    private lateinit var playoneCache: PlayoneCache

    @Before
    fun setUp() {

        playoneCache = mock()
        playoneCacheDataStore = PlayoneCacheDataStore(playoneCache)
    }

    @Test
    fun clearPlayoneList() {

        stubPlayoneCacheClearPlayoneList(Completable.complete())
        playoneCacheDataStore.clearPlayoneList().test().assertComplete()
    }

    @Test
    fun savePlayoneList() {

        stubPlayoneCacheSavePlayoneList(Completable.complete())
        playoneCacheDataStore
            .savePlayoneList(Factory.makePlayoneList(4))
            .test()
            .assertComplete()
    }

    @Test
    fun getPlayoneList() {

        stubPlayoneCacheGetPlayoneList(Single.just(Factory.makePlayoneList(2)))
        playoneCacheDataStore.getPlayoneList().test().assertComplete()
    }

    @Test
    fun clearJoinedPlayoneList() {

        whenever(playoneCache.clearJoinedPlayoneList()).thenReturn(Completable.complete())
        playoneCacheDataStore.clearJoinedPlayoneList().test().assertComplete()
    }

    @Test
    fun saveJoinedPlayoneList() {

        whenever(playoneCache.saveJoinedPlayoneList(any())).thenReturn(Completable.complete())
        playoneCacheDataStore
            .saveJoinedPlayoneList(Factory.makePlayoneList(2))
            .test()
            .assertComplete()
    }

    @Test
    fun getJoinedPlayoneList() {

        whenever(playoneCache.getJoinedPlayoneList(2))
            .thenReturn(SingleJust(Factory.makePlayoneList(3)))
        playoneCacheDataStore.getJoinedPlayoneList(2).test().assertComplete()
    }

    private fun stubPlayoneCacheSavePlayoneList(completable: Completable) {

        whenever(playoneCache.savePlayoneList(any())).thenReturn(completable)
    }

    private fun stubPlayoneCacheClearPlayoneList(completable: Completable) {

        whenever(playoneCache.clearPlayoneList()).thenReturn(completable)
    }

    private fun stubPlayoneCacheGetPlayoneList(single: Single<List<PlayoneEntity>>) {

        whenever(playoneCache.getPlayoneList()).thenReturn(single)
    }
}