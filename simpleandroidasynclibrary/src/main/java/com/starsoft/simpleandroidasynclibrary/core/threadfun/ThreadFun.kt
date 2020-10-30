/*
 * Copyright (c) 2020. Dmitry Starkin Contacts: t0506803080@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the «License»);
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  //www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an «AS IS» BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

@file:JvmName("ThreadUtil")

package com.starsoft.simpleandroidasynclibrary.core.threadfun

import com.starsoft.simpleandroidasynclibrary.core.executorfun.*
import com.starsoft.simpleandroidasynclibrary.executors.currentThread
import com.starsoft.simpleandroidasynclibrary.stubs.stub
import com.starsoft.simpleandroidasynclibrary.stubs.stubErrorCallback

//This File Created at 25.10.2020 11:25.

/**
 * Calls the specified function [lambda] with `this` value
 * as its receiver and returns its result as callback
 * each call is made on a new thread
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 * [onError] and [onResult] are called in the main thread
 * @param _isDaemon determines whether the thread is a daemon, false by default
 * @return a reference to the thread in which the work
 * is performed can be used for example for interrupting
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> T.runOnThread(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    _isDaemon: Boolean = false,
    lambda:  T.() -> R
): Thread = Thread {this.runOnExecutor(
    currentThread(),
    onResult,
    onError,
    lambda
)}.apply {
    isDaemon = _isDaemon
    start()
}

/**
 * Calls the specified function [lambda] with the given [receiver] as its receiver
 * and returns its result as callback
 * each call is made on a new thread
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 * [onError] and [onResult] are called in the main thread
 *  @param _isDaemon determines whether the thread is a daemon, false by default
 * @return a reference to the thread in which the work
 * is performed can be used for example for interrupting
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> runOnThreadWitch(
    receiver: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    _isDaemon: Boolean = false,
    lambda: T.() -> R
): Thread = Thread {
    runOnExecutorWitch(
        currentThread(),
        receiver,
        onResult,
        onError,
        lambda
    )
}.apply {
    isDaemon = _isDaemon
    start()
}

/**
 * Calls the specified function [lambda]
 * with `this` value as its argument and returns its result as callback
 * each call is made on a new thread
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 * [onError] and [onResult] are called in the main thread
 * @param _isDaemon determines whether the thread is a daemon, false by default
 * @return a reference to the thread in which the work
 * is performed can be used for example for interrupting
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> T.processingOnThread(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    _isDaemon: Boolean = false,
    lambda: (T) -> R
): Thread = Thread {
    processingOnExecutor(
        currentThread(),
        onResult,
        onError,
        lambda
    )
}.apply {
    isDaemon = _isDaemon
    start()
}

/**
 * Calls the specified function [lambda] with the given [data] as as its argument
 * and returns its result as callback
 * each call is made on a new thread
 * @param data data for handling
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 * [onError] and [onResult] are called in the main thread
 * @param _isDaemon determines whether the thread is a daemon, false by default
 * @return a reference to the thread in which the work
 * is performed can be used for example for interrupting
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> handleOnThread(
    data: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    _isDaemon: Boolean = false,
    lambda: (T) -> R
): Thread = Thread {
    handleOnExecutor(
        currentThread(),
        data,
        onResult,
        onError,
        lambda
    )
}.apply {
    isDaemon = _isDaemon
    start()
}

/**
 * Calls the specified function [lambda]
 * with `this` value as its argument on a new thread
 * @param onResult Calls  on main thread with `this` value as its argument after
 * function [lambda] finished
 * if this code is missing will be run stub
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 * [onError] and [onResult] are called in the main thread
 * @param _isDaemon determines whether the thread is a daemon, true by default
 * @return a reference to the thread in which the work
 * is performed can be used for example for interrupting
 * @since 0.1.0
 */
@JvmOverloads
fun <T> T.prepareOnThreadAndRun(
    onResult: T.() -> Unit = ::rStub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    _isDaemon: Boolean = false,
    lambda: T.() -> Unit
): Thread = Thread {
    prepareOnExecutorAndRun(
        currentThread(),
        onResult,
        onError,
        lambda
    )
}.apply {
    isDaemon = _isDaemon
    start()
}

/**
 * @since 0.1.0
 * @suppress*/
fun <T> T.rStub(t: T) {

}