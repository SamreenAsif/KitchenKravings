package com.example.recipebook

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun shareOnWhatsApp(context: Context) {
    val textToShare = "Check out this amazing recipe!"

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToShare)
        type = "text/plain"
    }

    sendIntent.setPackage("com.whatsapp")

    if (sendIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(sendIntent)
    } else {
        Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
    }
}
