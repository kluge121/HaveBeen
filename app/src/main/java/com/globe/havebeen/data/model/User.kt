package com.globe.havebeen.data.model

import java.io.Serializable

/**
 * Created by baeminsu on 01/09/2018.
 */

data class User(
        var name: String? = "",
        var email: String? = "",
        var profileUrl: String? = "",
        var isSelected: Boolean = false
) : Serializable
