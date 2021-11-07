package com.mahdikaseatashin.vpnsk.utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.mahdikaseatashin.vpnsk.R

class ConnectionView : View {
    private var mInnerRect: Paint? = null
    private var mTextPaint: Paint? = null
    private var radiusOuter = 110
    private var radiusInner = 90
    private var mCenterX = 0
    private var mCenterY = 0
    private var textSize = 0
    private var mText = "test"
    var widgetWidth = 24
    var widgetHeight = 24
    private val isRunning = false
    private var isMeasured = false

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialization()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialization()
    }

    constructor(context: Context?) : super(context) {
        initialization()
    }

    private fun initialization() {
        mInnerRect = Paint(Paint.ANTI_ALIAS_FLAG)
        mInnerRect!!.color = resources.getColor(R.color.gray)
        mTextPaint = Paint()
        mTextPaint!!.color = Color.DKGRAY
        mTextPaint!!.textSize = textSize.toFloat()
    }

    private val updateRadius: Unit
        get() {
            if (!isMeasured) {
                isMeasured = true
                val size = if (widgetWidth < widgetHeight) widgetWidth else widgetHeight
                radiusOuter = (size * 0.35f).toInt()
                radiusInner = (size * 0.3f).toInt()
                textSize = (size * 0.08f).toInt()
                setTimedTextSize(textSize)
            }
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCenterX = width / 2
        mCenterY = height / 2
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val areaRect = RectF(
            (mCenterX - radiusInner).toFloat(),
            (mCenterY - radiusInner).toFloat(),
            (mCenterX + radiusInner).toFloat(),
            (mCenterY + radiusInner).toFloat()
        )
        val bounds = RectF(areaRect)

        // measure text width
        bounds.right = mTextPaint!!.measureText(mText, 0, mText.length)
        // measure text height
        bounds.bottom = mTextPaint!!.descent() - mTextPaint!!.ascent()
        bounds.left += (areaRect.width() - bounds.right) / 2.0f
        bounds.top += (areaRect.height() - bounds.bottom) / 2.0f
        canvas.drawRoundRect(
            mCenterX - areaRect.width() / 4,
            mCenterY + mTextPaint!!.ascent(),
            mCenterX + areaRect.width() / 4,
            mCenterY - mTextPaint!!.ascent(),
            45F,
            45F,
            mInnerRect!!
        )
        canvas.drawText(mText, bounds.left, bounds.top - mTextPaint!!.ascent(), mTextPaint!!)
    }

    private fun setTimedTextSize(textSize: Int) {
        this.textSize = textSize
        mTextPaint?.textSize = textSize.toFloat()
    }

    fun setText(tvText: String) {
        mText = tvText
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        widgetWidth = (widthSize * 0.6).toInt()
        widgetHeight = (heightSize * 0.6).toInt()

        //Measure Width
        val width: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize
            }
            MeasureSpec.AT_MOST -> {
                widgetWidth.coerceAtMost(widthSize)
            }
            else -> {
                widgetWidth
            }
        }
        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }
            MeasureSpec.AT_MOST -> {
                widgetHeight.coerceAtMost(heightSize)
            }
            else -> {
                widgetHeight
            }
        }
        widgetWidth = width
        widgetHeight = height
        updateRadius
        setMeasuredDimension(width, height)
    }
}
