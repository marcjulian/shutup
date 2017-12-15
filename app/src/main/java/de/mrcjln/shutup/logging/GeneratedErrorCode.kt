package de.mrcjln.shutup.logging

internal object GeneratedErrorCode {

    private val A_PRIME = Integer.MAX_VALUE

    fun of(e: Throwable): String {
        return format(originCode(rootCause(e))) + ':' + format(traceCode(e))
    }

    private fun rootCause(e: Throwable): Throwable {
        return if (e.cause == null) {
            e
        } else {
            e.cause!!
        }
    }

    private fun format(value: Int): String {
        var value = value
        value = value and 0xfffff xor value.ushr(20)
        value = value or 0x100000
        return Integer.toString(value, 32).substring(1).toUpperCase()
    }

    private fun traceCode(e: Throwable?): Int {
        var e = e
        var result = -0x596764e6
        while (e != null) {
            result = result * A_PRIME + originCode(e)
            for (element in e.stackTrace) {
                result = result * A_PRIME + element.className.hashCode()
                result = result * A_PRIME + element.methodName.hashCode()
            }
            e = e.cause
        }
        return result
    }

    private fun originCode(e: Throwable, stack: Array<StackTraceElement> = e.stackTrace): Int {
        var result = 0x6c528c4a
        result = result * A_PRIME + e.javaClass.name.hashCode()
        if (stack.size > 0) {
            result = result * A_PRIME + stack[0].className.hashCode()
            result = result * A_PRIME + stack[0].methodName.hashCode()
        }
        return result
    }

}
