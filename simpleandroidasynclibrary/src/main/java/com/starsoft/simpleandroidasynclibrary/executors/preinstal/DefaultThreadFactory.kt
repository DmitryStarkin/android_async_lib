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

package com.starsoft.simpleandroidasynclibrary.executors.preinstal

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

//This File Created at 25.10.2020 17:57.

/**
 * @suppress
 * @since 0.1.0
 * */
object DefaultThreadFactory : ThreadFactory {

    private const val THREAD_NAME_PREFIX = "Async_android_lib_worker-"

        private var count = AtomicInteger(1)

    override fun newThread(r: Runnable): Thread {
        val thread  = Thread(r)
        thread.name = THREAD_NAME_PREFIX + count.getAndIncrement()
        return thread
    }
}