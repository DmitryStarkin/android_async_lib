# A small Kotlin library for asynchronous task on Android

The library provide functions for easy execution of tasks in thread use lambda style,
and send lambda for update UI (like runOnUiThread in Android)

## Version:

0.2.0b current

Add LifeCicle support

Add HahdlerThread Executor

0.1.0b
first version

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

globalSingleTreadPoll - operations are performed on a [ThreadPool](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor) with a single thread that exists in a single instance within the application process.

globalMultiThreadPoll - operations are performed on a [ThreadPool](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor) with a starting number of threads equal to the number of processors + 1  
and a maximum number of threads equal to the number of processors * 2. this pool exists in a single instance within the application process.

newSingleThreadPoll() - the same as globalSingleTreadPoll but return new instance each time

newMultiThreadPoll() - the same as globalMultiThreadPoll but return new instance each time

newThread() - a new [Thread](https://developer.android.com/reference/java/lang/Thread) is created for execution each time

newHandlerThread() - a new [HandlerThread](https://developer.android.com/reference/android/os/HandlerThread) is created for execution each time

currentThread() - task is performed in the calling thread

mainThread() - task is performed in the main thread

Executors, in addition to global ones, can be linked to the [Lifecycle](https://developer.android.com/reference/androidx/lifecycle/Lifecycle?hl=ru) of an Activity or Fragment using the connectToLifecycle(owner: LifecycleOwner) method

There are extensions for [LifecycleOwner](https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner) that do this automatically

You can use your own implementation of the Executor (see [ThreadPoolExecutor](https://developer.android.com/reference/java/util/concurrent/ThreadPoolExecutor) for example)  
or get it using Java factory Executors See [Executors](https://developer.android.com/reference/java/util/concurrent/Executors)

For convenience the library includes extension functions for the Executor interface

Regardless of where the task is executed **callbacks are always executed in the main thread**

Code example

```kotlin
class SomeClass : AppCompatActivity() {

    private val textView: TextView = TextView(this) //for example
    var thread: Thread? = null
    private val executor =
        this.newHandlerThread() //HandlerThread executor associated with the lifecycle of this activity

    private val executor2 =
        (ExecutorsProvider.newHandlerThread() as LifecycleSupport).connectToLifecycle(this) //do the same

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
                .use { reader -> reader.readText() }// execute on new thread, 
                // it represent parameter file
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

    fun someFun7() {

        val file = File("example.txt")
        //start operation on on HandlerThread
        executor.run(
            { text -> textView.text = text }, // callback on main thread
            { e -> textView.text = e.toString() }) // callback on main thread
        {
            file.inputStream().bufferedReader()
                .use { it.readText() } // execute on threadPool, file captured in closure
        }
    }

    fun someFun8() {
        // print new value in textView every 1 second 100 times
        executor2.run { // start task on HandlerThread use stub as result and error callbacks
            for (i in 0..100) {
                Thread.sleep(1000)
                runOnUI { textView.text = i.toString() } // execute lambda on UI thread
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //(executor as HandlerThread).quit() this call is not needed here 
        // because the executor is associated with the lifecycle
        //(executor2 as HandlerThread).quit() this call is not needed here 
        // because the executor2 is associated with the lifecycle
        ExecutorsProvider.globalSingleTreadPoll.purge() //clearing the task queue
        thread?.interrupt()
    }
}

```


See [Docs](https://dmitrystarkin.github.io/android_async_lib/)