package com.khaithu.a3ctech_android_final_project.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.khaithu.a3ctech_android_final_project.R
import com.khaithu.a3ctech_android_final_project.customview.interfacecustomview.IRateView
import kotlin.math.cos
import kotlin.math.sin

class RateView(context: Context, attrs: AttributeSet) : View(context, attrs), IRateView {

    private var mRadiusWidthParent: Float = 0f
    private var mRadiusHeightParent: Float = 0f
    private var mRadiusBiggestCircle: Float = 0f
    private var mRadiusCircleSecond: Float = 0f
    private var mBgrColorFirst: Int = 0
    private var mBgrColorSecond: Int = 0
    private var mBgrColorThird: Int = 0
    private var mmBorderColor: Int = 0
    private var mBorder: Float = 0f
    private var mTextSize: Float = 0f
    private var mRadiusWithText: Float = 0f
    private var mRadiusHeightText: Float = 0f
    private var mPercent: Int = 0
    private var mTextPercent: String = ""

    override fun getPercent(): Int {
        return this.mPercent
    }

    override fun setPercent(mPercent: Int) {
        this.mPercent = mPercent
        invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //dimen
        mRadiusWidthParent = width / 2f
        mRadiusHeightParent = height / 2f
        mRadiusBiggestCircle = ((width + height) / 2f) * 0.35f
        mRadiusCircleSecond = mRadiusBiggestCircle - mRadiusBiggestCircle * 0.2f
        mBorder = (mRadiusBiggestCircle / 40f)
        mRadiusWithText = mRadiusWidthParent - mRadiusWidthParent * 0.5f
        mRadiusHeightText = mRadiusHeightParent + mRadiusHeightParent * 0.1f
        mTextSize = mRadiusBiggestCircle * 0.7f

        //color
        mBgrColorSecond = context.getColor(R.color.black_blue)
        mBgrColorThird = context.getColor(R.color.yellow_green)
        mmBorderColor = Color.BLACK
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas) {
        if (mPercent == 0) {
            mTextPercent = "NR"
            mBgrColorFirst = context.getColor(R.color.yellow_green)
        } else {
            mTextPercent = "${mPercent}%"
        }

        onDrawCircleParent(canvas)
        onDrawPercent(canvas, mPercent)
        onDrawCircle(canvas)
        onDrawBorderCircleInside(canvas)
        onDrawBorderCircleOutside(canvas)
        onDrawText(canvas)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun onDrawCircle(canvas: Canvas, radius: Float, paint: Paint) {
        canvas.drawCircle(mRadiusWidthParent, mRadiusHeightParent, radius, paint)
    }

    private fun onDrawCircleParent(canvas: Canvas) {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = mBgrColorThird
        paint.style = Paint.Style.FILL

        onDrawCircle(canvas, mRadiusBiggestCircle, paint)
    }

    private fun onDrawCircle(canvas: Canvas) {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = mBgrColorSecond
        paint.style = Paint.Style.FILL

        onDrawCircle(canvas, mRadiusCircleSecond, paint)
    }

    private fun onDrawBorderCircleOutside(canvas: Canvas) {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = mmBorderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mBorder

        onDrawCircle(canvas, mRadiusCircleSecond, paint)
    }

    private fun onDrawBorderCircleInside(canvas: Canvas) {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = mmBorderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mBorder

        onDrawCircle(canvas, mRadiusBiggestCircle, paint)
    }

    private fun onDrawText(canvas: Canvas) {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = mTextSize
        canvas.drawText(mTextPercent, mRadiusWithText, mRadiusHeightText, paint)
    }

    private fun onDrawLine(canvas: Canvas, stopX: Float, stopY: Float, paint: Paint) {
        canvas.drawLine(mRadiusWidthParent, mRadiusHeightParent, stopX, stopY, paint)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onDrawPercent(canvas: Canvas, mPercent: Int) {
        var start: Int = 0
        var finish = mPercent * 3.6f
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        mBgrColorFirst = when {
            mPercent == 0 -> context.getColor(R.color.yellow_green)
            mPercent < 40 -> context.getColor(R.color.red)
            mPercent < 70 -> context.getColor(R.color.yellow)
            else -> context.getColor(R.color.light_green)
        }
        paint.color = mBgrColorFirst
        paint.style = Paint.Style.FILL
        paint.strokeWidth = mBorder
        do {
            onDrawLine(
                canvas,
                mRadiusWidthParent + sin(start * Math.PI / 180.0).toFloat() * mRadiusBiggestCircle,
                mRadiusHeightParent - cos(start * Math.PI / 180.0).toFloat() * mRadiusBiggestCircle,
                paint
            )
            start++
        } while (start <= finish)
    }
}
