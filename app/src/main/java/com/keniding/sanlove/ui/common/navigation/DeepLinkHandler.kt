package com.keniding.sanlove.ui.common.navigation

object DeepLinkHandler {
    private var pendingCode: String? = null

    fun setPendingCode(code: String?) {
        pendingCode = code
    }

    fun consumePendingCode(): String? {
        val code = pendingCode
        pendingCode = null
        return code
    }
}
