package com.top.kjb.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_main_item() :baseBean(), Parcelable {
    var id=0
    var name=""
    var distance=""
    var gDescribe=""
    var comprehensiveScore = 0f
    var gymID=0
    var pic=""
    var headImg=""
    var address=""
    var lat="0"
    var lng="0"
    var openYear=""
    var area=""
    var seatingCapacity=""
    var instrumentTapNum=""
    lateinit var businessHours:ArrayList<String>

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        distance = parcel.readString()
        gDescribe = parcel.readString()
        comprehensiveScore = parcel.readFloat()
        gymID = parcel.readInt()
        pic = parcel.readString()
        address = parcel.readString()
        lat = parcel.readString()
        lng = parcel.readString()
        openYear = parcel.readString()
        area = parcel.readString()
        seatingCapacity = parcel.readString()
        instrumentTapNum = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(distance)
        parcel.writeString(gDescribe)
        parcel.writeFloat(comprehensiveScore)
        parcel.writeInt(gymID)
        parcel.writeString(pic)
        parcel.writeString(address)
        parcel.writeString(lat)
        parcel.writeString(lng)
        parcel.writeString(openYear)
        parcel.writeString(area)
        parcel.writeString(seatingCapacity)
        parcel.writeString(instrumentTapNum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<bean_main_item> {
        override fun createFromParcel(parcel: Parcel): bean_main_item {
            return bean_main_item(parcel)
        }

        override fun newArray(size: Int): Array<bean_main_item?> {
            return arrayOfNulls(size)
        }
    }


}