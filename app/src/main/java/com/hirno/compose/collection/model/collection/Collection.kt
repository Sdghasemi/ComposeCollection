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
    val objects: ArrayList<CollectionItemModel> = arrayListOf(),
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
    val id: String = "",
    @ColumnInfo(name = "objectNumber")
    @SerializedName("objectNumber")
    val objectNumber: String = "",
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String? = null,
    @Embedded(prefix = "webImage_")
    @SerializedName("webImage")
    val webImage: WebImage = WebImage(),
) : Parcelable {
    /**
     * Model of webImage property inside [CollectionItemModel]
     */
    @Parcelize
    data class WebImage(
        @ColumnInfo(name = "width")
        @SerializedName("width")
        val width: Int = 0,
        @ColumnInfo(name = "height")
        @SerializedName("height")
        val height: Int = 0,
        @ColumnInfo(name = "url")
        @SerializedName("url")
        val url: String? = null,
    ) : Parcelable {
        /**
         * Returns a string ratio of the collection image
         */
        val ratioString
            get() = "$width:$height"
    }
}