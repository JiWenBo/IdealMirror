package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 观察QQ的滑动删除效果，可以猜测可以滑动删除的部分主要包含两个部分，
 * 一个是内容区域（用于放置正常显示的view），另一个是操作区域（用于放置删除按钮）。
 * 默认情况下，操作区域是不显示的，内容区域的大小是填充整个容器，操作区域始终位于内容区域的右面。
 * 当开始滑动的时候，整个容器中的所有子view都像左滑动，如果操作区域此时是不可见的，设置为可见
 * <p/>
 * 观察QQ的滑动删除效果，可以猜测可以滑动删除的部分主要包含两个部分，
 * 一个是内容区域（用于放置正常显示的view），另一个是操作区域（用于放置删除按钮）。
 * 默认情况下，操作区域是不显示的，内容区域的大小是填充整个容器，操作区域始终位于内容区域的右面。
 * 当开始滑动的时候，整个容器中的所有子view都像左滑动，如果操作区域此时是不可见的，设置为可见
 */

/**
 * 观察QQ的滑动删除效果，可以猜测可以滑动删除的部分主要包含两个部分，
 * 一个是内容区域（用于放置正常显示的view），另一个是操作区域（用于放置删除按钮）。
 * 默认情况下，操作区域是不显示的，内容区域的大小是填充整个容器，操作区域始终位于内容区域的右面。
 * 当开始滑动的时候，整个容器中的所有子view都像左滑动，如果操作区域此时是不可见的，设置为可见
 */

/**
 *自定义一个layout SwipeLayout继承自LinearLayout。
 * SwipeLayout包含两个子view，第一个子view是内容区域，第二个子view是操作区域。
 * 滑动效果的控制，主要就是通过检测SwipeLayout的touch事件来实现，
 * 这里不想自己去通过监听touch事件来实现滑动效果，那是一个很繁琐的过程。
 * Android support库里其实已经提供了一个很好的工具类来帮我们做这件事情ViewDragHelper。
 * 如果你看过Android原生的DrawerLayout的代码，就会发现DrawerLayout的滑动效果也是通过ViewDragHelper类实现的。
 */

public class SwipeLayout extends LinearLayout {

    private ViewDragHelper viewDragHelper;
    private View contentView;  // 左边的view
    private View actionView;   // 右边的view
    private int dragDistance;  // 拖动的距离
    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int draggedX;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 第一步 在容器中创建一个ViewDragHelper类的对象。
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    @Override
    protected void onFinishInflate() {  // onFinishInflate方法 当View中所有的子控件均被映射成xml后触发
        contentView = getChildAt(0);
        actionView = getChildAt(1);
        actionView.setVisibility(GONE);
    }


    //在SwipeLayout的 复写LinearLayout的measure方法的事件中，
    // 设置拖动的距离为actionView的宽度。
    // onMeasure:当子View的父控件要放置该View的时候，父控件会传递两个参数给View
    //  获取子View的宽高尺寸
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragDistance = actionView.getMeasuredWidth();
    }


    // 第二步 接下来要把容器的事件处理委托给ViewDragHelper对象
    //  onInterceptTouchEvent返回true，就是为了这个区域内的任何触摸事件，都要拦截一下

    //ViewDragHelper对象来决定motion event是否是属于拖动过程。
    // 如果motion event属于拖动过程，那么触摸事件就交给ViewDragHelper来处理，
    // ViewDragHelper在处理拖动过程的时候，会调用ViewDragHelper.Callback对象的一系列方法。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (viewDragHelper.shouldInterceptTouchEvent(ev)) {  // 有兴趣的 可以看看shouldInterceptTouchEvent这个方法  －.－
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    private class DragHelperCallback extends ViewDragHelper.Callback {

        // tryCaptureView方法，用来确定contentView和actionView是可以拖动的
        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == contentView || view == actionView;
        }

        //onViewPositionChanged在被拖动的view位置改变的时候调用，
        // 如果被拖动的view是contentView，我们需要在这里更新actionView的位置
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            if (changedView == contentView) {
                actionView.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }
            if (actionView.getVisibility() == View.GONE) {
                actionView.setVisibility(View.VISIBLE);
            }
            invalidate();
        }

        // clampViewPositionHorizontal用来限制view在x轴上拖动，要实现水平拖动效果必须要实现这个方法，
        // 我们这里因为仅仅需要实现水平拖动，所以没有实现clampViewPositionVertical方法
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - dragDistance;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                return newLeft;
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }

        // getViewHorizontalDragRange方法用来限制view可以拖动的范围
        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
        }

        // onViewReleased方法中，根据滑动手势的速度以及滑动的距离来确定是否显示actionView。
        // smoothSlideViewTo方法用来在滑动手势之后实现惯性滑动效果
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            if (xvel > AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (xvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = true;
            } else if (draggedX <= -dragDistance / 2) {
                settleToOpen = true;
            } else if (draggedX > -dragDistance / 2) {
                settleToOpen = false;
            }

            final int settleDestX = settleToOpen ? -dragDistance : 0;
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
        }
    }
}
