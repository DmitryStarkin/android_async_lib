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

package com.starsoft.simpleandroidasynclibrary.core.utils

import android.os.Looper

//This File Created at 26.10.2020 10:00.

/**
 * Defines the thread in which the function is called
 * @return true if called in Main tread false otherwise
 * @since 0.1.0
 */

fun isMainThread(): Boolean {
    return Looper.getMainLooper().thread === Thread.currentThread()
}