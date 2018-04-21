package com.playone.mobile.remote

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.remote.mapper.BufferooEntityMapper
import com.playone.mobile.remote.test.factory.BufferooFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.modelmapper.ModelMapper
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BufferooRemoteImplTest {

    private lateinit var entityMapper: BufferooEntityMapper
    private lateinit var bufferooService: BufferooService

    private lateinit var bufferooRemoteImpl: BufferooRemoteImpl

    @Before
    fun setup() {
        entityMapper = BufferooEntityMapper(ModelMapper())
        bufferooService = mock()
        bufferooRemoteImpl = BufferooRemoteImpl(bufferooService, entityMapper)
    }

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        stubBufferooServiceGetBufferoos(Single.just(BufferooFactory.makeBufferooResponse()))
        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooResponse = BufferooFactory.makeBufferooResponse()
        stubBufferooServiceGetBufferoos(Single.just(bufferooResponse))
        val bufferooEntities = mutableListOf<BufferooEntity>()
        bufferooResponse.team.forEach {
            bufferooEntities.add(entityMapper.mapToData(it))
        }

        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertValue {
            it.forEachIndexed { index, entity ->
                assertEquals(entity.name, bufferooEntities[index].name)
                assertEquals(entity.title, bufferooEntities[index].title)
                assertEquals(entity.avatar, bufferooEntities[index].avatar)
            }
            true
        }
    }
    //</editor-fold>

    private fun stubBufferooServiceGetBufferoos(single: Single<BufferooService.BufferooResponse>) {
        whenever(bufferooService.getBufferoos())
                .thenReturn(single)
    }
}