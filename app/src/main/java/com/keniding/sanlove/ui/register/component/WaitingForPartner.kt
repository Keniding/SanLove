package com.keniding.sanlove.ui.register.component

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.keniding.sanlove.ui.common.component.StyledButton
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard
import androidx.compose.material.icons.filled.Share
import com.keniding.sanlove.ui.common.component.icons.QrCodeIcon

@Composable
fun WaitingForPartner(code: String?) {
    val context = LocalContext.current
    var showQrDialog by remember { mutableStateOf(false) }

    fun generateQRCode(content: String, size: Int): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bitmap
    }

    if (showQrDialog && code != null) {
        Dialog(onDismissRequest = { showQrDialog = false }) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Código QR de Amor",
                        style = MaterialTheme.typography.displayMedium,
                        color = ValentineColors.DeepRose,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val qrBitmap = remember(code) {
                        generateQRCode("sanlove://connect/$code", 512)
                    }

                    Box(
                        modifier = Modifier
                            .size(256.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    StyledButton(
                        onClick = { showQrDialog = false },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            "Cerrar",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Invita a tu Amor",
            style = MaterialTheme.typography.headlineLarge.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Tu código de amor",
                    style = MaterialTheme.typography.bodyLarge,
                    color = ValentineColors.DeepRose.copy(alpha = 0.8f)
                )

                Text(
                    text = code ?: "",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 48.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = ValentineColors.DeepRose,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Comparte este código con tu pareja\npara comenzar juntos esta aventura",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = ValentineColors.DeepRose.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StyledButton(
                onClick = {
                    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Código de amor", code)
                    clipboardManager.setPrimaryClip(clip)
                    Toast.makeText(context, "Código copiado", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Copiar",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Copiar")
            }

            StyledButton(
                onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "💕 ¡Unamos nuestros corazones en SanLove! 💕\n" +
                                    "Usa este código especial: $code\n" +
                                    "O simplemente toca: sanlove://connect/$code"
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Compartir código de amor"))
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compartir")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            StyledButton(
                onClick = { showQrDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = QrCodeIcon,
                    contentDescription = "Código QR",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mostrar QR")
            }
        }
    }
}
