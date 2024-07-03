package com.app.electricstations.util.editTextKhayat


/*
Created by Aiman Qaid on 16,يوليو,2020
Contact me at wakka-2@hotmail.com
*/
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.TextViewBindingAdapter
import com.app.electricstations.R

object SSCustomEditTextBinder {

    /**
     * This binding adapter is used to set custom edittext value.
     *
     * @param customEditText
     * @param value
     */
    @JvmStatic
    @BindingAdapter("textValue")
    fun setTextValue(customEditText: SSCustomEdittextOutlinedBorder, value: String?) {
        value?.let {
            customEditText.setTextValue(value)
        }
    }

    @JvmStatic
    @BindingAdapter("errorTextValue")
    fun setErrorTextValue(customEditText: SSCustomEdittextOutlinedBorder, value: String?) {
        value?.let {
            customEditText.findViewById<AppCompatTextView>(R.id.lableError).text = value
        }
    }

    @JvmStatic
    @BindingAdapter("isErrorEnable")
    fun setIsErrorEnable(customEditText: SSCustomEdittextOutlinedBorder, value: Boolean) {
        customEditText.setIsErrorEnable(value,R.string.empty)
    }

    @JvmStatic
    @BindingAdapter(value = ["android:afterTextChanged", "android:textAttrChanged"], requireAll = false)
    fun setTextWatcher(filterPositionView: SSCustomEdittextOutlinedBorder, test: TextViewBindingAdapter.AfterTextChanged?, textAttrChanged: InverseBindingListener?) {
        val newValue = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                test?.let {
                    test.afterTextChanged(s)
                }

                textAttrChanged?.let {
                    textAttrChanged.onChange()
                }
            }
        }
//        val oldValue = ListenerUtil.trackListener(filterPositionView.editText, newValue, R.id.textWatcher)
//        if (oldValue != null) {
//            filterPositionView.editText?.removeTextChangedListener(oldValue)
//        }
//        filterPositionView.editText?.addTextChangedListener(newValue)
    }
}