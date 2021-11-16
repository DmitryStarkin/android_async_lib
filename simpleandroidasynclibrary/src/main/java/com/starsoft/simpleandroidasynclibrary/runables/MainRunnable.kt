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

package com.starsoft.simpleandroidasynclibrary.runables

import com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport
import com.starsoft.simpleandroidasynclibrary.core.utils.isMainThread
import com.starsoft.simpleandroidasynclibrary.handlers.DELIVER_ERROR
import com.starsoft.simpleandroidasynclibrary.handlers.DELIVER_RESULT
import com.starsoft.simpleandroidasynclibrary.handlers.MainHandler

//This File Created at 25.10.2020 11:05.

/**
 * @since 0.1.0
 * @suppress*/
class MainRunnable<T, V>(
    private var data: T?,
    private var onResult: ((V) -> Unit)?,
    private var onError: ((Throwable) -> Unit)?,
    private var lambda: ((T) -> V)?
) : Runnable, LifecycleSupport {

    private var result: V? = null
    var error: Throwable? = null
        private set

    override fun run() {
            try {
                result = data?.let { lambda?.invoke(it) }
                command(DELIVER_RESULT)
            } catch (e: Throwable) {
                e.printStackTrace()
                error = e
                command(DELIVER_ERROR)
            }
    }

    fun deliverResult() {
        result?.let { onResult?.invoke(it) }
        finalizeThis()
    }

    fun deliverError() {
        error?.let { onError?.invoke(it) }
        finalizeThis()
    }

    private fun sendCommandToHandler(command: Int) {
        val message = MainHandler.obtainMessage(command, this)
        message.sendToTarget()
    }

    private fun command(command: Int) {
        if (!Thread.currentThread().isInterrupted) {
            if (!isMainThread()) {
                sendCommandToHandler(command)
            } else {
                when (command) {
                    DELIVER_RESULT -> deliverResult()
                    DELIVER_ERROR -> deliverError()
                }
            }
        }
    }

    private fun finalizeThis() {
        result = null
        onResult = null
        onError = null
        lambda = null
    }

 //TODO this class is not yet ready for the lifecycle
    override fun finalizeTask() {
        finalizeThis()
    }
}