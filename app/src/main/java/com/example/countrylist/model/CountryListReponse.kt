package com.example.countrylist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/***
 *  Country List Response model class
 */
class CountryListResponse : ArrayList<CountryListResponseItem>()
@Parcelize
data class CountryListResponseItem(
    val flags: Flags,
    val name: Name
) : Parcelable


@Parcelize
data class Flags(
    val alt: String,
    val png: String,
    val svg: String
) : Parcelable


@Parcelize
data class Name(
    val common: String,
    val nativeName: NativeName,
    val official: String
) : Parcelable

@Parcelize
data class NativeName(
    val spa: Spa
) : Parcelable

@Parcelize
data class Spa(
    val common: String,
    val official: String
) : Parcelable