package com.hirno.compose.collection.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Model of server-returned errors
 */
@Parcelize
data class ErrorResponseModel @JvmOverloads constructor(
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("traceId")
    var traceId: String? = null,
    @SerializedName("error")
    var error: Throwable? = null,
) : Parcelable