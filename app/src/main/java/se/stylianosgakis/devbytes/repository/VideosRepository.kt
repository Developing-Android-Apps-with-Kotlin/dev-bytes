package se.stylianosgakis.devbytes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import se.stylianosgakis.devbytes.database.VideosDatabase
import se.stylianosgakis.devbytes.database.asDomainModel
import se.stylianosgakis.devbytes.domain.Video
import se.stylianosgakis.devbytes.network.Network
import se.stylianosgakis.devbytes.network.asDatabaseModel

class VideosRepository(
    private val database: VideosDatabase
) {
    val videosLiveData: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylistAsync().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}