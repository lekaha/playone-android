package com.playone.mobile.cache.db.mapper

/**
 * Maps a [CachedPlayone] instance to a database entity.
 */
class PlayoneMapper
//    : ModelDbMapper<CachedBufferoo> {
//
//    /**
//     * Construct an instance of [ContentValues] using the given [CachedBufferoo]
//     */
//    override fun toContentValues(model: CachedBufferoo): ContentValues {
//        val values = ContentValues()
//        values.put(Db.BufferooTable.NAME, model.name)
//        values.put(Db.BufferooTable.TITLE, model.title)
//        values.put(Db.BufferooTable.AVATAR, model.avatar)
//        return values
//    }
//
//    /**
//     * Parse the cursor creating a [CachedBufferoo] instance.
//     */
//    override fun parseCursor(cursor: Cursor): CachedBufferoo {
//        val name = cursor.getString(cursor.getColumnIndexOrThrow(Db.BufferooTable.NAME))
//        val title = cursor.getString(cursor.getColumnIndexOrThrow(Db.BufferooTable.TITLE))
//        val avatar = cursor.getString(cursor.getColumnIndexOrThrow(Db.BufferooTable.AVATAR))
//        return CachedBufferoo().apply {
//            this.name = name
//            this.title = title
//            this.avatar = avatar
//        }
//    }
//
//}