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

import androidx.lifecycle.LifecycleOwner
import com.starsoft.simpleandroidasynclibrary.core.lifeciclesupport.interfaces.LifecycleSupport
import java.lang.Exception
import java.util.concurrent.*

//This File Created at 25.10.2020 17:54.

/**
 * @since 0.1.0
 * @suppress*/
private fun determineNumberOfProcessors() = Runtime.getRuntime().availableProcessors()

val DEFAULT: Nothing? = null
val NUMBER_OF_PROCESSORS = determineNumberOfProcessors()

private const val DEFAULT_CORE_POOL_SIZE = 0
private val DEFAULT_MAX_POOL_SIZE = NUMBER_OF_PROCESSORS + 1
private const val DEFAULT_THREAD_IDLE_TIME: Long = 30
private const val DEFAULT_ALLOW_CORE_THREAD_TIME_OUT: Boolean = false
private val DEFAULT_TIME_UNIT: TimeUnit? = TimeUnit.SECONDS

/**
 * @since 0.1.0
 * @suppress*/
abstract class AbstractThreadPool : ThreadPoolExecutor(
    DEFAULT_CORE_POOL_SIZE,
    DEFAULT_MAX_POOL_SIZE,
    DEFAULT_THREAD_IDLE_TIME,
    DEFAULT_TIME_UNIT,
    LinkedBlockingQueue<Runnable>() as BlockingQueue<Runnable>?
), LifecycleSupport {

    var allowFinalize: Boolean = true
    abstract val allowCoreThreadTimeOut: Boolean?
    abstract val startPoolSize: Int?
    abstract val maxPoolSize: Int?
    abstract val threadIdleTime: Long?
    abstract val timeUnit: TimeUnit?
    abstract val factory: ThreadFactory?
    abstract val rejectedTaskHandler: RejectedExecutionHandler?

    fun adjust() {
        allowCoreThreadTimeOut(allowCoreThreadTimeOut ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
        setKeepAliveTime(threadIdleTime?.takeUnless {
            it == 0L && (allowCoreThreadTimeOut ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
        } ?: DEFAULT_THREAD_IDLE_TIME, timeUnit ?: DEFAULT_TIME_UNIT)
        corePoolSize = startPoolSize ?: DEFAULT_CORE_POOL_SIZE
        maximumPoolSize = maxPoolSize ?: DEFAULT_MAX_POOL_SIZE
        if (rejectedTaskHandler !== DEFAULT) rejectedExecutionHandler = rejectedTaskHandler
        threadFactory = factory ?: DefaultThreadFactory
    }

    final override fun allowCoreThreadTimeOut(value: Boolean) {
        super.allowCoreThreadTimeOut(
            allowCoreThreadTimeOut ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT
        )
    }

    final override fun setKeepAliveTime(time: Long, unit: TimeUnit?) {
        super.setKeepAliveTime(threadIdleTime?.takeUnless {
            it == 0L && (allowCoreThreadTimeOut ?: DEFAULT_ALLOW_CORE_THREAD_TIME_OUT)
        } ?: DEFAULT_THREAD_IDLE_TIME, timeUnit ?: DEFAULT_TIME_UNIT)
    }

    final override fun setMaximumPoolSize(maximumPoolSize: Int) {
        super.setMaximumPoolSize(maxPoolSize ?: DEFAULT_MAX_POOL_SIZE)
    }

    final override fun setCorePoolSize(corePoolSize: Int) {
        super.setCorePoolSize(corePoolSize ?: DEFAULT_CORE_POOL_SIZE)
    }

    final override fun setThreadFactory(threadFactory: ThreadFactory?) {
        super.setThreadFactory(threadFactory ?: DefaultThreadFactory)
    }

    override fun shutdown() {
        if (allowFinalize) {
            super.shutdown()
        } else {
            throw Exception("Impossible shutdown this ThreadPool")
        }
    }

    override fun shutdownNow(): MutableList<Runnable> {
        if (allowFinalize) {
            return super.shutdownNow()
        } else {
            throw Exception("Impossible shutdown this ThreadPool")
        }
    }

    override fun connectToLifecycle(owner: LifecycleOwner) {
        if (allowFinalize) {
            super.connectToLifecycle(owner)
        } else {
            throw Exception("This ThreadPool cant connect to Lifecycle")
        }
    }

    override fun finalizeTask() {
        if (allowFinalize) {
            shutdownNow()
        } else {
            throw Exception("This ThreadPool cant finalize")
        }
    }
}