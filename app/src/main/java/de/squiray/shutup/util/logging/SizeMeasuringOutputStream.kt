package de.squiray.shutup.util.logging

import java.io.IOException
import java.io.OutputStream

internal class SizeMeasuringOutputStream(private val delegate: OutputStream) : OutputStream() {

    @Volatile private var size = 0L

    fun size(): Long {
        return size
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        delegate.write(b)
        size++
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray) {
        delegate.write(b)
        size += b.size.toLong()
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        delegate.write(b, off, len)
        size += len.toLong()
    }

    @Throws(IOException::class)
    override fun flush() {
        delegate.flush()
    }

    @Throws(IOException::class)
    override fun close() {
        delegate.close()
    }

}
