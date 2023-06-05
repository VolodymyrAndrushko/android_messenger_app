package com.vandrushko.ui.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import com.vandrushko.R
import kotlin.properties.Delegates

private const val BUTTON_COLOR = 0xFAFAFA
private const val BUTTON_TEXT_COLOR = 0x18181F
private const val BUTTON_FONT_WEIGHT = 600
private const val BUTTON_TEXT_SIZE = 16f
private const val MISSING_FONT_EXCEPTION_TEXT = "Font is missing"

class GoogleButtonView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.googleButtonStyle
) : androidx.appcompat.widget.AppCompatButton(context, attributeSet, defStyleAttr) {

    private val typedArray: TypedArray
    private var buttonBackGroundColor by Delegates.notNull<Int>()
    private lateinit var buttonText: String
    private var buttonTextColor by Delegates.notNull<Int>()
    private lateinit var buttonTextFont: Typeface
    private var buttonTextFontWeight by Delegates.notNull<Int>()
    private var buttonTextFontSize by Delegates.notNull<Float>()
    private var buttonCornerRadius by Delegates.notNull<Float>()
    private var imageDrawable: Drawable? = null
    private var imagePadding by Delegates.notNull<Float>()

    init {
        typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.GoogleButtonView,
            defStyleAttr,
            R.style.DefaultGoogleButtonStyle
        )

        setButtonBackground()

        setButtonText()

        setButtonImage()

        gravity = Gravity.START or Gravity.CENTER_VERTICAL

        typedArray.recycle()
    }

    private fun setButtonText() {
        buttonText = typedArray.getString(R.styleable.GoogleButtonView_buttonText) ?: ""
        buttonTextColor =
            typedArray.getColor(R.styleable.GoogleButtonView_buttonTextColor, BUTTON_TEXT_COLOR)
        buttonTextFont = typedArray.getFont(R.styleable.GoogleButtonView_buttonTextFont)
            ?: throw Exception(MISSING_FONT_EXCEPTION_TEXT)
        buttonTextFontWeight =
            typedArray.getInt(R.styleable.GoogleButtonView_buttonTextFontWeight, BUTTON_FONT_WEIGHT)
        buttonTextFontSize =
            typedArray.getDimension(
                R.styleable.GoogleButtonView_buttonTextFontSize,
                BUTTON_TEXT_SIZE
            )

        text = buttonText
        setTextColor(buttonTextColor)
        typeface = buttonTextFont
        applyFontWeight(buttonTextFontWeight, typeface)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextFontSize)
    }

    private fun applyFontWeight(weight: Int, typeface: Typeface) {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            CustomTypefaceSpan(typeface, weight),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    }

    private fun setButtonBackground() {
        buttonBackGroundColor = typedArray.getColor(
            R.styleable.GoogleButtonView_buttonColor, BUTTON_COLOR
        )
        buttonCornerRadius =
            typedArray.getDimension(R.styleable.GoogleButtonView_buttonCornerRadius, 0f)

        val shapeDrawable = GradientDrawable().apply {
            cornerRadius = buttonCornerRadius
            setColor(buttonBackGroundColor)
        }

        background = shapeDrawable
    }

    private fun setButtonImage() {
        imageDrawable = typedArray.getDrawable(R.styleable.GoogleButtonView_buttonImage)
        imagePadding = typedArray.getDimension(R.styleable.GoogleButtonView_buttonImagePadding, 0f)

        setCompoundDrawablesRelativeWithIntrinsicBounds(
            imageDrawable, null, null, null
        )
        compoundDrawablePadding = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX, imagePadding, resources.displayMetrics
        ).toInt()
    }

    inner class CustomTypefaceSpan(private val typeface: Typeface, private val fontWeight: Int) :
        TypefaceSpan("") {

        override fun updateDrawState(ds: TextPaint) {
            applyTypeface(ds, typeface)
            applyFontWeight(ds, fontWeight)
        }

        override fun updateMeasureState(paint: TextPaint) {
            applyTypeface(paint, typeface)
            applyFontWeight(paint, fontWeight)
        }

        private fun applyTypeface(paint: Paint, typeface: Typeface) {
            paint.typeface = typeface
        }

        private fun applyFontWeight(paint: Paint, fontWeight: Int) {
            paint.isFakeBoldText = fontWeight >= Typeface.BOLD
        }
    }

    override fun onDraw(canvas: Canvas) {
        val buttonContentWidth = width - paddingLeft - paddingRight

        val method = transformationMethod
        val buttonText =
            (if (method != null) method.getTransformation(text, this) else text).toString()
        val textWidth = paint.measureText(buttonText)

        val drawables = compoundDrawables
        val drawableLeft = drawables[0]
        val drawableWidth = drawableLeft?.intrinsicWidth ?: 0

        val drawablePadding =
            if (textWidth > 0 && drawableLeft != null) compoundDrawablePadding else 0

        val bodyWidth = textWidth + drawableWidth + drawablePadding
        canvas.translate((buttonContentWidth - bodyWidth) / 2, 0f)

        super.onDraw(canvas)
    }
}