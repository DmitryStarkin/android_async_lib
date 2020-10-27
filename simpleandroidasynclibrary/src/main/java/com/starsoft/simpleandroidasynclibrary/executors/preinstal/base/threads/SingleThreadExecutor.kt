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

package com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threads

import androidx.lifecycle.LifecycleOwner
import com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport
import java.util.concurrent.Executor


// This File Created at 27.10.2020 11:50.

/**
 * This class Implement [Executor] as a  new [Thread][java.lang.Thread]
 * This [SingleThreadExecutor] can connect to Lifecycle
 * by [connectToLifecycle][com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport.connectToLifecycle] method
 * @since 0.1.1
 */
class SingleThreadExecutor : Executor, LifecycleSupport {

    private lateinit var thread: Thread
    private var destroyed = false

    /**
     * @suppress
     * @since 0.1.1
     * */
    override fun execute(command: Runnable?) {
        if (!destroyed) {
            thread = Thread(command).apply { start() }
        }
    }

    /**
     * @suppress
     * @since 0.1.1
     * */
    override fun finalize() {
        destroyed = true
        if (::thread.isInitialized) {
            thread.interrupt()
        }
    }
}