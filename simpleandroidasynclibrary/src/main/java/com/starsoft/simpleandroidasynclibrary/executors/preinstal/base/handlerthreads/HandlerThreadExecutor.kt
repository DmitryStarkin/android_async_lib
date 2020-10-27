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

package com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.handlerthreads

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport
import java.lang.Exception
import java.util.concurrent.Executor


// This File Created at 27.10.2020 13:10.

/**
 * This class Implement [Executor] as a  new [HandlerThread][android.os.HandlerThread]
 * This [HandlerThreadExecutor] can connect to Lifecycle
 * by [connectToLifecycle][com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport.connectToLifecycle] method
 * @since 0.1.1
 */
class HandlerThreadExecutor(id: String, priority: Int = Process.THREAD_PRIORITY_DEFAULT) :
    HandlerThread(id, priority), Executor, LifecycleSupport {

    var allowFinalize: Boolean = true
    private var isWork = false
    private lateinit var handler: Handler

    /**
     * @suppress
     * @since 0.1.1
     * */
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = Handler(looper)
        isWork = true
    }

    /**
     * @suppress
     * @since 0.1.1
     * */
    override fun execute(command: Runnable) {
        if (isWork) {
            handler.post(command)
        }
    }

    /**
     * @suppress
     * @since 0.1.1
     * */
    override fun finalize() {

        if (allowFinalize) {
            isWork = false
            interrupt()
            quit()
        } else {
            throw Exception("This Executor cant finalize")
        }
    }
}