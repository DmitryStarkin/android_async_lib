# A small Kotlin library for asynchronous task on Android

The library provide functions for easy execution of tasks in thread use lambda style,
and send lambda for update UI (like runOnUiThread in Android)

## Current version:
0.1.0b

## Installation:

1 in project level build.gradle add:
```
repositories {
........
        maven { url "https://jitpack.io" }
   }
```

2 in module level build.gradle add:
```
dependencies {
...........
         implementation 'com.github.DmitryStarkin:android_async_lib:version'
   }
```

## Usage:

If you need to execute code asynchronously, just use one of
the functions provided by the library.  
You can pass a callback to the function to process the result and to handle the error.  
Operations are performed using an executor that is passed as the first parameter.  
The library contains a Class that provides implementations of [Executor](https://developer.android.com/reference/java/util/concurrent/Executor):

globalSingleTreadPoll - operations are performed on a thread pool with a single thread that exists in a single instance within the application process.

globalMultiThreadPoll - operations are performed on a thread pool with a starting number of threads equal to the number of processors + 1  
and a maximum number of threads equal to the number of processors * 2. this pool exists in a single instance within the application process.

newSingleThreadPoll() - the same as globalSingleTreadPoll but return new instance each time

newMultiThreadPoll() - the same as globalMultiThreadPoll but return new instance each time

newThread () - a new thread is created for execution each time

currentThread () - task is performed in the calling thread

mainThread () - task is performed in the main thread

You can use your own implementation of the Executor (see [ThreadDoolExecutor](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor) for example)  
or get it using Java factory Executors See [Executors](https://developer.android.com/reference/java/util/concurrent/Executors)

For convenience the library includes extension functions for the Executor interface

Regardless of where the task is executed **callbacks are always executed in the main thread**

Code example

```kotlin
class SomeClass : Activity() {

    val textView: TextView = TextView(this) //for example
    var thread: Thread? = null

    fun someFun() {
        val inputStream: InputStream = File("example.txt").inputStream()
        //start operation on new Thread
        thread = inputStream.runOnThread(
            { text -> textView.text = text },// callback on main thread
            { e -> textView.text = e.toString() })// callback on main thread
        {
            bufferedReader().use { it.readText() }// execute on new thread
        }
    }

    fun someFun1() {

        val file = File("example.txt")
        thread = file.processingOnThread(
            { text -> textView.text = text }, // callback on main thread
            { e -> textView.text = e.toString() })// callback on main thread
        { _file ->
            _file.inputStream().bufferedReader().use { it.readText() }// execute on new thread
        }
    }

    fun someFun2() {

        val file = File("example.txt")
        thread = handleOnThread(
            file,
            { text -> textView.text = text }, // callback on main thread
            { e -> textView.text = e.toString() }) // callback on main thread
        {
            it.inputStream().bufferedReader()
                .use { reader -> reader.readText() }// execute on new thread, it represent parameter file
        }
    }


    fun someFun3() {

        val file = File("example.txt")
        //start operation on given Executor
        runOnExecutor(
            ExecutorsProvider.globalSingleTreadPoll,
            { text -> textView.text = text }, // callback on main thread
            { e -> textView.text = e.toString() }) // callback on main thread
        {
            file.inputStream().bufferedReader()
                .use { it.readText() }// execute on threadPool, file captured in closure
        }
    }

    fun someFun4() {

        val file = File("example.txt")
        //start operation on given Executor
        file.runOnExecutor(
            ExecutorsProvider.globalSingleTreadPoll,
            { text -> textView.text = text }) // callback on main thread
        // stub error wil be use (just throws error)
        { inputStream().bufferedReader().use { it.readText() } }// execute on threadPool
    }

    fun someFun5() {
        // does the same as the previous function but uses a pre installed thread pool
        val file = File("example.txt")
        file.runOnGlobalSinglePool({ text -> textView.text = text }) // callback on main thread
        // stub error wil be use (just throws error)
        {
            inputStream().bufferedReader().use { it.readText() } // execute on threadPool
        }
    }

    fun someFun6() {
        // print new value in textView every 1 second 100 times
        runOnGlobalSinglePool { // start task on single threadPoll use stub as result and error callbacks
            for (i in 0..100) {
                Thread.sleep(1000)
                runOnUI { textView.text = i.toString() } // execute lambda on UI thread
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ExecutorsProvider.globalSingleTreadPoll.purge()
        thread?.interrupt()
    }
}

```


See [Docs](https://dmitrystarkin.github.io/android_async_lib/)