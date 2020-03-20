package se.stylianosgakis.devbytes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import se.stylianosgakis.devbytes.database.getDatabase
import se.stylianosgakis.devbytes.domain.Video
import se.stylianosgakis.devbytes.repository.VideosRepository

class DevByteViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val videosRepository = VideosRepository(database)

    init {
        viewModelScope.launch {
            videosRepository.refreshVideos()
        }
    }

    val videosListLiveData: LiveData<List<Video>> = videosRepository.videosLiveData

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
