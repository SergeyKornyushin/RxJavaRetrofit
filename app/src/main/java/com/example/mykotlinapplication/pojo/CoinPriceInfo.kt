package com.example.mykotlinapplication.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mykotlinapplication.utils.convertTimestampToTime
import com.google.gson.annotations.Expose
import com.example.mykotlinapplication.api.ApiFactory.BASE_IMAGE_URL

import com.google.gson.annotations.SerializedName

@Entity(tableName = "full_price_list")
data class CoinPriceInfo (
    @PrimaryKey
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromsymbol: String,

    @SerializedName("TOSYMBOL")
    @Expose
    val tosymbol: String,

    @SerializedName("PRICE")
    @Expose
    val price: Double,

    @SerializedName("LASTUPDATE")
    @Expose
    val lastupdate: Int,

    @SerializedName("HIGHDAY")
    @Expose
    val highday: Double,

    @SerializedName("LOWDAY")
    @Expose
    val lowday: Double,

    @SerializedName("LASTMARKET")
    @Expose
    val lastmarket: String,

    @SerializedName("IMAGEURL")
    @Expose
    val imageurl: String
) {
    fun getFormattedTime(): String{
        return convertTimestampToTime(lastupdate)
    }

    fun getFullImageUrl(): String{
        return BASE_IMAGE_URL + imageurl
    }
}