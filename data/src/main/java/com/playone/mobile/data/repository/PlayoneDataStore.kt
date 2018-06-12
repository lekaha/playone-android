package com.playone.mobile.data.repository

/**
 * Interface defining methods for the data operations related to
 * [com.playone.mobile.data.model.PlayoneItem]. This is to be implemented by external
 * data source layers, setting the requirements for the operations that need to
 * be implemented.
 */
interface PlayoneDataStore : PlayoneCache, PlayoneRemote