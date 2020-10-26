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

package com.starsoft.simpleandroidasynclibrary.core

import com.starsoft.simpleandroidasynclibrary.core.executorfun.handleOnExecutor
import com.starsoft.simpleandroidasynclibrary.core.executorfun.runOnExecutor
import com.starsoft.simpleandroidasynclibrary.core.executorfun.runOnExecutorWitch
import com.starsoft.simpleandroidasynclibrary.executors.ExecutorsProvider
import com.starsoft.simpleandroidasynclibrary.stubs.stub
import com.starsoft.simpleandroidasynclibrary.stubs.stubErrorCallback
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

//This File Created at 26.10.2020 13:57.

fun <T, R> T.runOnGlobalSinglePool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): ThreadPoolExecutor = this.runOnExecutor(ExecutorsProvider.globalSingleTreadPoll, onResult, onError, lambda) as ThreadPoolExecutor

fun <T, R> T.runOnGlobalMultiPool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): ThreadPoolExecutor = this.runOnExecutor(ExecutorsProvider.globalMultiThreadPoll, onResult, onError, lambda) as ThreadPoolExecutor

fun <R> runOnGlobalSinglePool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): ThreadPoolExecutor = ExecutorsProvider.globalSingleTreadPoll.run(onResult, onError, lambda) as ThreadPoolExecutor

fun <T, R> runOnGlobalMultiPool(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): ThreadPoolExecutor = ExecutorsProvider.globalMultiThreadPoll.run(onResult, onError, lambda) as ThreadPoolExecutor

fun <T, R> Executor.executeWitch(
    receiver: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): Executor = runOnExecutorWitch(this, receiver, onResult, onError, lambda)

fun <T, R> Executor.handle(
    data: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (T) -> R
): Executor = handleOnExecutor(this, data, onResult, onError, lambda)

fun <R> Executor.run(
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (Unit) -> R
): Executor = handleOnExecutor(this, Unit, onResult, onError, lambda)