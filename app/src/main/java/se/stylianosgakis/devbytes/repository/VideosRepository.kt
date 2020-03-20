package se.stylianosgakis.devbytes.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import se.stylianosgakis.devbytes.database.VideosDatabase
import se.stylianosgakis.devbytes.network.Network
import se.stylianosgakis.devbytes.network.asDatabaseModel

class VideosRepository(
    private val database: VideosDatabase
) {
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylistAsync().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}