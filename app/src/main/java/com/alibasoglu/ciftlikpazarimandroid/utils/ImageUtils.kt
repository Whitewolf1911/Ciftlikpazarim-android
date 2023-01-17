package com.alibasoglu.ciftlikpazarimandroid.utils

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


//BitmapDrawable drawable = (BitmapDrawable) imgPreview.getDrawable();
//Bitmap bitmap = drawable.getBitmap();
//encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
fun encodeImageToBase64(image: Bitmap, compressFormat: CompressFormat?, quality: Int): String? {
    val byteArrayOS = ByteArrayOutputStream()
    image.compress(compressFormat, quality, byteArrayOS)
    return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
}


// Bitmap bitmap = decodeBase64(encodedImage );
// img.setImageBitmap(bitmap);
fun decodeBase64Image(input: String?): Bitmap? {
    val decodedBytes = Base64.decode(input, 0)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
