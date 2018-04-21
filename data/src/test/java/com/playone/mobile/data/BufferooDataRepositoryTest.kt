package com.playone.mobile.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.playone.mobile.data.mapper.BufferooMapper
import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.data.repository.BufferooDataStore
import com.playone.mobile.data.source.BufferooCacheDataStore
import com.playone.mobile.data.source.BufferooDataStoreFactory
import com.playone.mobile.data.source.BufferooRemoteDataStore
import com.playone.mobile.data.test.factory.BufferooFactory
import com.playone.mobile.domain.model.Bufferoo
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BufferooDataRepositoryTest {

    private lateinit var bufferooDataRepository: BufferooDataRepository

    private lateinit var bufferooDataStoreFactory: BufferooDataStoreFactory
    private lateinit var bufferooMapper: BufferooMapper
    private lateinit var bufferooCacheDataStore: BufferooCacheDataStore
    private lateinit var bufferooRemoteDataStore: BufferooRemoteDataStore

    @Before
    fun setUp() {
        bufferooDataStoreFactory = mock()
        bufferooMapper = mock()
        bufferooCacheDataStore = mock()
        bufferooRemoteDataStore = mock()
        bufferooDataRepository = BufferooDataRepository(bufferooDataStoreFactory, bufferooMapper)
        stubBufferooDataStoreFactoryRetrieveCacheDataStore()
        stubBufferooDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Bufferoos">
    @Test
    fun clearBufferoosCompletes() {
        stubBufferooCacheClearBufferoos(Completable.complete())
        val testObserver = bufferooDataRepository.clearBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearBufferoosCallsCacheDataStore() {
        stubBufferooCacheClearBufferoos(Completable.complete())
        bufferooDataRepository.clearBufferoos().test()
        verify(bufferooCacheDataStore).clearBufferoos()
    }

    @Test
    fun clearBufferoosNeverCallsRemoteDataStore() {
        stubBufferooCacheClearBufferoos(Completable.complete())
        bufferooDataRepository.clearBufferoos().test()
        verify(bufferooRemoteDataStore, never()).clearBufferoos()
    }
    //</editor-fold>

    //<editor-fold desc="Save Bufferoos">
    @Test
    fun saveBufferoosCompletes() {
        stubBufferooCacheSaveBufferoos(Completable.complete())
        val testObserver = bufferooDataRepository.saveBufferoos(
                BufferooFactory.makeBufferooList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBufferoosCallsCacheDataStore() {
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooCacheDataStore).saveBufferoos(any())
    }

    @Test
    fun saveBufferoosNeverCallsRemoteDataStore() {
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooRemoteDataStore, never()).saveBufferoos(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooCacheDataStore)
        stubBufferooCacheDataStoreGetBufferoos(Single.just(
                BufferooFactory.makeBufferooEntityList(2)))
        val testObserver = bufferooDataRepository.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooCacheDataStore)
        val bufferoos = BufferooFactory.makeBufferooList(2)
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)
        bufferoos.forEachIndexed { index, bufferoo ->
            stubBufferooMapperMapFromEntity(bufferooEntities[index], bufferoo) }
        stubBufferooCacheDataStoreGetBufferoos(Single.just(bufferooEntities))

        val testObserver = bufferooDataRepository.getBufferoos().test()
        testObserver.assertValue(bufferoos)
    }

    @Test
    fun getBufferoosSavesBufferoosWhenFromCacheDataStore() {
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooCacheDataStore)
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooCacheDataStore).saveBufferoos(any())
    }

    @Test
    fun getBufferoosNeverSavesBufferoosWhenFromRemoteDataStore() {
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooRemoteDataStore)
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooRemoteDataStore, never()).saveBufferoos(any())
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubBufferooCacheSaveBufferoos(completable: Completable) {
        whenever(bufferooCacheDataStore.saveBufferoos(any()))
                .thenReturn(completable)
    }

    private fun stubBufferooCacheDataStoreGetBufferoos(single: Single<List<BufferooEntity>>) {
        whenever(bufferooCacheDataStore.getBufferoos())
                .thenReturn(single)
    }

    private fun stubBufferooRemoteDataStoreGetBufferoos(single: Single<List<BufferooEntity>>) {
        whenever(bufferooRemoteDataStore.getBufferoos())
                .thenReturn(single)
    }

    private fun stubBufferooCacheClearBufferoos(completable: Completable) {
        whenever(bufferooCacheDataStore.clearBufferoos())
                .thenReturn(completable)
    }

    private fun stubBufferooDataStoreFactoryRetrieveCacheDataStore() {
        whenever(bufferooDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(bufferooCacheDataStore)
    }

    private fun stubBufferooDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(bufferooDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(bufferooCacheDataStore)
    }

    private fun stubBufferooDataStoreFactoryRetrieveDataStore(dataStore: BufferooDataStore) {
        whenever(bufferooDataStoreFactory.retrieveDataStore())
                .thenReturn(dataStore)
    }

    private fun stubBufferooMapperMapFromEntity(bufferooEntity: BufferooEntity,
                                                bufferoo: Bufferoo) {
        whenever(bufferooMapper.mapFromEntity(bufferooEntity))
                .thenReturn(bufferoo)
    }
    //</editor-fold>

}