//package com.hotmob.sdk.hotmobsdkshowcase.banner
//
//import android.content.Context
//import android.util.AttributeSet
//import android.view.View
//import android.widget.ScrollView
//
//class ContainerScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
//    : ScrollView(context, attrs, defStyleAttr) {
//
//    private var isScrollingChild = false
//
//    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
//        println(isScrollingChild)
//        if (!isScrollingChild) {
//            super.onScrollChanged(l, t, oldl, oldt)
//        }
//    }
//
//    override fun onNestedScroll(target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
//        println("onNestedScroll")
//        if (!isScrollingChild) {
//            super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
//        }
//    }
//
//    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
//        isScrollingChild = true
//        return super.onStartNestedScroll(child, target, nestedScrollAxes)
//    }
//
//    override fun onStopNestedScroll(target: View?) {
//        isScrollingChild = false
//        super.onStopNestedScroll(target)
//    }
//}