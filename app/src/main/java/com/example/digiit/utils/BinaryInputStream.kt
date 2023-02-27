package com.example.digiit.utils

import java.io.ByteArrayOutputStream
import java.io.EOFException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*


typealias ReadCallback<T> = (BinaryInputStream) -> T

@Suppress("NOTHING_TO_INLINE")

@OptIn(ExperimentalUnsignedTypes::class)
class BinaryInputStream(private val stream: InputStream) {
    private val bytes: ByteArray = ByteArray(8)


    class NotEnoughSpaceInArrayException : Exception("Not enough space in destination array")



    // Utils

    private fun readFixed(len: Int) {
        readFixedBytes(len, bytes)
    }

    private fun checkBufferSpace(inLen: Int, outOffset: Int, outLen: Int) {
        if (outOffset + inLen > outLen)
            throw NotEnoughSpaceInArrayException()
    }

    fun close() {
        stream.close()
    }



    // Bytes

    private fun readNBytes(b: ByteArray, off: Int, len: Int): Int {
        var n = 0
        while (n < len) {
            val count = stream.read(b, off + n, len - n)
            if (count < 0) break
            n += count
        }
        return n
    }

    fun readFixedBytes(len: Int, buff: ByteArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        if (readNBytes(buff, offset, len) != len)
            throw EOFException()
    }

    fun readFixedBytes(len: Int): ByteArray {
        val result = ByteArray(len)
        if (readNBytes(result, 0, len) != len)
            throw EOFException()
        return result
    }

    fun readBytes(buff: ByteArray, offset: Int = 0) = readFixedBytes(readInt(), buff, offset)

    fun readBytes() = readFixedBytes(readInt())



    // UBytes

    fun readFixedUBytes(len: Int, buff: UByteArray, offset: Int = 0) = readFixedBytes(len, buff.asByteArray(), offset)

    fun readFixedUBytes(len: Int) = readFixedBytes(len).asUByteArray()

    fun readUBytes(buff: UByteArray, offset: Int = 0) = readFixedUBytes(readInt(), buff, offset)

    fun readUBytes() = readFixedUBytes(readInt())



    // Shorts

    fun readFixedShorts(len: Int, buff: ShortArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readShort()
    }

    fun readFixedShorts(len: Int) = ShortArray(len) { _ -> readShort() }

    fun readShorts(buff: ShortArray, offset: Int = 0) = readFixedShorts(readInt(), buff, offset)

    fun readShorts() = readFixedShorts(readInt())



    // UShorts

    fun readFixedUShorts(len: Int, buff: UShortArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readUShort()
    }

    fun readFixedUShorts(len: Int) = UShortArray(len) { _ -> readUShort() }

    fun readUShorts(buff: UShortArray, offset: Int = 0) = readFixedUShorts(readInt(), buff, offset)

    fun readUShorts() = readFixedUShorts(readInt())



    // Ints

    fun readFixedInts(len: Int, buff: IntArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readInt()
    }

    fun readFixedInts(len: Int) = IntArray(len) { _ -> readInt() }

    fun readInts(buff: IntArray, offset: Int = 0) = readFixedInts(readInt(), buff, offset)

    fun readInts() = readFixedInts(readInt())



    // UInts

    fun readFixedUInts(len: Int, buff: UIntArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readUInt()
    }

    fun readFixedUInts(len: Int) = UIntArray(len) { _ -> readUInt() }

    fun readUInts(buff: UIntArray, offset: Int = 0) = readFixedUInts(readInt(), buff, offset)

    fun readUInts() = readFixedUInts(readInt())



    // Longs

    fun readFixedLongs(len: Int, buff: LongArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readLong()
    }

    fun readFixedLongs(len: Int) = LongArray(len) { _ -> readLong() }

    fun readLongs(buff: LongArray, offset: Int = 0) = readFixedLongs(readInt(), buff, offset)

    fun readLongs() = readFixedLongs(readInt())



    // ULongs

    fun readFixedULongs(len: Int, buff: ULongArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readULong()
    }

    fun readFixedULongs(len: Int) = ULongArray(len) { _ -> readULong() }

    fun readULongs(buff: ULongArray, offset: Int = 0) = readFixedULongs(readInt(), buff, offset)

    fun readULongs() = readFixedULongs(readInt())



    // Floats

    fun readFixedFloats(len: Int, buff: FloatArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readFloat()
    }

    fun readFixedFloats(len: Int) = FloatArray(len) { _ -> readFloat() }

    fun readFloats(buff: FloatArray, offset: Int = 0) = readFixedFloats(readInt(), buff, offset)

    fun readFloats() = readFixedFloats(readInt())



    // Doubles

    fun readFixedDoubles(len: Int, buff: DoubleArray, offset: Int = 0) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = readDouble()
    }

    fun readFixedDoubles(len: Int) = DoubleArray(len) { _ -> readDouble() }

    fun readDoubles(buff: DoubleArray, offset: Int = 0) = readFixedDoubles(readInt(), buff, offset)

    fun readDoubles() = readFixedDoubles(readInt())



    // Generic array

    fun <T> readFixedArray(len: Int, buff: Array<T>, offset: Int, reader: ReadCallback<T>) {
        checkBufferSpace(len, offset, buff.size)
        for (i in offset until len + offset)
            buff[i] = reader(this)
    }

    inline fun <reified T> readFixedArray(len: Int, reader: ReadCallback<T>) = Array(len) { _ -> reader(this) }

    fun <T> readArray(buff: Array<T>, offset: Int, reader: ReadCallback<T>) =
        readFixedArray(readInt(), buff, offset, reader)

    inline fun <reified T> readArray(reader: ReadCallback<T>) = readFixedArray(readInt(), reader)



    // UUID

    fun readUUID() = UUID(readLongLE(), readLongLE())



    // String

    // Read a null terminated string (can't contain null characters)
    fun readCString(encoding: Charset = Charsets.UTF_8): String {
        val buff = ByteArrayOutputStream()
        while (true) {
            val byte = stream.read()
            if (byte == -1)
                throw EOFException()
            if (byte == 0)
                return buff.toString(encoding.name())
            buff.write(byte)
        }
    }

    // Read a string (can contain null characters)
    fun readString(encoding: Charset = Charsets.UTF_8): String {
        val len = readIntLE()
        return String(readFixedBytes(len), encoding)
    }



    // Byte

    fun readUByte(): UByte {
        val byte = stream.read()
        if (byte == -1)
            throw EOFException()
        return byte.toUByte()
    }

    fun readByte(): Byte {
        val byte = stream.read()
        if (byte == -1)
            throw EOFException()
        return byte.toByte()
    }



    // Boolean

    fun readBoolean(): Boolean {
        readFixed(1)
        return bytes[0] != 0.toByte()
    }



    // Float

    fun readFloat(): Float {
        return Float.fromBits(readIntBE())
    }



    // Double

    fun readDouble(): Double {
        return Double.fromBits(readLongBE())
    }



    // Short (2 bytes size)

    fun readUShortLE(): UShort {
        readFixed(2)
        return (
                (bytes[0].toUInt()      ) or
                        (bytes[1].toUInt() shl 8)
                ).toUShort()
    }

    fun readShortLE(): Short {
        readFixed(2)
        return (
                (bytes[0].toInt()      ) or
                        (bytes[1].toInt() shl 8)
                ).toShort()
    }

    fun readUShortBE(): UShort {
        readFixed(2)
        return (
                (bytes[1].toUInt()      ) or
                        (bytes[0].toUInt() shl 8)
                ).toUShort()
    }

    fun readShortBE(): Short {
        readFixed(2)
        return (
                (bytes[1].toInt()      ) or
                        (bytes[0].toInt() shl 8)
                ).toShort()
    }



    // Int (4 bytes size)

    fun readUIntLE(): UInt {
        readFixed(4)
        return (
                (bytes[0].toUInt()       ) or
                        (bytes[1].toUInt() shl  8) or
                        (bytes[2].toUInt() shl 16) or
                        (bytes[3].toUInt() shl 24)
                )
    }

    fun readIntLE(): Int {
        readFixed(4)
        return (
                (bytes[0].toInt()       ) or
                        (bytes[1].toInt() shl  8) or
                        (bytes[2].toInt() shl 16) or
                        (bytes[3].toInt() shl 24)
                )
    }

    fun readUIntBE(): UInt {
        readFixed(4)
        return (
                (bytes[3].toUInt()       ) or
                        (bytes[2].toUInt() shl  8) or
                        (bytes[1].toUInt() shl 16) or
                        (bytes[0].toUInt() shl 24)
                )
    }

    fun readIntBE(): Int {
        readFixed(4)
        return (
                (bytes[3].toInt()       ) or
                        (bytes[2].toInt() shl  8) or
                        (bytes[1].toInt() shl 16) or
                        (bytes[0].toInt() shl 24)
                )
    }



    // Long (8 bytes size)

    fun readULongLE(): ULong {
        readFixed(8)
        return (
                (bytes[0].toULong()       ) or
                        (bytes[1].toULong() shl  8) or
                        (bytes[2].toULong() shl 16) or
                        (bytes[3].toULong() shl 24) or
                        (bytes[4].toULong() shl 32) or
                        (bytes[5].toULong() shl 40) or
                        (bytes[6].toULong() shl 48) or
                        (bytes[7].toULong() shl 56)
                )
    }

    fun readLongLE(): Long {
        readFixed(8)
        return (
                (bytes[0].toLong()       ) or
                        (bytes[1].toLong() shl  8) or
                        (bytes[2].toLong() shl 16) or
                        (bytes[3].toLong() shl 24) or
                        (bytes[4].toLong() shl 32) or
                        (bytes[5].toLong() shl 40) or
                        (bytes[6].toLong() shl 48) or
                        (bytes[7].toLong() shl 56)
                )
    }

    fun readULongBE(): ULong {
        readFixed(8)
        return (
                (bytes[0].toULong()       ) or
                        (bytes[1].toULong() shl  8) or
                        (bytes[2].toULong() shl 16) or
                        (bytes[3].toULong() shl 24) or
                        (bytes[4].toULong() shl 32) or
                        (bytes[5].toULong() shl 40) or
                        (bytes[6].toULong() shl 48) or
                        (bytes[7].toULong() shl 56)
                )
    }

    fun readLongBE(): Long {
        readFixed(8)
        return (
                (bytes[0].toLong()       ) or
                        (bytes[1].toLong() shl  8) or
                        (bytes[2].toLong() shl 16) or
                        (bytes[3].toLong() shl 24) or
                        (bytes[4].toLong() shl 32) or
                        (bytes[5].toLong() shl 40) or
                        (bytes[6].toLong() shl 48) or
                        (bytes[7].toLong() shl 56)
                )
    }



    // Alias

    inline fun readShort() = readShortLE()
    inline fun readUShort() = readUShortLE()

    inline fun readInt() = readIntLE()
    inline fun readUInt() = readUIntLE()

    inline fun readLong() = readLongLE()
    inline fun readULong() = readULongLE()
}
