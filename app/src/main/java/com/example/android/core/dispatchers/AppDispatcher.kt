package com.example.android.core.dispatchers

import kotlinx.coroutines.Dispatchers

/**
 * Android dispatcher for work.
 * Internally uses Coroutine Dispatchers for dispatching work to background threads.
 */
class AppDispatcher : Dispatcher {

    override val main = Dispatchers.Main

    override val io = Dispatchers.IO

}
