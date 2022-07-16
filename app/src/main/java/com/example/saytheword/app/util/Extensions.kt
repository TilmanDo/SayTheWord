package com.example.saytheword.app.util

import android.content.res.Resources

class Extensions {

    companion object{

        fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    }
}