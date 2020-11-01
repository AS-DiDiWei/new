package com.example.test01

import android.content.Context
import android.icu.util.Measure
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class FlowLayout:ViewGroup {
    //定义两控件之间的间距
    var mHorizontalSpace = 30
    var mVerticalSpace = 20

    //保存所有的View
    var allLineViews: MutableList<MutableList<View>> = mutableListOf()
    //保存所有的高度
    var everyLineHeights: MutableList<Int> = mutableListOf()

    constructor(context: Context):super(context){}
    constructor(context: Context,attrs: AttributeSet):super(context,attrs){}

    //清空函数
    private fun clearFun(){
        allLineViews.clear()
        everyLineHeights.clear()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //清空，而不是重新申请空间，防止内存抖动
        clearFun()

        //得到爷爷给此ViewGroup的参考值
        var selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        var selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        //记录此ViewGroup实际需要多宽多高
        var parentNeededWidth = 0
        var parentNeededHeight = 0

        //保存这一行的行宽以及行高
        var lineWidthUsed = 0
        var lineHeightUsed = 0
        //保存这一行所有的View
        var oneLineViews: MutableList<View> = mutableListOf()

        //测量孩子
        for(i in 0 until childCount){
            var childView = getChildAt(i)

            //得到layoutParams
            var childLP = childView.layoutParams

            //得到MeasureSpec
            var childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,childLP.width)
            var childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,paddingTop+paddingBottom,childLP.height)

            //进行测量
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec)

            if(lineWidthUsed + childView.measuredWidth + mHorizontalSpace > selfWidth){
                //换行
                allLineViews.add(oneLineViews)
                everyLineHeights.add(lineHeightUsed)
                parentNeededWidth = Math.max(parentNeededWidth,lineWidthUsed)
                parentNeededHeight += lineHeightUsed

                //数据重置或者清0
                lineWidthUsed = 0
                lineHeightUsed = 0
                oneLineViews = mutableListOf()
            }

            oneLineViews.add(childView)
            lineWidthUsed += childView.measuredWidth + mHorizontalSpace
            lineHeightUsed = Math.max(lineHeightUsed,childView.measuredHeight + mVerticalSpace)

            //保存最后一行的数据（这个很容易忘记）
            if(i == childCount - 1){//注意这个判断条件
                //换行
                allLineViews.add(oneLineViews)
                everyLineHeights.add(lineHeightUsed)
                parentNeededWidth = Math.max(parentNeededWidth,lineWidthUsed)
                parentNeededHeight += lineHeightUsed
            }
        }
        //测量父亲
        //首先得到父亲的mode，看看是不是EXACTLY
        var parentWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        var parentHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        var realWidth = if(parentWidthMode == MeasureSpec.EXACTLY) selfWidth else parentNeededWidth
        var realHeight = if(parentHeightMode == MeasureSpec.EXACTLY) selfHeight else parentNeededHeight

        //这里要加上间距
        setMeasuredDimension(realWidth+paddingLeft+paddingRight,realHeight+paddingTop+paddingBottom)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var padL = paddingLeft
        var padT = paddingTop

        for(i in 0 until allLineViews.size){
            for(j in 0 until allLineViews.get(i).size){
                //注意：下面这里不是getChildAt，而是得到二维数组的每一个值
                var childView = allLineViews.get(i).get(j)
                var left = padL
                var top = padT
                var right = left + childView.measuredWidth
                var bottom = top + childView.measuredHeight
                childView.layout(left,top,right,bottom)

                padL += childView.width + mHorizontalSpace
            }
            //一定要注意，左起始点要进行归0
            padL = paddingLeft
            padT += everyLineHeights[i]
        }
    }
}