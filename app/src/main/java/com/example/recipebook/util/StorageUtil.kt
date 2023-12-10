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

class StorageUtil{


    companion object {

        suspend fun uploadToStorage(uri: Uri, context: Context, type: String): String {
            val storage = Firebase.storage
            // Create a storage reference from our app
            var storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            var spaceRef: StorageReference


            if (type == "image"){
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

//            byteArray?.let{
//
//                var uploadTask = spaceRef.putBytes(byteArray)
//                val downloadUrl = spaceRef.downloadUrl.await().toString()
//
//                uploadTask.addOnFailureListener {
//                    Toast.makeText(
//                        context,
//                        "upload failed",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    // Handle unsuccessful uploads
//                }.addOnSuccessListener { taskSnapshot ->
//                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//                    // ...
//                    Toast.makeText(
//                        context,
//                        "upload successed",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }

            return byteArray?.let {
                try {
                    // Upload the image to storage
                    val uploadTask = spaceRef.putBytes(byteArray).await()

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
            } ?: ""
        }
    }
}
