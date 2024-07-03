package com.khayat.app.util

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object Tools {

    fun isSaudiNumber(userMobile: String): Boolean {
        return when (userMobile.length) {
            10 -> {
                userMobile.startsWith("05")
            }
//            9 -> {
//                userMobile.startsWith("5")
//            }
//            12 -> {
//                userMobile.startsWith("9665")
//            }
//            13 -> {
//                userMobile.startsWith("+9665")
//            }
            else -> false
        }
    }

    fun openUrl(activity: Activity, url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)

    }

    fun convertFullDateToMilliseconds(data: String): Int {
        Log.e(toString(), "data: $data")

        val formatter = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
        formatter.isLenient = false
        val oldDate = formatter.parse(data)
        val oldMillis = oldDate.time

        return ((Date().time - oldMillis) / 1000).toInt()
    }

    fun timeAgoDisplay(time: Int): String {
        Log.e(toString(), "time: $time")
        val minute = 60
        val hour = 60 * minute
        val day = 24 * hour
        val week = 7 * day
        val month = 4 * week

        val ago = "منذ"
        var duration = ""
        return when {
            time < minute -> {
                duration = "ثانية"
                "$ago $time $duration"
            }
            time < hour -> {
                duration = "دقائق"
                "$ago ${time / minute} $duration"
            }
            time < day -> {
                duration = "ساعات"
                "$ago ${(time / hour)} $duration"
            }
            time < week -> {
                duration = "أيام"
                "$ago ${(time / day)} $duration"
            }
            else -> {
                duration = "اسبوع"
                "$ago ${(time / week)} $duration"
            }
        }
    }

    fun formatPhoneNumber(userMobile: String): String {
        var newFormatPhone = StringBuilder(userMobile)
        if (newFormatPhone[0] == '0') {
            newFormatPhone = newFormatPhone.deleteCharAt(0)
            newFormatPhone = StringBuilder("+966$newFormatPhone")
        } else if (newFormatPhone[0] == '9') {
            newFormatPhone = StringBuilder("+$newFormatPhone")

        }
        return newFormatPhone.toString()
    }

    fun validateEmail(email: String): Boolean {
        return Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .matcher(email)
                .matches()
    }

    fun validatePassword(passWord: String): Boolean {
        return (passWord.length > 4)
    }

    fun pickImageFromCamera(activity: Activity): Pair<Intent, Uri?>? {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) != null) {
            val values = ContentValues(1)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
            val fileUri = activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//            activity.startActivityForResult(intent, 1000)
            return Pair(first = intent, second = fileUri)
        } else {
            Toast
                    .makeText(activity, "تعذر فتح الكمبيرا", Toast.LENGTH_LONG)
                    .show()
            return null
        }
    }

    fun pickImageFromGallery(activity: Activity): Intent {
//        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
//        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val chooserIntent: Intent = Intent.createChooser(pickIntent, "Select Image")
        // chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
//        activity.startActivityForResult(chooserIntent, 2000)
        return chooserIntent
    }

    fun getRealPathFromURI(contentURI: Uri, activity: Activity): String {
        val result: String
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity.contentResolver.query(contentURI, filePathColumn, null, null, null)
        if (cursor == null) {
            result = contentURI.path!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(filePathColumn[0])
            result = cursor.getString(idx)
        }
        cursor?.close()
        return result
    }

    fun createTime(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH)
        return dateFormatter.format(currentTime)
    }

    fun calculate(password: String): Int {
        var score: Int = 0
        // boolean indicating if password has an upper case
        var upper: Boolean = false
        // boolean indicating if password has a lower case
        var lower: Boolean = false
        // boolean indicating if password has at least one digit
        var digit: Boolean = false
        // boolean indicating if password has a least one special char
        var specialChar: Boolean = false

        for (i in password.indices) {
            var c: Char = password[i]

            if (!specialChar && !Character.isLetterOrDigit(c)) {
                score++
                specialChar = true
            } else {
                if (!digit && Character.isDigit(c)) {
                    score++
                    digit = true
                } else {
                    if (!upper || !lower) {
                        if (Character.isUpperCase(c)) {
                            upper = true
                        } else {
                            lower = true
                        }

                        if (upper && lower) {
                            score++
                        }
                    }
                }
            }
        }
        if (password.length <= 5) {
            score = 0
        }
        return score
    }



//    @JvmStatic
//    fun showDialogRegistration(activity: Activity?, msg: String?, headerTitle: String?) {
//        val dialog = Dialog(activity)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.thank_you_for_registration)
//        val dialogButton = dialog.findViewById<View>(R.id.btn_done) as Button
//        dialogButton.setOnClickListener {
////            dataPasser = activity as OnClickPass
////            dataPasser!!.onClick()
//            dialog.dismiss()
//        }
//        dialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
//
//        dialog.show()
//    }
//    fun showDialogcheckYourMail(activity: Activity?, msg: String?, headerTitle: String?) {
//        val dialog = Dialog(activity)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.check_your_email_dialog)
//        val dialogButton = dialog.findViewById<View>(R.id.btn_ok) as Button
//        val email = dialog.findViewById<View>(R.id.tv_emailSent) as TextView
//        dialogButton.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
//    }


    var dataPasser: OnClickPass? = null

    interface OnClickPass {
        fun onClick()
    }
}