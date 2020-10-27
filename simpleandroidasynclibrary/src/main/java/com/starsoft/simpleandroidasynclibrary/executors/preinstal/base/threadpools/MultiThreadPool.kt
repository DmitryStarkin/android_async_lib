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

package com.starsoft.simpleandroidasynclibrary.executors.preinstal.base.threadpools

import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

//This File Created at 25.10.2020 18:03.

/**
 * Thread pool with a starting number
 * of threads equal to the number of processors + 1
 * and a maximum number of threads equal to the number of processors * 2.
 * This ThreadPool can connect to Lifecycle
 * by [connectToLifecycle][com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport.connectToLifecycle] method
 * @since 0.1.0
 */
class MultiThreadPool: AbstractThreadPool() {

    override val allowCoreThreadTimeOut: Boolean?= DEFAULT
    override val startPoolSize: Int? = DEFAULT
    override val maxPoolSize: Int? = NUMBER_OF_PROCESSORS * 2
    override val threadIdleTime: Long? = DEFAULT
    override val timeUnit: TimeUnit? = DEFAULT
    override val factory: ThreadFactory? = DEFAULT
    override val rejectedTaskHandler: RejectedExecutionHandler? = DEFAULT

    init {
        adjust()
    }

}