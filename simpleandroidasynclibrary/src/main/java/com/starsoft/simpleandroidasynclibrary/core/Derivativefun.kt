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

@file:JvmName("AsyncOperation")

package com.starsoft.simpleandroidasynclibrary.core

import com.starsoft.simpleandroidasynclibrary.core.executorfun.handleOnExecutor
import com.starsoft.simpleandroidasynclibrary.core.executorfun.runOnExecutor
import com.starsoft.simpleandroidasynclibrary.core.executorfun.runOnExecutorWitch
import com.starsoft.simpleandroidasynclibrary.executors.globalHandlerThread
import com.starsoft.simpleandroidasynclibrary.executors.globalMultiThreadPoll
import com.starsoft.simpleandroidasynclibrary.executors.globalSingleTreadPoll
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.handlerthreads.HandlerThreadExecutor
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools.MultiThreadPool
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools.SingleThreadPool
import com.starsoft.simpleandroidasynclibrary.stubs.stub
import com.starsoft.simpleandroidasynclibrary.stubs.stubErrorCallback
import java.util.concurrent.Executor

//This File Created at 26.10.2020 13:57.

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> T.runOnGlobalSinglePool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): SingleThreadPool = this.runOnExecutor(
    globalSingleTreadPoll,
    onResult,
    onError,
    lambda
) as SingleThreadPool

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> T.runOnGlobalMultiPool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): MultiThreadPool = this.runOnExecutor(
    globalMultiThreadPoll,
    onResult,
    onError,
    lambda
) as MultiThreadPool

/**
 * @since 0.1.1
 */
@JvmOverloads
fun <T, R> T.runOnGlobalHandlerThread(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): HandlerThreadExecutor = this.runOnExecutor(
    globalHandlerThread,
    onResult,
    onError,
    lambda
) as HandlerThreadExecutor

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <R> runOnGlobalSinglePool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): SingleThreadPool =
    globalSingleTreadPoll.launch(onResult, onError, lambda) as SingleThreadPool

/**
 * @since 0.1.1
 */
@JvmOverloads
fun <R> runOnGlobalHandlerThread(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): HandlerThreadExecutor =
    globalHandlerThread.launch(onResult, onError, lambda) as HandlerThreadExecutor

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <R> runOnGlobalMultiPool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): MultiThreadPool =
    globalMultiThreadPoll.launch(onResult, onError, lambda) as MultiThreadPool

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> Executor.executeWitch(
    receiver: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): Executor = runOnExecutorWitch(this, receiver, onResult, onError, lambda)

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <T, R> Executor.handle(
    data: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (T) -> R
): Executor = handleOnExecutor(this, data, onResult, onError, lambda)

/**
 * @since 0.1.0
 */
@JvmOverloads
fun <R> Executor.launch(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): Executor = handleOnExecutor(this, Unit, onResult, onError, lambda)