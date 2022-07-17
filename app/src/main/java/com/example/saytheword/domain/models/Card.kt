package com.example.saytheword.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(val word: String): Parcelable
