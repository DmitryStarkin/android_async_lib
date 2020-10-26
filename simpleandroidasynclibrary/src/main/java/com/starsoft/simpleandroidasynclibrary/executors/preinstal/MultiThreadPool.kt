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

import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

//This File Created at 25.10.2020 18:03.

/**
 * @since 0.1.0
 * @suppress
 */
open class MultiThreadPool internal constructor(): AbstractThreadPool() {

    override val ALLOW_CORE_THREAD_TIME_OUT: Boolean?= DEFAULT
    override val CORE_POOL_SIZE: Int? = DEFAULT
    override val MAX_POOL_SIZE: Int? = NUMBER_OF_PROCESSORS * 2
    override val THREAD_IDLE_TIME: Long? = DEFAULT
    override val TIME_UNIT: TimeUnit? = DEFAULT
    override val THREAD_FACTORY: ThreadFactory? = DEFAULT
    override val REGECTED_TASK_HANDLER: RejectedExecutionHandler? = DEFAULT

    init {
        adjust()
    }

}