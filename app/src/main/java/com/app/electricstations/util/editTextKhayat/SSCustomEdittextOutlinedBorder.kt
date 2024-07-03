package com.app.electricstations.util.editTextKhayat


/*
Created by Aiman Qaid on 16,يوليو,2020
Contact me at wakka-2@hotmail.com
*/

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.text.InputFilter
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import com.app.electricstations.R
import com.app.electricstations.util.EditSpinner
//import com.app.teacheasy.util.EditSpinner
import java.util.*


@InverseBindingMethods(value = [InverseBindingMethod(type = SSCustomEdittextOutlinedBorder::class, attribute = "textValue", event = "android:textAttrChanged", method = "getTextValue"),
    InverseBindingMethod(type = SSCustomEdittextOutlinedBorder::class, attribute = "errorTextValue"),
    InverseBindingMethod(type = SSCustomEdittextOutlinedBorder::class, attribute = "isErrorEnable")])
class SSCustomEdittextOutlinedBorder @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyle: Int = 0, defStyleRes: Int = 0) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var titleColor = ContextCompat.getColor(context, R.color.dark_blue_text)
    private var titleErrorColor = ContextCompat.getColor(context, R.color.color_error)
    private var borderColor = ContextCompat.getColor(context, R.color.colorPrimary)
    private var borderErrorColor = ContextCompat.getColor(context, R.color.color_error)
    private var borderWidth = 2
    private var isLineBackground = false
    var isSpinner = false
    var isSpinner2 = false
    var isCode = false
    var editText:AppCompatEditText ?= null
    var et:AppCompatEditText?=null
//    private var clickListenerOnly:ClickListenerOnly ?= null

    val getTextValue: String
        get() {
            return editText?.text.toString()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_edittext, this, true)
        orientation = VERTICAL
        et = findViewById(R.id.et)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.custom_component_attributes, 0, 0)
            val title = resources.getText(typedArray.getResourceId(R.styleable.custom_component_attributes_custom_component_title, R.string.app_name))
            val editTextHint = resources.getText(typedArray.getResourceId(R.styleable.custom_component_attributes_custom_component_editText_hint, R.string.app_name))
            val errorText = resources.getText(typedArray.getResourceId(R.styleable.custom_component_attributes_custom_component_error_text_value, R.string.empty))
            val isErrorEnable = typedArray.getBoolean(R.styleable.custom_component_attributes_isErrorEnable, false)
            val inputType = typedArray.getInt(R.styleable.custom_component_attributes_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL)
            val imeOptions = typedArray.getInt(R.styleable.custom_component_attributes_android_imeOptions, EditorInfo.IME_ACTION_NEXT)
            val digits = typedArray.getString(R.styleable.custom_component_attributes_android_digits)
            val maxLine = typedArray.getInt(R.styleable.custom_component_attributes_custom_component_maxline, 1)
            val minLine = typedArray.getInt(R.styleable.custom_component_attributes_custom_component_minline, 1)
            val maxLength = typedArray.getInt(R.styleable.custom_component_attributes_custom_component_maxLength, 99)
            val titleBgColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_title_bg_color, ContextCompat.getColor(context, R.color.white))
            val editTextBgColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_editText_bg_color, ContextCompat.getColor(context, R.color.white))
            val errorTextBgColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_error_text_bg_color, ContextCompat.getColor(context, R.color.white))
            val hideTitle = typedArray.getBoolean(R.styleable.custom_component_attributes_custom_component_hide_title, true)
            val minHeight = typedArray.getDimension(R.styleable.custom_component_attributes_android_minHeight, 40f)
            val isLTR = typedArray.getBoolean(R.styleable.custom_component_attributes_custom_is_ltr, false)
            titleColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_title_color, ContextCompat.getColor(context, R.color.dark_blue_text))
            titleErrorColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_title_error_color, ContextCompat.getColor(context, R.color.color_error))
            borderColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_border_color, ContextCompat.getColor(context, R.color.colorPrimary))
            borderErrorColor = typedArray.getColor(R.styleable.custom_component_attributes_custom_component_border_error_color, ContextCompat.getColor(context, R.color.color_error))
            borderWidth = typedArray.getInt(R.styleable.custom_component_attributes_custom_component_border_width, 2)
            isSpinner = typedArray.getBoolean(R.styleable.custom_component_attributes_custom_component_is_spinner, false)
            isSpinner2 = typedArray.getBoolean(R.styleable.custom_component_attributes_custom_component_is_spinner2, false)
            isCode = typedArray.getBoolean(R.styleable.custom_component_attributes_custom_component_is_code, false)


            checkSpinner()
            checkSpinner2()
            checkCode()
            setTitle(title as String)
            setEditTextHint(editTextHint as String)
            setTextStyle(ResourcesCompat.getFont(context, R.font.dubai_regular))
            setIsErrorEnable(isErrorEnable,R.string.empty)
            setIsErrorDisable(isErrorEnable,true)
            setStyle(inputType, maxLine, minLine, maxLength,imeOptions,minHeight)
            setTitleBackGroundColor(titleBgColor)
            setEditTextBackGroundColor(editTextBgColor)
            setErrorTextBackGroundColor(errorTextBgColor)
            hideTitle(hideTitle)
            setLtr(isLTR)
            setErrorText(errorText as String)
            digits?.let { it1 -> setInputDigit(it1) }

            typedArray.recycle()
        }
    }

    private fun setLtr(ltr: Boolean) {
        if(ltr){
            et?.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }else{
            et?.layoutDirection = View.LAYOUT_DIRECTION_INHERIT
        }
    }

    private fun checkCode() {
        if(isCode){
            editText = findViewById(R.id.etCode)
            val constraintSet = ConstraintSet()
            constraintSet.clone(findViewById<ConstraintLayout>(R.id.constraintLayout))
            val pxValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15f,
                context.resources.displayMetrics
            ).toInt()
            val pyValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                context.resources.displayMetrics
            ).toInt()
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.START,
                R.id.cl,
                ConstraintSet.START,
                pxValue
            )
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.TOP,
                R.id.constraintLayout,
                ConstraintSet.TOP,
                pyValue
            )
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.BOTTOM,
                R.id.cl,
                ConstraintSet.TOP,
                0
            )
            constraintSet.applyTo(findViewById<ConstraintLayout>(R.id.constraintLayout))
            findViewById<ConstraintLayout>(R.id.cl).visibility = View.VISIBLE
            et?.visibility = View.GONE
            findViewById<EditSpinner>(R.id.spinner).visibility = View.GONE
            findViewById<LinearLayout>(R.id.li).visibility = View.GONE
        }
    }

    private fun checkSpinner2() {
        if(isSpinner2){
            editText = findViewById(R.id.spinner2)
            val constraintSet = ConstraintSet()
            constraintSet.clone(findViewById<ConstraintLayout>(R.id.constraintLayout))
            val pxValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15f,
                context.resources.displayMetrics
            ).toInt()
            val pyValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                context.resources.displayMetrics
            ).toInt()
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.START,
                R.id.li,
                ConstraintSet.START,
                pxValue
            )
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.TOP,
                R.id.constraintLayout,
                ConstraintSet.TOP,
                pyValue
            )
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.BOTTOM,
                R.id.li,
                ConstraintSet.TOP,
                0
            )
            constraintSet.applyTo(findViewById<ConstraintLayout>(R.id.constraintLayout))
            findViewById<LinearLayout>(R.id.li).visibility = View.VISIBLE
            et?.visibility = View.GONE
            findViewById<EditSpinner>(R.id.spinner).visibility = View.GONE
        }
    }

    private fun checkSpinner() {
        if(isSpinner){
            editText = findViewById(R.id.spinner)
            val constraintSet = ConstraintSet()
            constraintSet.clone(findViewById<ConstraintLayout>(R.id.constraintLayout))
            val pxValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15f,
                context.resources.displayMetrics
            ).toInt()
            val pyValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                context.resources.displayMetrics
            ).toInt()
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.START,
//                R.id.spinner,
                ConstraintSet.START,
                pxValue
            )
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.TOP,
                R.id.constraintLayout,
                ConstraintSet.TOP,
                pyValue
            )
            constraintSet.connect(
                R.id.lableTitle,
                ConstraintSet.BOTTOM,
//                R.id.spinner,
                ConstraintSet.TOP,
                0
            )
            constraintSet.applyTo(findViewById<ConstraintLayout>(R.id.constraintLayout))
            findViewById<EditSpinner>(R.id.spinner).visibility = View.VISIBLE
            et?.visibility = View.GONE
        }else{
            editText = findViewById(R.id.et)
            findViewById<EditSpinner>(R.id.spinner).visibility = View.GONE
            et?.visibility = View.VISIBLE
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    override fun addView(child: View?) {
        super.addView(child)

    }

    fun setTextValue(value : String?){
        value?.let {
            editText?.setText(value)
            editText?.setSelection(value.length)
        }
    }
    fun setErrorText(value : String?){
        value?.let {
            findViewById<AppCompatTextView>(R.id.lableError).text = value
        }
    }

    fun setIsErrorEnable(isShown: Boolean,errorMessage:Int) {
        if (isShown) {
            setTitleColor(titleColor)
            if(isLineBackground) {
                setBackgroundBorderErrorColor(borderErrorColor)
            }else{
                editText?.background = ContextCompat.getDrawable(context,R.drawable.edit_text_border_error_dotted)
                findViewById<AppCompatTextView>(R.id.lableTitle).visibility = View.GONE
                isLineBackground = false
            }
            findViewById<AppCompatTextView>(R.id.lableError).visibility = View.VISIBLE
            findViewById<AppCompatTextView>(R.id.lableError).text = resources.getString(errorMessage)
        }
    }
    fun setIsErrorDisable(isShown: Boolean,isFirst:Boolean) {
        if (!isShown && !isFirst) {
            findViewById<AppCompatTextView>(R.id.lableError).visibility = View.INVISIBLE
            changeBorderColor()
            hideTitle(isShown)
        }
    }
    fun changeBorderColor(){
        when {
            isSpinner2 -> {
                findViewById<LinearLayout>(R.id.li)?.background = ContextCompat.getDrawable(context,R.drawable.bg_custom_edittext)
            }
            isCode -> {
                findViewById<ConstraintLayout>(R.id.cl)?.background = ContextCompat.getDrawable(context,R.drawable.bg_custom_edittext)
            }
            else -> {
                editText?.background = ContextCompat.getDrawable(context, R.drawable.bg_custom_edittext)
            }
        }
        setTitleColor(titleColor)
        setBackgroundBorderColor(borderColor)
        isLineBackground = true
    }
    fun removeBorderColor(){
        if(isCode){
            findViewById<ConstraintLayout>(R.id.cl).background = ContextCompat.getDrawable(context,R.drawable.edit_text_border_dotted)
        }else {
            editText?.background =
                ContextCompat.getDrawable(context, R.drawable.edit_text_border_dotted)
        }
        isLineBackground = false
        //setBackgroundBorderColor(borderColor)
    }
    fun setErrorBorder(){
        if(isSpinner2) {
            findViewById<LinearLayout>(R.id.li)?.background = ContextCompat.getDrawable(context, R.drawable.edit_text_border_error_dotted)
        }else {
            editText?.background = ContextCompat.getDrawable(context, R.drawable.edit_text_border_error_dotted)
        }
        isLineBackground = false
    }

    private fun setTitleColor(@ColorInt colorID: Int) {
        findViewById<AppCompatTextView>(R.id.lableTitle).setTextColor(colorID)
    }

    private fun setTitle(title: String) {
        findViewById<AppCompatTextView>(R.id.lableTitle).text = title
    }

    private fun setTitleBackGroundColor(@ColorInt colorID: Int) {
        findViewById<AppCompatTextView>(R.id.lableTitle).setBackgroundColor(colorID)
    }

    private fun setErrorTextBackGroundColor(@ColorInt colorID: Int) {
        findViewById<AppCompatTextView>(R.id.lableError).setBackgroundColor(colorID)
    }

    private fun setInputDigit(digit:String) {
        editText?.keyListener = DigitsKeyListener.getInstance("0123456789")
    }

    private fun setEditTextBackGroundColor(@ColorInt colorID: Int) {
        when {
            isSpinner2 -> {
                val drawable = findViewById<LinearLayout>(R.id.li)?.background as StateListDrawable
                val dcs = drawable.constantState as DrawableContainerState?
                val drawableItems = dcs!!.children
                val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                gradientDrawableChecked.setColor(colorID)
            }
            isCode -> {
                val drawable = findViewById<ConstraintLayout>(R.id.cl)?.background as StateListDrawable
                val dcs = drawable.constantState as DrawableContainerState?
                val drawableItems = dcs!!.children
                val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                gradientDrawableChecked.setColor(colorID)
            }
            else -> {
                val drawable = editText?.background as StateListDrawable
                val dcs = drawable.constantState as DrawableContainerState?
                val drawableItems = dcs!!.children
                val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                gradientDrawableChecked.setColor(colorID)
            }
        }
    }

    fun setEditTextHint(hint: String) {
        editText?.hint = hint
    }

    private fun setStyle(inputType: Int, maxLine: Int, minLine: Int, maxLength: Int,imeOptions:Int,minHeight:Float) {
        editText?.inputType = inputType
        editText?.apply {
            maxLines = maxLine
            minLines = minLine
            gravity = Gravity.TOP or Gravity.START
            filters = arrayOf(InputFilter.LengthFilter(maxLength))
            this.imeOptions = imeOptions
            this.minHeight = minHeight.toInt()
        }
    }

     fun setBackgroundBorderErrorColor(colorID: Int) {
         when {
             isSpinner2 -> {
                 val drawable = findViewById<LinearLayout>(R.id.li)?.background as StateListDrawable
                 val dcs = drawable.constantState as DrawableContainerState?
                 val drawableItems = dcs!!.children
                 val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                 gradientDrawableChecked.setStroke(borderWidth, colorID)
             }
             isCode -> {
                 val drawable = findViewById<ConstraintLayout>(R.id.cl)?.background as StateListDrawable
                 val dcs = drawable.constantState as DrawableContainerState?
                 val drawableItems = dcs!!.children
                 val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                 gradientDrawableChecked.setStroke(borderWidth, colorID)
             }
             else -> {
                 val drawable = editText?.background as StateListDrawable
                 val dcs = drawable.constantState as DrawableContainerState?
                 val drawableItems = dcs!!.children
                 val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                 gradientDrawableChecked.setStroke(borderWidth, colorID)
             }
         }
    }
    fun setBackgroundBorderColor(colorID: Int) {
        when {
            isSpinner2 -> {
                val drawable = findViewById<LinearLayout>(R.id.li)?.background as StateListDrawable
                val dcs = drawable.constantState as DrawableContainerState?
                val drawableItems = dcs!!.children
                val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                gradientDrawableChecked.setStroke(borderWidth, colorID)
            }
            isCode -> {
                val drawable = findViewById<ConstraintLayout>(R.id.cl)?.background as StateListDrawable
                val dcs = drawable.constantState as DrawableContainerState?
                val drawableItems = dcs!!.children
                val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                gradientDrawableChecked.setStroke(borderWidth, colorID)
            }
            else -> {
                val drawable = editText?.background as StateListDrawable
                val dcs = drawable.constantState as DrawableContainerState?
                val drawableItems = dcs!!.children
                val gradientDrawableChecked = drawableItems[0] as GradientDrawable
                gradientDrawableChecked.setStroke(borderWidth, colorID)
            }
        }
    }

    private fun setTextStyle(textStyle: Typeface?) {
        findViewById<AppCompatTextView>(R.id.lableTitle).typeface = textStyle
        editText?.typeface = textStyle
        findViewById<AppCompatTextView>(R.id.lableError).typeface = textStyle
    }

     fun hideTitle(hide:Boolean){
        if(hide){
            findViewById<AppCompatTextView>(R.id.lableTitle).visibility = View.GONE
        }else{
            findViewById<AppCompatTextView>(R.id.lableTitle).visibility = View.VISIBLE
        }
    }
    fun getEditTextView(): EditText? {
        return editText
    }
    fun getLinear(): LinearLayout? {
        return findViewById<LinearLayout>(R.id.li)
    }
    fun changeBackground(pic:Int){
        editText?.background = ContextCompat.getDrawable(context,pic)
    }
}