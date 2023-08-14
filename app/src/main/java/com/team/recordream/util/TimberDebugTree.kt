package com.team.recordream.util

import timber.log.Timber

class TimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement) =
        "${element.fileName}:${element.lineNumber}#${element.methodName}"
}
