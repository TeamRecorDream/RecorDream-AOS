package com.recodream_aos.recordream.util.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomBottomNavigationView : BottomNavigationView {
    private var mPath: Path = Path()
    private var mPaint: Paint = Paint()

    private val CURVE_CIRCLE_RADIUS = 190 / 2 // 움푹파이는자식

    private val mFirstCurveStartPoint = Point()
    private val mFirstCurveEndPoint = Point()
    private val mFirstCurveControlPoint1 = Point()
    private val mFirstCurveControlPoint2 = Point()

    private var mSecondCurveStartPoint = Point()
    private val mSecondCurveEndPoint = Point()
    private val mSecondCurveControlPoint1 = Point()
    private val mSecondCurveControlPoint2 = Point()
    private var mNavigationBarWidth: Int = 0
    private var mNavigationBarHeight: Int = 0

    val startColor = Color.parseColor("#FF0000") // 시작 색상
    val endColor = Color.parseColor("#00FF00") // 끝 색상

    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(startColor, endColor),
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        mPaint.style = Paint.Style.FILL

        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mNavigationBarWidth = width
        mNavigationBarHeight = height

        mFirstCurveStartPoint.set(
            // CURVE_CIRCLE_RADIUS 굴곡담당
            (mNavigationBarWidth / 1.92 - CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS / 3).toInt(),
            0,
        )
        mFirstCurveEndPoint.set(
            (mNavigationBarWidth / 2),
            CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4,
        )
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint.set(
            (mNavigationBarWidth / 2.1 + CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS / 3).toInt(),
            0,
        )

        mFirstCurveControlPoint1.set(
            mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4,
            mFirstCurveStartPoint.y,
        )

        mFirstCurveControlPoint2.set(
            (mFirstCurveEndPoint.x - CURVE_CIRCLE_RADIUS * 2.29 + CURVE_CIRCLE_RADIUS).toInt(),
            mFirstCurveEndPoint.y,
        )

        mSecondCurveControlPoint1.set(
            (mSecondCurveStartPoint.x + CURVE_CIRCLE_RADIUS * 2.29 - CURVE_CIRCLE_RADIUS).toInt(),
            mSecondCurveStartPoint.y,
        )
        mSecondCurveControlPoint2.set(
            mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4),
            mSecondCurveEndPoint.y,
        )

        mPath.reset()
        mPath.moveTo(0F, 0F)
        mPath.lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())

        mPath.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(),
            mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(),
            mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(),
            mFirstCurveEndPoint.y.toFloat(),
        )

        mPath.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(),
            mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(),
            mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(),
            mSecondCurveEndPoint.y.toFloat(),
        )

        mPath.lineTo(mNavigationBarWidth.toFloat(), 0F)
        mPath.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
        mPath.lineTo(0F, mNavigationBarHeight.toFloat())
        mPath.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val startColor = Color.parseColor("#FFFFFF") // 그라데이션 시작 색상
        val endColor = Color.parseColor("#02040F") // 그라데이션 끝 색상

        // 그라데이션을 그리는 영역을 제외한 부분을 투명하게 설정
        gradientDrawable.setBounds(0, CURVE_CIRCLE_RADIUS, width, height)
        gradientDrawable.alpha = 0
        gradientDrawable.draw(canvas)

        // 움푹 파인 부분에 그라데이션 그리기
        val gradient = LinearGradient(
            0f,
            0f,
            0f,
            CURVE_CIRCLE_RADIUS.toFloat(),
            startColor,
            endColor,
            Shader.TileMode.CLAMP,
        )

        mPaint.shader = gradient
        canvas.drawPath(mPath, mPaint)
    }
}
