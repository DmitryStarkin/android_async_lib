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

package com.starsoft.simpleandroidasynclibrary.stubs


/**
 *This File Created at 25.10.2020 11:24.
 **/

/**
 * This is stub for Callbacks
 * in case you don't need to do anything
 *  @since 1.0
 */
fun <T> stub(par: T) {

}

/**
 * This is stub for ErrorCallback
 * in case you don't need to to handle Exception
 * the exception will simply be throw
 * @since 1.0
 */
fun stubErrorCallback(par: Throwable) {
    throw par
}

/**@suppress*/
fun <T> T.rStub() {

}

/**@suppress*/
@Suppress("UNCHECKED_CAST")
fun <T> TStub(): T = Any() as T