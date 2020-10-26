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

import java.util.concurrent.*


/**
 *This File Created at 25.10.2020 17:54.
 **/

private fun determineNumberOfProcessors() = Runtime.getRuntime().availableProcessors()

val DEFAULT: Nothing? = null
val NUMBER_OF_PROCESSORS = determineNumberOfProcessors()

private const val DEFAULT_CORE_POOL_SIZE = 0
private val DEFAULT_MAX_POOL_SIZE = NUMBER_OF_PROCESSORS + 1
private const val DEFAULT_THREAD_IDLE_TIME: Long = 30
private const val DEFAULT_ALLOW_CORE_THREAD_TIME_OUT: Boolean = false
private val DEFAULT_TIME_UNIT: TimeUnit? = TimeUnit.SECONDS


abstract class AbstractThreadPool : ThreadPoolExecutor(
    DEFAULT_CORE_POOL_SIZE,
    DEFAULT_MAX_POOL_SIZE,
    DEFAULT_THREAD_IDLE_TIME,
    DEFAULT_TIME_UNIT,
    LinkedBlockingQueue<Runnable>() as BlockingQueue<Runnable>?
) {

    abstract val ALLOW_CORE_THREAD_TIME_OUT: Boolean?
    abstract val CORE_POOL_SIZE: Int?
    abstract val MAX_POOL_SIZE: Int?
    abstract val THREAD_IDLE_TIME: Long?
    abstract val TIME_UNIT: TimeUnit?
    abstract val THREAD_FACTORY: ThreadFactory?
    abstract val REGECTED_TASK_HANDLER: RejectedExecutionHandler?

    fun build() {
        allowCoreThreadTimeOut(ALLOW_CORE_THREAD_TIME_OUT ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
        setKeepAliveTime(THREAD_IDLE_TIME?.takeUnless {
            it == 0L && (ALLOW_CORE_THREAD_TIME_OUT ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
        } ?: DEFAULT_THREAD_IDLE_TIME, TIME_UNIT ?: DEFAULT_TIME_UNIT)
        corePoolSize = CORE_POOL_SIZE ?: DEFAULT_CORE_POOL_SIZE
        maximumPoolSize = MAX_POOL_SIZE ?: DEFAULT_MAX_POOL_SIZE
        if (REGECTED_TASK_HANDLER !== DEFAULT) rejectedExecutionHandler = REGECTED_TASK_HANDLER
        threadFactory = THREAD_FACTORY ?: DefaultThreadFactory
    }

    final override fun allowCoreThreadTimeOut(value: Boolean) {
        super.allowCoreThreadTimeOut(ALLOW_CORE_THREAD_TIME_OUT ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
    }

    final override fun setKeepAliveTime(time: Long, unit: TimeUnit?) {
        super.setKeepAliveTime(THREAD_IDLE_TIME?.takeUnless {
            it == 0L && (ALLOW_CORE_THREAD_TIME_OUT ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
        } ?: DEFAULT_THREAD_IDLE_TIME, TIME_UNIT ?: DEFAULT_TIME_UNIT)
    }

    final override fun setMaximumPoolSize(maximumPoolSize: Int) {
        super.setMaximumPoolSize(MAX_POOL_SIZE ?: DEFAULT_MAX_POOL_SIZE)
    }

    final override fun setCorePoolSize(corePoolSize: Int) {
        super.setCorePoolSize(CORE_POOL_SIZE ?: DEFAULT_CORE_POOL_SIZE)
    }

    final override fun setThreadFactory(threadFactory: ThreadFactory?) {
        super.setThreadFactory(THREAD_FACTORY ?: DefaultThreadFactory)
    }
}