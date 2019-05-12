package com.naldana.ejemplo10

import android.os.Parcel
import android.os.Parcelable

data class Coin (
    val value: String = "N/A",
    val value_us: String="N/A",
    val year: String="N/A",
    val review: String="N/A",
    val isAvaliable: Boolean=false,
    val img: String="N/A",
    val _id: String="N/A",
    val name: String="N/A",
    val country: String="N/A",
    val __v: Int=0,
    val imgBanderaPais: String="N/A"
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
        parcel.writeString(value_us)
        parcel.writeString(year)
        parcel.writeString(review)
        parcel.writeByte(if (isAvaliable) 1 else 0)
        parcel.writeString(img)
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeInt(__v)
        parcel.writeString(imgBanderaPais)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }
}