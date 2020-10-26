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

package com.starsoft.simpleandroidasynclibrary.core.executorfun



import com.starsoft.simpleandroidasynclibrary.runables.MainRunnable
import com.starsoft.simpleandroidasynclibrary.stubs.TStub
import com.starsoft.simpleandroidasynclibrary.stubs.stub
import com.starsoft.simpleandroidasynclibrary.stubs.stubErrorCallback
import java.util.concurrent.Executor

/**
 *This File Created at 25.10.2020 11:52.
 **/

/**
 * Calls the specified function [lambda] with `this` value
 * as its receiver and returns its result as callback
 * each call is made on a given [Executor]
 * @param executor [Executor] for perform work
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 *
 * [onError] and [onResult] are called in the main thread
 * @return a reference to the [Executor] in which the work
 */
fun <T, R> T.runOnExecutor(
    executor: Executor,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): Executor = executor.apply {
    execute(MainRunnable(Unit, onResult, onError, { this@runOnExecutor.lambda() }))
}

/**
 * Calls the specified function [lambda] with the given [receiver] as its receiver
 * and returns its result as callback
 * each call is made on a given [Executor]
 * @param executor [Executor] for perform work
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 *
 * [onError] and [onResult] are called in the main thread
 * @return a reference to the [Executor] in which the work
 */
fun <T, R> runOnExecutorWitch(
    executor: Executor,
    receiver: T = TStub(),
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> R
): Executor = executor.apply {
    execute(MainRunnable(Unit, onResult, onError, { receiver.lambda() }))
}

/**
 * Calls the specified function [lambda]
 * with `this` value as its argument and returns its result as callback
 * each call is made on a given [Executor]
 * @param executor [Executor] for perform work
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 *
 * [onError] and [onResult] are called in the main thread
 * @return a reference to the [Executor] in which the work
 */
fun <T, R> T.processingOnExecutor(
    executor: Executor,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (T) -> R
): Executor = executor.apply {
    execute(MainRunnable(this@processingOnExecutor, onResult, onError, lambda))
}


/**
 * Calls the specified function [lambda] with the given [data] as as its argument
 * and returns its result as callback
 * each call is made on a given [Executor]
 * @param executor [Executor] for perform work
 * @param data data for handling
 * @param onResult the code that return the result,
 * if this code is missing will be run [stub][stub]
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 *
 * [onError] and [onResult] are called in the main thread
 * @return a reference to the [Executor] in which the work
 */
fun <T, R> handleOnExecutor(
    executor: Executor,
    data: T,
    onResult: (R) -> Unit = ::stub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: (T) -> R
): Executor = executor.apply {
    execute(MainRunnable(data, onResult, onError, lambda))
}

/**
 * Calls the specified function [lambda]
 * with `this` value as its argument on a given [Executor]
 * @param executor [Executor] for perform work
 * @param onResult Calls  on main thread with `this` value as its argument after
 * function [lambda] finished
 * if this code is missing will be run stub
 * @param onError the code that  handle the Exception,
 * if this code is missing will be run [stubErrorCallback][stubErrorCallback]
 * [onError] and [onResult] are called in the main thread
 * @return a reference to the [Executor] in which the work
 * is performed
 */
fun <T> T.prepareOnExecutorAndRun(
    executor: Executor,
    onResult: T.() -> Unit = ::rStub,
    onError: (Throwable) -> Unit = ::stubErrorCallback,
    lambda: T.() -> Unit
): Executor = executor.apply {
    execute(
        MainRunnable(
            Unit,
            { this@prepareOnExecutorAndRun.onResult() },
            onError,
            { this@prepareOnExecutorAndRun.lambda() })
    )
}


/**@suppress*/
fun <T> T.rStub(t: T) {

}


