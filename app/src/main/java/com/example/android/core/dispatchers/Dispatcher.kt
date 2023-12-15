package com.example.android.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

/**
 * The base interface for dispatching Coroutine work.
 * Can be mocked by implementing this interface for testability.
 */
interface Dispatcher {

    /** Dispatch work on Android's Main Thread **/
    val main: CoroutineDispatcher

    /** Dispatch work on a background thread (from a pool) for Networking **/
    val io: CoroutineDispatcher

}
