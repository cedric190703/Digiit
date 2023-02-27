package com.example.digiit.utils

import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*


typealias WriteCallback<T> = (BinaryOutputStream, T) -> Unit

@Suppress("NOTHING_TO_INLINE")

@OptIn(ExperimentalUnsignedTypes::class)
class BinaryOutputStream(private val stream: OutputStream) {
    private val bytes: ByteArray = ByteArray(8)


    class NotEnoughDataInArrayException : Exception("Not enough data in source array")



    // Utils

    private fun writeFixed(len: Int) {
        writeFixedBytes(bytes, len)
    }

    private fun checkBufferData(inLen: Int, inOffset: Int, buffLen: Int) {
        if (buffLen < inOffset + inLen)
            throw NotEnoughDataInArrayException()
    }

    fun close() {
        stream.close()
    }



    // Bytes

    fun writeFixedBytes(buff: ByteArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        stream.write(buff, offset, len)
    }

    fun writeBytes(buff: ByteArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        writeInt(len)
        stream.write(buff, offset, len)
    }



    // UBytes

    fun writeFixedUBytes(buff: UByteArray, len: Int = buff.size, offset: Int = 0) = writeFixedBytes(buff.asByteArray(), len, offset)

    fun writeUBytes(buff: UByteArray, len: Int = buff.size, offset: Int = 0) = writeBytes(buff.asByteArray(), len, offset)



    // Shorts

    fun writeFixedShorts(buff: ShortArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeShort(buff[i])
    }

    fun writeShorts(buff: ShortArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedShorts(buff, len, offset)
    }



    // UShorts

    fun writeFixedUShorts(buff: UShortArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeUShort(buff[i])
    }

    fun writeUShorts(buff: UShortArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedUShorts(buff, len, offset)
    }



    // Ints

    fun writeFixedInts(buff: IntArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeInt(buff[i])
    }

    fun writeInts(buff: IntArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedInts(buff, len, offset)
    }



    // UInts

    fun writeFixedUInts(buff: UIntArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeUInt(buff[i])
    }

    fun writeUInts(buff: UIntArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedUInts(buff, len, offset)
    }



    // Longs

    fun writeFixedLongs(buff: LongArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeLong(buff[i])
    }

    fun writeLongs(buff: LongArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedLongs(buff, len, offset)
    }



    // ULong

    fun writeFixedULongs(buff: ULongArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeULong(buff[i])
    }

    fun writeShorts(buff: ULongArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedULongs(buff, len, offset)
    }



    // Floats

    fun writeFixedFloats(buff: FloatArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeFloat(buff[i])
    }

    fun writeFloats(buff: FloatArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedFloats(buff, len, offset)
    }



    // Doubles

    fun writeFixedDoubles(buff: DoubleArray, len: Int = buff.size, offset: Int = 0) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writeDouble(buff[i])
    }

    fun writeDoubles(buff: DoubleArray, len: Int = buff.size, offset: Int = 0) {
        writeInt(len)
        writeFixedDoubles(buff, len, offset)
    }



    // Generic array

    fun <T> writeFixedArray(buff: Array<T>, len: Int = buff.size, offset: Int, writer: WriteCallback<T>) {
        checkBufferData(len, offset, buff.size)
        for (i in offset until len + offset)
            writer(this, buff[i])
    }

    fun <T> writeArray(buff: Array<T>, len: Int = buff.size, offset: Int, writer: WriteCallback<T>) {
        writeInt(len)
        writeFixedArray(buff, len, offset, writer)
    }



    // UUID

    fun writeUUID(uuid: UUID) {
        writeLong(uuid.mostSignificantBits)
        writeLong(uuid.leastSignificantBits)
    }



    // String

    // Read a null terminated string (can't contain null characters)
    fun writeCString(string: String, encoding: Charset = Charsets.UTF_8) {
        writeFixedBytes(string.toByteArray(encoding))
        writeByte(0)
    }

    // Read a string (can contain null characters)
    fun writeString(string: String, encoding: Charset = Charsets.UTF_8) {
        writeBytes(string.toByteArray(encoding))
    }



    // Byte

    fun writeUByte(value: UByte) {
        bytes[0] = value.toByte()
        writeFixed(1)
    }

    fun writeByte(value: Byte) {
        bytes[0] = value
        writeFixed(1)
    }




    // Boolean

    fun writeBoolean(value: Boolean) {
        bytes[0] = if(value) 1 else 0
        writeFixed(1)
    }



    // Float

    fun writeFloat(value: Float) {
        writeInt(value.toRawBits())
    }



    // Double

    fun writeDouble(value: Double) {
        writeLong(value.toRawBits())
    }



    // Short (2 bytes size)

    fun writeUShortLE(value: UShort) {
        bytes[0] = value.toByte()
        bytes[1] = (value.toUInt() shr 8).toByte()
        writeFixed(2)
    }

    fun writeShortLE(value: Short) {
        bytes[0] = value.toByte()
        bytes[1] = (value.toUInt() shr 8).toByte()
        writeFixed(2)
    }

    fun writeUShortBE(value: UShort) {
        bytes[1] = value.toByte()
        bytes[0] = (value.toUInt() shr 8).toByte()
        writeFixed(2)
    }

    fun writeShortBE(value: UShort) {
        bytes[1] = value.toByte()
        bytes[0] = (value.toUInt() shr 8).toByte()
        writeFixed(2)
    }



    // Int (4 bytes size)

    fun writeUIntLE(value: UInt) {
        bytes[0] = value.toByte()
        bytes[1] = (value shr 8).toByte()
        bytes[2] = (value shr 16).toByte()
        bytes[3] = (value shr 24).toByte()
        writeFixed(4)
    }

    fun writeIntLE(value: Int) {
        bytes[0] = value.toByte()
        bytes[1] = (value shr 8).toByte()
        bytes[2] = (value shr 16).toByte()
        bytes[3] = (value shr 24).toByte()
        writeFixed(4)
    }

    fun writeUIntBE(value: UInt) {
        bytes[3] = value.toByte()
        bytes[2] = (value shr 8).toByte()
        bytes[1] = (value shr 16).toByte()
        bytes[0] = (value shr 24).toByte()
        writeFixed(4)
    }

    fun writeIntBE(value: Int) {
        bytes[3] = value.toByte()
        bytes[2] = (value shr 8).toByte()
        bytes[1] = (value shr 16).toByte()
        bytes[0] = (value shr 24).toByte()
        writeFixed(4)
    }



    // Long (8 bytes size)

    fun writeULongLE(value: ULong) {
        bytes[0] = value.toByte()
        bytes[1] = (value shr  8).toByte()
        bytes[2] = (value shr 16).toByte()
        bytes[3] = (value shr 24).toByte()
        bytes[4] = (value shr 32).toByte()
        bytes[5] = (value shr 40).toByte()
        bytes[6] = (value shr 48).toByte()
        bytes[7] = (value shr 56).toByte()
        writeFixed(8)
    }

    fun writeLongLE(value: Long) {
        bytes[0] = value.toByte()
        bytes[1] = (value shr  8).toByte()
        bytes[2] = (value shr 16).toByte()
        bytes[3] = (value shr 24).toByte()
        bytes[4] = (value shr 32).toByte()
        bytes[5] = (value shr 40).toByte()
        bytes[6] = (value shr 48).toByte()
        bytes[7] = (value shr 56).toByte()
        writeFixed(8)
    }

    fun writeULongBE(value: ULong) {
        bytes[7] = value.toByte()
        bytes[6] = (value shr  8).toByte()
        bytes[5] = (value shr 16).toByte()
        bytes[4] = (value shr 24).toByte()
        bytes[3] = (value shr 32).toByte()
        bytes[2] = (value shr 40).toByte()
        bytes[1] = (value shr 48).toByte()
        bytes[0] = (value shr 56).toByte()
        writeFixed(8)
    }

    fun writeLongBE(value: Long) {
        bytes[7] = value.toByte()
        bytes[6] = (value shr  8).toByte()
        bytes[5] = (value shr 16).toByte()
        bytes[4] = (value shr 24).toByte()
        bytes[3] = (value shr 32).toByte()
        bytes[2] = (value shr 40).toByte()
        bytes[1] = (value shr 48).toByte()
        bytes[0] = (value shr 56).toByte()
        writeFixed(8)
    }



    // Alias

    fun writeShort(value: Short) = writeShortLE(value)
    fun writeUShort(value: UShort) = writeUShortLE(value)

    fun writeInt(value: Int) = writeIntLE(value)
    fun writeUInt(value: UInt) = writeUIntLE(value)

    fun writeLong(value: Long) = writeLongLE(value)
    fun writeULong(value: ULong) = writeULongLE(value)
}