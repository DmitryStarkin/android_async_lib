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

package com.starsoft.simpleandroidasynclibrary.core.callbacks

import com.starsoft.simpleandroidasynclibrary.handlers.MainHandler

//This File Created at 25.10.2020 11:55.

/**
 * @since 0.1.0
 * Calls `this` as function in UI (Main) thread
 * @param data data that will be passed to function as an input parameter
 */
fun <T, V> ((T) -> V).runInUI(data: T) {
    MainHandler.post { this.invoke(data) }
}

/**
 * @since 0.1.0
 * Calls `this` as function in UI (Main) thread
 */
fun <V> (() -> V).runInUI() {
    MainHandler.post { this.invoke() }
}

/**
 * @since 0.1.0
 * Calls [lambda] as function in UI (Main) thread
 * @param lambda function to call
 * @param data data that will be passed to lambda as an input parameter
 */
fun <T, V> runOnUI(data: T , lambda: (T) -> V) {
    MainHandler.post { lambda(data) }
}

/**
 * @since 0.1.0
 * Calls [lambda] as function in UI (Main) thread
 * @param lambda function to call
 */
fun <V> runOnUI(lambda: () -> V) {
    MainHandler.post { lambda() }
}