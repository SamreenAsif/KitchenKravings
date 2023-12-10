package com.example.recipebook.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import kotlinx.coroutines.tasks.await

class StorageUtil {
    companion object {
        suspend fun uploadToStorage(uri: Uri, context: Context, type: String): String {
            val storage = Firebase.storage
            val storageRef = storage.reference

            val uniqueImageName = UUID.randomUUID().toString()
            val fileExtension = if (type == "image") "jpg" else "mp4"
            val spaceRef = storageRef.child("$type/$uniqueImageName.$fileExtension")

            return try {
                // Upload the file to Firebase Storage using putFile
                spaceRef.putFile(uri)
                    .addOnSuccessListener {
                        // Upload success
                        Toast.makeText(
                            context,
                            "Upload successful!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Optional: Additional actions on success
                    }
                    .addOnFailureListener { exception ->
                        // Handle unsuccessful upload
                        Toast.makeText(
                            context,
                            "Upload failed: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .await()

                // Retrieve the download URL after successful upload
                val downloadUrl = spaceRef.downloadUrl.await().toString()

                // Log the download URL (optional)
                Log.d("StorageUtil", "Download URL: $downloadUrl")

                // Return the download URL
                downloadUrl
            } catch (e: Exception) {
                // Handle exceptions (e.g., log or show an error message)
                Toast.makeText(
                    context,
                    "Upload failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                ""
            }
        }
    }
}


