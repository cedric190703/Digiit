package com.example.digiit.data.ticket

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.digiit.data.Tags
import com.example.digiit.utils.BinaryInputStream
import com.example.digiit.utils.BinaryOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset


class LocalTicket(private val file: File) : Ticket() {
    fun load() {
        val stream = BinaryInputStream(file.inputStream())
        lastEdit = stream.readLong()
        title = stream.readString()
        type = Tags.valueOf(stream.readCString())
        tag = stream.readString()
        price = stream.readFloat()
        rating = stream.readFloat()
        date = LocalDateTime.ofEpochSecond(stream.readLong(), 0, ZoneOffset.UTC)
        comment = stream.readString()
        colorText = readColor(stream)
        colorIcon = readColor(stream)
        colorTag = readColor(stream)
        stream.close()
    }

    override fun reload() {
        TODO("Not yet implemented")
    }

    override fun save() {
        val stream = BinaryOutputStream(file.outputStream())
        stream.writeLong(lastEdit)
        stream.writeString(title)
        stream.writeCString(type.name)
        stream.writeString(tag)
        stream.writeFloat(price)
        stream.writeFloat(rating)
        stream.writeLong(date.toEpochSecond(ZoneOffset.UTC))
        stream.writeString(comment)
        writeColor(stream, colorText)
        writeColor(stream, colorTag)
        writeColor(stream, colorTag)
        stream.close()
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    private fun readColor(stream: BinaryInputStream): Color {
        return Color(stream.readInt())
    }

    private fun writeColor(stream: BinaryOutputStream, color: Color) {
        stream.writeInt(color.toArgb())
    }
}