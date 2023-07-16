package com.hirno.compose.collection.model.collection

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * The response model of collections endpoint
 */
@Parcelize
data class CollectionResponseModel @JvmOverloads constructor(
    @SerializedName("artObjects")
    var objects: ArrayList<CollectionItemModel> = arrayListOf(),
) : Parcelable

/**
 * Model of each collection item
 */
@Parcelize
@Entity(tableName = "Collections")
data class CollectionItemModel @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String = "",
    @ColumnInfo(name = "objectNumber")
    @SerializedName("objectNumber")
    var objectNumber: String = "",
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null,
    @Embedded(prefix = "webImage_")
    @SerializedName("webImage")
    var webImage: WebImage = WebImage(),
) : Parcelable {
    /**
     * Model of webImage property inside [CollectionItemModel]
     */
    @Parcelize
    data class WebImage(
        @ColumnInfo(name = "width")
        @SerializedName("width")
        var width: Int = 0,
        @ColumnInfo(name = "height")
        @SerializedName("height")
        var height: Int = 0,
        @ColumnInfo(name = "url")
        @SerializedName("url")
        var url: String? = null,
    ) : Parcelable {
        /**
         * Returns a string ratio of the collection image
         */
        val ratioString
            get() = "$width:$height"
    }
}