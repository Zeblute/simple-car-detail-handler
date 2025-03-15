package edu.msoe.rutherfordc
import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Car(
    val id: Int,
    val color: String,
    val make: String,
    val modelYear: Int,
    var purchaseDate: Date? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong().takeIf { it != -1L }?.let { Date(it) } // If purchaseDate is present, read it as a long timestamp
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(color)
        parcel.writeString(make)
        parcel.writeInt(modelYear)
        parcel.writeLong(purchaseDate?.time ?: -1L) // Write -1L if purchaseDate is null
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }
}
