package com.hieuminh.chatapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class WordModel() {

     var content: String? = null
     var time: Date? = null
     var type: Int? = null
}
