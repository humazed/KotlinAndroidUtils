package humazed.github.com.kotlinandroidutils

import android.util.Log

/**
 * Simple logging tool for adding informative debug logs to code easily.
 * <br></br><br></br>
 * Usage: Add `Investigator.log(this)` (or its variants) to checkpoints.

 * @author Gabor_Keszthelyi
 */
object Investigator {

    /**
     * The tag used for the logcat messages.
     */
    var tag = "Investigator"
    /**
     * Number of the extra stacktrace elements (class + method name) logged from the stacktrace created at the log() call.
     * Zero means no extra method is logged, only the watched one.
     *
     *
     */
    var defaultMethodDepth = 0
    /**
     * Log the thread name or not.
     */
    var threadNameEnabled = true
    /**
     * Log level used for logcat.
     */
    var logLevel = Log.DEBUG
    /**
     * Remove the package name from the instance's toString value for easier readability.
     */
    var removePackageName = true
    /**
     * When enabled, an extra word ([.anonymousClassHighlightWord]) is inserted into anonymous classes' toString values to help notice them more easily.
     *
     * e.g.: `FirstFragment$1@1bf1abe3.onClick()` --&gt; `FirstFragment_INNNER_1@1bf1abe3.onClick()`
     */
    private val highlightAnonymousClasses = true
    private val anonymousClassHighlightWord = "_ANONYMOUS_"

    private val patternThreadName = "[%s] "
    private val patternInstanceAndMethod = "%s.%s()"
    private val patternComment = " | %s"
    private val patternVariableNameAndValue = " | %s = %s"
    private val messageStopwatchStarted = " | 0 ms (STOPWATCH STARTED)"
    private val patternElapsedTime = " | %s ms"
    private val patternStacktraceLine = "\tat %s"
    private val newLine = "\n"

    private val STACKTRACE_INDEX_OF_CALLING_METHOD = 3 // fixed value, need to update only if the 'location' of the stack trace fetching code changes
    private val ANONYMOUS_CLASS_TOSTRING_SYMBOL = "$"

    private var isStopWatchGoing: Boolean = false

    /**
     * Logs the calling instance and method name.
     *
     *
     * **Example**
     * <br></br>Code:
     * <br></br>`Investigator.log(this);`
     * <br></br>Log:
     * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate()`

     * @param instance the calling object instance
     */
    fun log(instance: Any) {
        doLog(instance, null, false, null, defaultMethodDepth)
    }

    /**
     * Logs the calling instance and method name, and the comment.
     *
     *
     * **Example**
     * <br></br>Code:
     * <br></br>`Investigator.log(this, "some comment");`
     * <br></br>Log:
     * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | some comment`

     * @param instance the calling object instance
     * *
     * @param comment  extra comment message
     */
    fun log(instance: Any, comment: String) {
        doLog(instance, comment, false, null, defaultMethodDepth)
    }

    /**
     * Logs the calling instance and method name, and the variable names and values.
     *
     *
     * **Example**
     * <br></br>Code:
     * <br></br>`Investigator.log(this, "fruit", fruit);`
     * <br></br>`Investigator.log(this, "fruit", fruit, "color", color);`
     * <br></br>Log:
     * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | fruit = cherry`
     * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | fruit = cherry | color = red`

     * @param instance               the calling object instance
     * *
     * @param variableNamesAndValues variable name and value pairs
     */
    fun log(instance: Any, vararg variableNamesAndValues: Any) {
        doLog(instance, null, false, variableNamesAndValues, defaultMethodDepth)
    }

    /**
     * Logs the calling instance and method name, and the stacktrace to the given method depth.
     *
     *
     * **Example**
     * <br></br>Code:
     * <br></br>`Investigator.log(this, 3);`
     * <br></br>Log:
     * <br></br>`D/Investigator: [main] MainActivity@cea8175.stackTrace()<br></br>
     * &nbsp;&nbsp;&nbsp;&nbsp;at gk.android.investigator.sample.MainActivity.access$500(MainActivity.java:20)<br></br>
     * &nbsp;&nbsp;&nbsp;&nbsp;at gk.android.investigator.sample.MainActivity$7.onClick(MainActivity.java:87)<br></br>
     * &nbsp;&nbsp;&nbsp;&nbsp;at android.view.View.performClick(View.java:5204)`

     * @param instance    the calling object instance
     * *
     * @param methodDepth the number of methods to log from the stacktrace
     */
    fun log(instance: Any, methodDepth: Int?) {
        doLog(instance, null, false, null, methodDepth)
    }

    /**
     * Starts an internal stopwatch and the consequent log calls will print the time elapsed since this call.
     * Calling it multiple times restarts the stopwatch.
     *
     *
     * **Example**
     * <br></br>Code:
     * <br></br>`Investigator.startStopWatch(this);`
     * <br></br>`...`
     * <br></br>`Investigator.log(this);`
     * <br></br>Log:
     * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | 0 ms (STOPWATCH STARTED)`
     * <br></br>`D/Investigator: NetworkController@788dc5c.onJobFinished() | 126 ms`

     * @param instance the calling object instance
     */
    fun startStopWatch(instance: Any) {
        StopWatch.startStopWatch()
        isStopWatchGoing = true
        doLog(instance, null, true, null, defaultMethodDepth)
    }

    /**
     * Stop logging times (started by [Investigator.startStopWatch] from this point on.
     */
    fun stopLoggingTimes() {
        isStopWatchGoing = false
    }

    private fun doLog(instance: Any, comment: String?, hasStopWatchJustStarted: Boolean, variableNamesAndValues: Array<out Any>?, methodDepth: Int?) {
        val stackTrace = stackTrace
        val msg = StringBuilder()
        if (threadNameEnabled) {
            msg.append(threadName())
        }
        msg.append(instanceAndMethodName(instance, stackTrace))
        if (comment != null) {
            msg.append(commentMessage(comment))
        }
        if (variableNamesAndValues != null) {
            msg.append(variablesMessage(*variableNamesAndValues))
        }
        if (hasStopWatchJustStarted) {
            msg.append(messageStopwatchStarted)
        }
        if (isStopWatchGoing && !hasStopWatchJustStarted) {
            msg.append(timeElapsedMessage())
        }
        if (methodDepth!! > 0) {
            msg.append(extraStackTraceLines(stackTrace, methodDepth))
        }
        logText(msg)
    }

    private // new Exception().getStackTrace() is faster than Thread.currentThread().getStackTrace(), see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6375302
    val stackTrace: Array<StackTraceElement>
        get() = Exception().stackTrace

    private fun threadName(): String {
        return String.format(patternThreadName, Thread.currentThread().name)
    }

    private fun instanceAndMethodName(instance: Any, stackTrace: Array<StackTraceElement>): String {
        val methodName = stackTrace[STACKTRACE_INDEX_OF_CALLING_METHOD].methodName
        var instanceName = instance.toString()
        if (removePackageName) {
            instanceName = removePackageName(instanceName)
        }
        instanceName = checkAndHighlightAnonymousClass(instanceName)
        return String.format(patternInstanceAndMethod, instanceName, methodName)
    }

    // VisibleForTesting
    internal fun removePackageName(instanceName: String): String {
        val lastDotIndex = instanceName.lastIndexOf(".")
        if (lastDotIndex < 0 || lastDotIndex == instanceName.length - 1) {
            return instanceName
        } else {
            return instanceName.substring(lastDotIndex + 1)
        }
    }

    // VisibleForTesting
    internal fun checkAndHighlightAnonymousClass(instanceName: String): String {
        if (!highlightAnonymousClasses) {
            return instanceName
        }
        val symbolIndex = instanceName.indexOf(ANONYMOUS_CLASS_TOSTRING_SYMBOL)
        val hasSymbolPlusDigit = symbolIndex > 0 && instanceName.length > symbolIndex + 1 && Character.isDigit(instanceName[symbolIndex + 1])
        if (hasSymbolPlusDigit) {
            return StringBuilder(instanceName).deleteCharAt(symbolIndex).insert(symbolIndex, anonymousClassHighlightWord).toString()
        } else {
            return instanceName
        }
    }

    private fun commentMessage(comment: String): String {
        return String.format(patternComment, comment)
    }

    private fun variablesMessage(vararg variableNamesAndValues: Any): StringBuilder {
        try {
            val result = StringBuilder()
            var i = 0
            while (i < variableNamesAndValues.size) {
                val variableName = variableNamesAndValues[i]
                val variableValue = variableNamesAndValues[++i] // Will fail on odd number of params deliberately
                val variableMessage = String.format(patternVariableNameAndValue, variableName, variableValue)
                result.append(variableMessage)
                i++
            }
            return result
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw IllegalArgumentException("Missed to add variable names and values in pairs? There has to be an even number of the 'variableNamesAndValues' varargs parameters).", e)
        }

    }

    private fun timeElapsedMessage(): String {
        return String.format(patternElapsedTime, StopWatch.elapsedTimeInMillis)
    }

    private fun extraStackTraceLines(stackTrace: Array<StackTraceElement>, methodDepth: Int?): StringBuilder {
        val extraLines = StringBuilder()
        var i = STACKTRACE_INDEX_OF_CALLING_METHOD + 1
        while (i <= STACKTRACE_INDEX_OF_CALLING_METHOD + methodDepth!! && i < stackTrace.size) {
            extraLines.append(newLine).append(stackTraceLine(stackTrace[i]))
            i++
        }
        return extraLines
    }

    private fun stackTraceLine(stackTraceElement: StackTraceElement): String {
        return String.format(patternStacktraceLine, stackTraceElement.toString())
    }

    private fun logText(message: StringBuilder) {
        Log.println(logLevel, tag, message.toString())
    }

    internal object StopWatch {

        private var startTimeInMillis: Long = 0

        fun startStopWatch() {
            startTimeInMillis = System.currentTimeMillis()
        }

        val elapsedTimeInMillis: Long
            get() = System.currentTimeMillis() - startTimeInMillis

    }
}












