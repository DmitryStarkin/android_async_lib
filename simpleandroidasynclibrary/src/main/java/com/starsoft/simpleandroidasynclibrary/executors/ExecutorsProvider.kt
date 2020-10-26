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
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.MultiThreadPool
import com.starsoft.simpleandroidasynclibrary.executors.preinstal.SingleThreadPoll
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
     * @since 0.1.0
     */
    val globalSingleTreadPoll: MainSingleThreadPoll by lazy {
        MainSingleThreadPoll()
    }

    /**
     * Providing [Executor] as a thread pool with a starting number
     * of threads equal to the number of processors + 1
     * and a maximum number of threads equal to the number of processors * 2.
     * This pool exists in a single instance within the application process
     * @since 0.1.0
     */
    val globalMultiThreadPoll: MainMultiThreadPool by lazy {
        MainMultiThreadPool()
    }

    /**
     * @since 0.1.0
     * @suppress*/
    val globalHandlerThread: Executor by lazy {
        object : Executor, HandlerThread("common HandlerThread") {
            override fun execute(command: Runnable?) {
                TODO("Not yet implemented")
            }

        }
    }

    /**
     * Providing [Executor] as a  new thread created for execution each time
     * @return [Executor] that uses a new thread to perform a task
     * @since 0.1.0
     */
    fun newThread(): Executor = Executor { command -> Thread(command).start() }

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
    fun newSingleThreadPoll(): ThreadPoolExecutor = SingleThreadPoll()

    /**
     * Providing [Executor] as a new thread pool with a starting number
     * of threads equal to the number of processors + 1
     * and a maximum number of threads equal to the number of processors * 2.
     * @since 0.1.0
     */
    fun newMultiThreadPoll(): ThreadPoolExecutor = MultiThreadPool()

}

/**
 * A class that implements a global thread pool with a single thread
 * @since 0.1.0
 */
class MainSingleThreadPoll() : SingleThreadPoll() {
    /**
     * This feature makes it impossible for the global thread pool to stop working
     * @since 0.1.0
     */
    override fun shutdown() {
        print("Main ThreadPoll cant shutdown")
    }
}

/**
 * A class that implements a global thread pool with a starting number
 * of threads equal to the number of processors + 1
 * and a maximum number of threads equal to the number of processors * 2.
 * @since 0.1.0
 */
class MainMultiThreadPool() : MultiThreadPool() {

    /**
     * this feature makes it impossible for the global thread pool to stop working
     * @since 0.1.0
     */
    override fun shutdown() {
        print("Main ThreadPoll cant shutdown")
    }
}