package com.app.electricstations.util.editTextKhayat


/*
Created by Aiman Qaid on 13,يوليو,2020
Contact me at wakka-2@hotmail.com
*/
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.app.electricstations.R
import com.app.electricstations.util.SharedPrefsUtil
import com.khayat.app.util.editTextKhayat.DefaultAnimatorListener


class KhayatEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    private val defStyleRes: Int = 0
) : LinearLayout(
    context,
    attributeSet,
    defStyleRes
) {

    private val path = Path()

    private var cornerRadius = 16f

    private var collapsedTextHeight = 0f

    private var collapsedTextWidth = 0f

    private var collapsedTextSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        10f,
        context.resources.displayMetrics
    )

    private var animationDuration = 300

    private var animator: Animator? = null

    private var hint: String = ""
    private var hintTitle: String = ""

    private var editText: EditText? = null
    private var textView: TextView? = null

    private val expandedHintPoint = PointF()

    private var spacing = dpToPx(2f, context)

    private var hintBaseLine = 0f

    private var hideHintText = true

    private var rectEdit = Rect()
    private var mGravity = Gravity.START or Gravity.TOP


    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 4f
        textSize = collapsedTextSize
    }


    init {
        val leftPadding = dpToPx(230f, context).toInt()
        val padding = dpToPx(8f, context).toInt()
        setPadding(padding, padding, padding, padding)
        setWillNotDraw(false)
        attributeSet?.let(::initAttrs)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        editText?.let {
            val editTextHeight = it.height
            val d = left
            val k = right
            val l = top
            val s = bottom
            val n = it.width
            val g = it.right
            it.getGlobalVisibleRect(rectEdit)
            val j = rectEdit.right
            val o = rectEdit.left
            val z = this.right
            expandedHintPoint.apply {
                x = paddingLeft.plus(it.paddingLeft).toFloat()
//                x = this@KhayatEditText.right.toFloat()

//                x = if(Locale.getDefault().displayLanguage == "English"){
//                    x = it.width.minus(width/2).toFloat()
//                }else {
//                    paddingLeft.plus(it.paddingLeft).plus(dpToPx(230f, context).toInt())
//                        .toFloat()
//                }
                y = it.top.plus(
                    editTextHeight.div(2f).plus(
                        it.textSize.div(2)
                    )
                )
            }
            if (changed) {
                hideHintText = if (editText?.hasFocus() == false) {
                    updateBorderPath(-spacing)
                    true
                } else {
                    hint = editText?.hint.toString()
                    editText?.hint = ""
                    true
                }
            }
        }
    }
    private fun getRelativeLeft(myView: View): Int {
        return if (myView.parent == myView.rootView) myView.right else myView.right + getRelativeLeft(
            myView.parent as View
        )
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        canvas.save()

        val isRtl = layoutDirection == View.LAYOUT_DIRECTION_RTL
        if (isRtl) {
            canvas.scale(-1f, 1f, (width / 2).toFloat(), (height / 2).toFloat())
            paint.setARGB(255, 57, 185, 164)
            paint.style = Paint.Style.STROKE
            paint.pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0f)
            canvas.drawPath(path, paint)
            if (!hideHintText || editText?.text?.isNotEmpty()!!) {
                paint.pathEffect = null
                paint.style = Paint.Style.STROKE
                canvas.drawPath(path, paint)
                paint.style = Paint.Style.FILL
                paint.setARGB(255, 40, 20, 164)
                if (editText?.id == R.id.etPhone) {
                    hintTitle = resources.getString(R.string.mobile_number)
                }
                val path = Path()
                val p = Paint()
                val strokeHalf = paint.strokeWidth.div(2)
//                path.moveTo(canvas.width/1.2.toFloat(), pivotY)
                path.apply {

                    moveTo(
                        rectEdit.right.toFloat()
                        ,
                        hintBaseLine
                    )

                }


                path.lineTo(expandedHintPoint.x.plus(spacing), pivotY)
                canvas.scale(1f, -1f, (width / 2).toFloat(), (height / 2).toFloat())
                canvas.drawTextOnPath(
                    hintTitle,
                    path,
                    ((canvas.width / 2.3 - paint.textSize).toFloat()).plus(paddingEnd),
                    ((canvas.height / 4 + paint.textSize)).minus(hintBaseLine),
                    paint
                )
//                canvas.drawText(
//                    hintTitle,
//                    expandedHintPoint.x,
//                    hintBaseLine,
//                    paint
//                )
            }
            canvas.restore()
        } else {

            paint.setARGB(255, 57, 185, 164)
            paint.style = Paint.Style.STROKE
            paint.pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0f)
            canvas.drawPath(path, paint)
            if (!hideHintText || editText?.text?.isNotEmpty()!!) {
                paint.pathEffect = null
                paint.style = Paint.Style.STROKE
                canvas.drawPath(path, paint)
                paint.style = Paint.Style.FILL
                paint.setARGB(255, 40, 20, 164)
                if (editText?.id == R.id.etPhone) {
                    hintTitle = resources.getString(R.string.mobile_number)
                }
                canvas.drawText(
                    hintTitle,
                    expandedHintPoint.x,
                    hintBaseLine,
                    paint
                )
            }
        }
    }


    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        if (child is EditText) {
            child.background = null

            child.setOnFocusChangeListener { _, hasFocus ->
                animateHint(
                    child,
                    hasFocus
                )
            }

            hint = child.hint.toString()
            val isLogin = SharedPrefsUtil.getBoolean(context, "isLogin", false)
            if (isLogin) {
                hintTitle = resources.getString(R.string.mobile_number)
            }
            val isProfile = SharedPrefsUtil.getBoolean(context, "isProfile", false)
            if (isProfile) {
                when (child.id) {
                    R.id.etPhone -> {
                        hintTitle = resources.getString(R.string.mobile_number)
                    }
//                    R.id.etName -> {
//                        hintTitle = resources.getString(R.string.name_title)
//                    }
//                    R.id.etEmail -> {
//                        hintTitle = resources.getString(R.string.email_title)
//                    }
//                    R.id.spGender -> {
//                        hintTitle = resources.getString(R.string.gender_title)
//                    }
                }
            }
            val rect = Rect()
//            paint.textAlign = Paint.Align.RIGHT
            paint.getTextBounds(
                hintTitle,
                0,
                hintTitle.length,
                rect
            )
            collapsedTextHeight = rect.height().toFloat()
            collapsedTextWidth = rect.width()
                .plus(spacing.times(2))
            editText = child
            editText?.getGlobalVisibleRect(rectEdit)
            val x = rectEdit.right
        }else{
            if(child is TextView){
                textView = child
            }
        }
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }

    private fun updateBorderPath(
        textWidth: Float
    ) {
        path.reset()
        val strokeHalf = paint.strokeWidth.div(2)
        path.apply {
            moveTo(
                expandedHintPoint.x
                    .minus(spacing),
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                )
            )

            lineTo(
                cornerRadius,
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                )
            )
            quadTo(
                strokeHalf,
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                ),
                strokeHalf,
                cornerRadius.plus(
                    collapsedTextHeight.div(2)
                )
            )

            lineTo(
                strokeHalf,
                height.minus(
                    strokeHalf + cornerRadius
                )
            )

            quadTo(
                strokeHalf,
                height.minus(strokeHalf),
                cornerRadius,
                height.minus(strokeHalf)
            )

            lineTo(
                width.minus(
                    cornerRadius
                ),
                height.minus(strokeHalf)
            )

            quadTo(
                width.minus(
                    strokeHalf
                ),
                height.minus(
                    strokeHalf
                ),
                width.minus(
                    strokeHalf
                ),
                height.minus(
                    cornerRadius
                )
            )

            lineTo(
                width.minus(
                    strokeHalf
                ),
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                ).plus(cornerRadius)
            )

            quadTo(
                width.minus(
                    strokeHalf
                ),
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                ),
                width.minus(
                    cornerRadius
                ),
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                )
            )

            lineTo(
                expandedHintPoint.x.plus(
                    textWidth
                ),
                strokeHalf.plus(
                    collapsedTextHeight.div(2)
                )
            )
        }
    }

    private fun initAttrs(attributeSet: AttributeSet) {
        with(
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.AwesomeTextInputLayout,
                defStyleRes,
                0
            )
        ) {

            paint.color = getColor(
                R.styleable.AwesomeTextInputLayout_borderColor,
                paint.color
            )
            paint.strokeWidth = getDimension(
                R.styleable.AwesomeTextInputLayout_borderWidth,
                paint.strokeWidth
            )
            collapsedTextSize = getDimension(
                R.styleable.AwesomeTextInputLayout_animatedTextSize,
                paint.textSize
            ).also {
                paint.textSize = it
            }
            cornerRadius = getDimension(
                R.styleable.AwesomeTextInputLayout_cornerRadius,
                cornerRadius
            )
            animationDuration = getInteger(
                R.styleable.AwesomeTextInputLayout_animationDuration,
                animationDuration
            )
            try {
                mGravity = getInteger(R.styleable.AwesomeTextInputLayout_android_layout_gravity, Gravity.TOP);
            } finally {
                recycle()
            }
        }
    }

    private fun animateHint(
        editText: EditText,
        hasFocus: Boolean
    ) {

        if (editText.editableText.isNotBlank()) {
            return
        }

        animator = if (hasFocus) {
            editText.hint = ""
            hideHintText = false
            getHintAnimator(
                editText.textSize,
                collapsedTextSize,
                expandedHintPoint.y,
                collapsedTextHeight,
                editText.width.toFloat(),
                collapsedTextWidth
            )
        } else {
            getHintAnimator(
                collapsedTextSize,
                editText.textSize,
                collapsedTextHeight,
                expandedHintPoint.y,
                collapsedTextWidth,
                -spacing
            ).apply {
                addListener(object : DefaultAnimatorListener() {
                    override fun onAnimationRepeat(animation: Animator) {
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        editText.hint = hint
                        hideHintText = true
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationStart(animation: Animator) {
                    }
                })
            }
        }

        animator?.start()
    }

    private fun getHintAnimator(
        fromTextSize: Float,
        toTextSize: Float,
        fromY: Float,
        toY: Float,
        fromTextWidth: Float,
        toTextWidth: Float
    ): Animator {
        val textSizeAnimator = ValueAnimator.ofFloat(
            fromTextSize,
            toTextSize
        ).apply {
            duration = animationDuration.toLong()
            interpolator = LinearInterpolator()
            addUpdateListener {
                paint?.textSize = it.animatedValue as Float
                invalidate()
            }
        }

        val translateAnimator = ValueAnimator.ofFloat(
            fromY,
            toY
        ).apply {
            duration = animationDuration.toLong()
            interpolator = LinearInterpolator()
            addUpdateListener {
                hintBaseLine = it.animatedValue as Float
                invalidate()
            }
        }

        val textWidthAnimator = ValueAnimator.ofFloat(
            fromTextWidth,
            toTextWidth
        ).apply {
            duration = animationDuration.toLong()
            interpolator = LinearInterpolator()
            addUpdateListener {
                updateBorderPath(it.animatedValue as Float)
                invalidate()
            }
        }

        return AnimatorSet().apply {
            playTogether(
                textSizeAnimator,
                translateAnimator,
                textWidthAnimator
            )
        }
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }
}