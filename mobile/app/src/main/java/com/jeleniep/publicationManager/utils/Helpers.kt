package com.jeleniep.publicationManager.utils

import android.text.Editable

object Helpers {
    fun swapNullToString(str: String?): String {
        if (str != null) {
            return str
        }
        return ""
    }

    fun listOfStringsToString(list: List<String>?, separator: String = ", "): String {
        if (list != null) {
            return list!!.joinToString(separator = separator)
        }
        return ""
    }

    fun stringToEditable(str: String?): Editable {
        return Editable.Factory.getInstance().newEditable(swapNullToString(str))
    }

    fun listOfStringsToEditable(list: List<String>?): Editable {
        return Editable.Factory.getInstance().newEditable(listOfStringsToString(list))
    }

}