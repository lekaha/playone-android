package com.playone.mobile.cache.db.mapper

import android.database.Cursor
import com.playone.mobile.cache.BuildConfig
import com.playone.mobile.cache.db.Db
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.model.CachedBufferoo
import com.playone.mobile.cache.test.DefaultConfig
import com.playone.mobile.cache.test.factory.BufferooFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class BufferooMapperTest {

    private lateinit var bufferooMapper: BufferooMapper
    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase

    @Before
    fun setUp() {
        bufferooMapper = BufferooMapper()
    }

    @Test
    fun parseCursorMapsData() {
        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
        insertCachedBufferoo(cachedBufferoo)

        val cursor = retrieveCachedBufferooCursor()
        assertEquals(cachedBufferoo.name, bufferooMapper.parseCursor(cursor).name)
        assertEquals(cachedBufferoo.title, bufferooMapper.parseCursor(cursor).title)
        assertEquals(cachedBufferoo.avatar, bufferooMapper.parseCursor(cursor).avatar)
    }

    private fun retrieveCachedBufferooCursor(): Cursor {
        val cursor = database.rawQuery("SELECT * FROM " + Db.BufferooTable.TABLE_NAME, null)
        cursor.moveToFirst()
        return cursor
    }

    private fun insertCachedBufferoo(cachedBufferoo: CachedBufferoo) {
        database.insertOrThrow(Db.BufferooTable.TABLE_NAME, null,
                               bufferooMapper.toContentValues(cachedBufferoo))
    }

}