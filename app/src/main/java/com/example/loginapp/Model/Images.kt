package com.example.loginapp.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Images {
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("url")
    @Expose
    private var url: String? = null
    fun getId(): Int? {
        return id
    }

    fun getTitle(): String? {
        return title
    }

    fun getUrl(): String? {
        return url
    }
}