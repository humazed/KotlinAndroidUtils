package humazed.github.com.kotlinandroidutils

import com.google.gson.Gson


inline fun <reified T> T.toJson() = Gson().toJson(this)!!

inline fun <reified T> String.toEntity() = Gson().fromJson(this, T::class.java)!!
