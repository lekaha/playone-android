package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to
 * [com.playone.mobile.data.model.PlayoneItem]. This is to be implemented by external
 * data source layers, setting the requirements for the operations that need to
 * be implemented.
 */
interface PlayoneDataStore {

    fun clearPlayoneList(): Completable

    fun savePlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getPlayoneList(): Single<List<PlayoneEntity>>
}