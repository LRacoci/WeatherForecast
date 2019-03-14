package com.lracoci.weatherforecast.ui.coroutines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext



abstract class ScopedAndroidViewModel(app : Application) : AndroidViewModel(app), CoroutineScope {
    private var viewModelJob: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}