package com.top.kjb.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_search_item() :baseBean(), Parcelable{
    var gymID=0
  var gymName=""
   var gymAddress=""
    var lat=""
    var lng=""

    constructor(parcel: Parcel) : this() {
        gymID = parcel.readInt()
        gymName = parcel.readString()
        gymAddress = parcel.readString()
        lat = parcel.readString()
        lng = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(gymID)
        parcel.writeString(gymName)
        parcel.writeString(gymAddress)
        parcel.writeString(lat)
        parcel.writeString(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<bean_search_item> {
        override fun createFromParcel(parcel: Parcel): bean_search_item {
            return bean_search_item(parcel)
        }

        override fun newArray(size: Int): Array<bean_search_item?> {
            return arrayOfNulls(size)
        }
    }

}