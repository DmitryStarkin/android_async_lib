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
 * @since 0.1.0
 */

object ExecutorsProvider {

    val globalSingleTreadPoll: MainSingleThreadPoll by lazy {
        MainSingleThreadPoll()
    }

    val globalMultiThreadPoll: MainMultiThreadPool by lazy {
        MainMultiThreadPool()
    }

    val globalHandlerThread: Executor by lazy {
        object : Executor, HandlerThread("common HandlerThread") {
            override fun execute(command: Runnable?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun newThread(): Executor = Executor { command -> Thread(command).start() }

    fun currentThread(): Executor = Executor { command -> command.run() }

    fun mainThread(): Executor {
        return if (isMainThread()) {
            globalSingleTreadPoll.shutdown()
            currentThread()
        } else {
            Executor { command -> MainHandler.post(command) }
        }

    }

    fun newSingleThreadPoll(): ThreadPoolExecutor = SingleThreadPoll()

    fun newMultiThreadPoll(): ThreadPoolExecutor = MultiThreadPool()

}

/**
 * @since 0.1.0
 */
class MainSingleThreadPoll(): SingleThreadPoll(){

    override fun shutdown() {
      print("Main ThreadPoll cant shutdown")
    }
}

/**
 * @since 0.1.0
 */
class MainMultiThreadPool(): MultiThreadPool(){

    override fun shutdown() {
        print("Main ThreadPoll cant shutdown")
    }
}