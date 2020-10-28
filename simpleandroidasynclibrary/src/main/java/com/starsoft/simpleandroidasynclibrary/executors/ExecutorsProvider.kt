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

package com.starsoft.simpleandroidasynclibrary.executors

import android.os.HandlerThread
import com.starsoft.simpleandroidasynclibrary.core.utils.isMainThread
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.handlerthreads.HandlerThreadExecutor
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools.MultiThreadPool
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools.SingleThreadPool
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threads.SingleThreadExecutor
import com.starsoft.simpleandroidasynclibrary.handlers.MainHandler
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor


//This File Created at 25.10.2020 17:33.

/**
 * Factory class for providing executors
 * @since 0.1.0
 */

object ExecutorsProvider {

    /**
     * Providing [Executor] as a thread pool with a single thread
     * that exists in a single instance within the application process
     * You impossible shutdown this ThreadPool
     * This ThreadPool cant connect to Lifecycle
     * @since 0.1.0
     */
    val globalSingleTreadPoll: SingleThreadPool by lazy {
        SingleThreadPool().apply { allowFinalize = false }
    }

    /**
     * Providing [Executor] as a thread pool with a starting number
     * of threads equal to the number of processors + 1
     * and a maximum number of threads equal to the number of processors * 2.
     * This pool exists in a single instance within the application process
     * You impossible shutdown this ThreadPool
     * This ThreadPool cant connect to Lifecycle
     * @since 0.1.0
     */
    val globalMultiThreadPoll: MultiThreadPool by lazy {
        MultiThreadPool().apply { allowFinalize = false }
    }

    /**
     * Providing [Executor] as a  new [HandlerThread][android.os.HandlerThread]
     * This [Executor] exists in a single instance within the application process
     * @return [Executor] that uses a [HandlerThread][android.os.HandlerThread] to perform a task,
     * this HandlerThread is returned running and does not require calling the start method
     * You impossible quit this [HandlerThread][android.os.HandlerThread]
     * This [Executor] cant connect to Lifecycle
     * @since 0.1.0
     */
    val globalHandlerThread: Executor by lazy {
        HandlerThreadExecutor("Global HandlerThread").apply {
            allowFinalize = false
            start()
        }
    }

    /**
     * Providing [Executor] as a  new [HandlerThread][android.os.HandlerThread] created for execution each time
     * @return [Executor] that uses a [HandlerThread][android.os.HandlerThread] to perform a task,
     * this HandlerThread is returned running and does not require calling the start method
     * @param id Id for this [HandlerThread][android.os.HandlerThread]
     * @since 0.1.1
     */
    fun newHandlerThread(id :String = ""): HandlerThreadExecutor = HandlerThreadExecutor(id).apply { start() }

    /**
     * Providing [Executor] as a  new thread created for execution each time
     * @return [Executor] that uses a new thread to perform a task
     * @since 0.1.0
     */
    fun newThread(): SingleThreadExecutor = SingleThreadExecutor()

    /**
     * Providing [Executor] that executes a task in the thread where it was called
     * @return [Executor] that executes a task in the thread where it was called
     * @since 0.1.0
     */
    fun currentThread(): Executor = Executor { command -> command.run() }

    /**
     * Providing [Executor] that executes a task in the Main thread
     * @return [Executor] that executes a task in Main thread
     * @since 0.1.0
     */
    fun mainThread(): Executor {
        return if (isMainThread()) {
            currentThread()
        } else {
            Executor { command -> MainHandler.post(command) }
        }
    }

    /**
     * Providing [Executor] as a new thread pool with a single thread
     * @return [ThreadPoolExecutor] with a single thread
     * @since 0.1.0
     */
    fun newSingleThreadPool(): SingleThreadPool = SingleThreadPool()

    /**
     * Providing [Executor] as a new thread pool with a starting number
     * of threads equal to the number of processors + 1
     * and a maximum number of threads equal to the number of processors * 2.
     * @since 0.1.0
     */
    fun newMultiThreadPoll(): MultiThreadPool = MultiThreadPool()
}