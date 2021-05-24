package com.yoshitoke.weatheringwithyou.utils

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

object AssetsLoader {
    fun loadJSONFromAsset(context: Context, fileName: String): String {
        val json: String?
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}
