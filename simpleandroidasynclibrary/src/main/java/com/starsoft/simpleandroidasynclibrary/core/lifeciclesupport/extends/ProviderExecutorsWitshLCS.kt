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

package com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.extends

import androidx.lifecycle.LifecycleOwner
import com.starsoft.simpleandroidasynclibrary.executors.ExecutorsProvider
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.handlerthreads.HandlerThreadExecutor
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools.MultiThreadPool
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools.SingleThreadPool
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threads.SingleThreadExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor


// This File Created at 27.10.2020 11:36.

/**
 * Extension for LifecycleOwner creates the [Executor]
 * which keeps track of the life cycle (which shutdown in onDestroy)
 * Providing [Executor] as a new thread pool with a single thread
 * @return [ThreadPoolExecutor] with a single thread,
 * keeps track of the life cycle (shutdown in onDestroy)
 * @since 0.1.1
 */
fun LifecycleOwner.newSingleThreadPoll(): ThreadPoolExecutor =
    ExecutorsProvider.newSingleThreadPool()
        .apply { (this as SingleThreadPool).connectToLifecycle(this@newSingleThreadPoll) }

/**
 * Extension for LifecycleOwner creates the [Executor]
 * which keeps track of the life cycle (which shutdown in onDestroy)
 * Providing [Executor] as a new thread pool with a starting number
 * of threads equal to the number of processors + 1
 * and a maximum number of threads equal to the number of processors * 2.
 * @return [ThreadPoolExecutor] with a with a starting number
 * of threads equal to the number of processors + 1
 * and a maximum number of threads equal to the number of processors * 2,
 * keeps track of the life cycle (shutdown in onDestroy)
 * @since 0.1.1
 */
fun LifecycleOwner.newMultiThreadPoll(): ThreadPoolExecutor =
    ExecutorsProvider.newMultiThreadPoll()
        .apply { (this as MultiThreadPool).connectToLifecycle(this@newMultiThreadPoll) }

/**
 * Extension for LifecycleOwner creates the [Executor]
 * which keeps track of the life cycle (which interrupt in onDestroy)
 * Providing [Executor] as a  new thread created for execution each time
 * @return [Executor] that uses a new thread to perform a task,
 * keeps track of the life cycle (interrupt in onDestroy)
 * @since 0.1.1
 */
fun LifecycleOwner.newThread(): Executor =
    ExecutorsProvider.newThread()
        .apply { (this as SingleThreadExecutor).connectToLifecycle(this@newThread) }

/**
 * Extension for LifecycleOwner creates the [Executor]
 * which keeps track of the life cycle (which quit in onDestroy)
 * Providing [Executor] as a  new [HandlerThread][android.os.HandlerThread] created for execution each time
 * @return [Executor] that uses a [HandlerThread][android.os.HandlerThread] to perform a task,
 * keeps track of the life cycle (quit in onDestroy)
 * this HandlerThread is returned running and does not require calling the start method
 * @param id Id for this [HandlerThread][android.os.HandlerThread]
 * @since 0.1.1
 */
fun LifecycleOwner.newHandlerThread(id: String = ""): Executor =
    ExecutorsProvider.newHandlerThread(id)
        .apply { (this as HandlerThreadExecutor).connectToLifecycle(this@newHandlerThread) }
