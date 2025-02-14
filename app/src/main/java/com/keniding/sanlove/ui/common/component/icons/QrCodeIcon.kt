package com.keniding.sanlove.ui.common.component.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val QrCodeIcon: ImageVector
    get() {
        if (_qrCodeIcon != null) {
            return _qrCodeIcon!!
        }
        _qrCodeIcon = materialIcon(name = "QrCode") {
            materialPath {
                moveTo(3f, 3f)
                horizontalLineTo(11f)
                verticalLineTo(11f)
                horizontalLineTo(3f)
                verticalLineTo(3f)
                close()

                moveTo(5f, 5f)
                horizontalLineTo(9f)
                verticalLineTo(9f)
                horizontalLineTo(5f)
                verticalLineTo(5f)
                close()

                moveTo(13f, 3f)
                horizontalLineTo(21f)
                verticalLineTo(11f)
                horizontalLineTo(13f)
                verticalLineTo(3f)
                close()

                moveTo(15f, 5f)
                horizontalLineTo(19f)
                verticalLineTo(9f)
                horizontalLineTo(15f)
                verticalLineTo(5f)
                close()

                moveTo(3f, 13f)
                horizontalLineTo(11f)
                verticalLineTo(21f)
                horizontalLineTo(3f)
                verticalLineTo(13f)
                close()

                moveTo(5f, 15f)
                horizontalLineTo(9f)
                verticalLineTo(19f)
                horizontalLineTo(5f)
                verticalLineTo(15f)
                close()

                moveTo(13f, 13f)
                horizontalLineTo(21f)
                verticalLineTo(21f)
                horizontalLineTo(13f)
                verticalLineTo(13f)
                close()
            }
        }
        return _qrCodeIcon!!
    }

private var _qrCodeIcon: ImageVector? = null
