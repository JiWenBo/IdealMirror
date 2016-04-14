package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Bruce on 11/24/14.
 */
public class SwipeLayout extends LinearLayout {
    //添加一个接口
    private ContentViewLinister linister;
    private ViewDragHelper viewDragHelper;
    private View contentView;
    private View actionView;
    private int dragDistance;
    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int draggedX;
    private boolean flag = true;


    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float yStart = 0, y = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yStart = event.getY();
                        flag = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        y = yStart - event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flag) {
                            linister.doSomeThing();
                        }
                        break;
                }
                if (Math.abs(y) > 2) {
                    flag = false;
                }
                return false;
            }
        });
    }


    //    设置接口的方法
    public void setContentListener(ContentViewLinister listener) {
        this.linister = listener;
    }


    //onFinishInflate 当View中所有的子控件均被映射成xml后触发
    @Override
    protected void onFinishInflate() {

        contentView = getChildAt(0);
        actionView = getChildAt(1);
        actionView.setVisibility(GONE);
    }

    //    当控件的父元素正要放置该控件时调用.父元素会问子控件一个问题，“你想要用多大地方啊？”，
// 然后传入两个参数——widthMeasureSpec和heightMeasureSpec.
//            这两个参数指明控件可获得的空间以及关于这个空间描述的元数据.
//            更好的方法是你传递View的高度和宽度到setMeasuredDimension方法里,
// 这样可以直接告诉父控件，需要多大地方放置子控件.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        dragDistance = actionView.getMeasuredWidth();
        Log.e("onMeasure", "dragDistance="+dragDistance);
    }


    private class DragHelperCallback extends ViewDragHelper.Callback {
        //        返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
        @Override
        public boolean tryCaptureView(View view, int i) {
            Log.e("tryCaptureView","tryCaptureView");
            return view == contentView || view == actionView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            Log.e("onviewposition-left=" + draggedX, "dx=" + dx);
//            选择删除按钮的时候  left 的值是700+
//      选删除按钮的时候只要向右移动就会隐藏按钮
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

        //        可以在该方法中对child移动的边界进行控制，left , top 分别为即将移动到的位置，
//        比如横向的情况下，我希望只在ViewGroup的内部移动，即：最小>=paddingleft，
//        最大<=ViewGroup.getWidth()-paddingright-child.getWidth
//        手指触摸移动时实时回调
//        限制拖动子窗口一直是横移动
//        1被拖动的窗口   2 沿x轴运动的  3提出位置向左变化
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                Log.e("clampview+dx+" + dx, "num==" + left);
//                DX应该是带有刷新的那种  left是直接往左传
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - dragDistance;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                Log.e("newleft(shangmian", "是多少" + newLeft);
                return newLeft;
            } else {
//                走这里的时候 向右滑动  怎嘛滑动距离都会
//                必须向右滑动一点 才开始计算这个方法
//                返回的值是700+   是向右滑动700
//                一旦走了这个方法 则按钮的 点击事件  就会被取消
//                取消后 按钮应该就放回的False  所以会交给按钮的这个父类
//                父类获得了监听
//                在抬手式的时候  xvle的值  是0了   所以需要关闭按钮
//这个返回值到底给谁了啊?   上面方法中有一部分原因
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                Log.e("newleft(xiamian()+dx" + dx, "是多少" + newLeft);
                return newLeft;
            }

        }

        //得到窗口横向移动的范围
        @Override
        public int getViewHorizontalDragRange(View child) {
           // Log.e("getViewHorizontalDragRange","");
            return dragDistance;
        }

        // 抬手  参数1向左滑动的速度
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            Log.e("xvelxvelxvel", "==" + xvel);
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
            Log.e("settleDestX", settleDestX + "");
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果这个事件是提供给父视图的拦截事件  就应该拦截
//        按钮向右滑动一点的时候才去 拦截  要不不拦截
//        可能是向右滑动一点 子布局就不能点击了  这个时候就点击事件就没被激活
//        所有就被传给了  父类  父类在交给viewDragHelper
//        问题 2  问神马按钮向右滑动一点就滑动隐藏了
        if (viewDragHelper.shouldInterceptTouchEvent(ev)) {
            Log.e("viewDragHelper", "走没走这里");
            return true;
        }
        Log.e("viewDragHelper", "下面走没走这里");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       Log.e("onTouchEvent","onTouchEvent");
//        交给viewdraghelper处理
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.e("computeScrol", "computeScrol");
        if (viewDragHelper.continueSettling(true)) {
            Log.e("computeScrol", "viewDragHelper");
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


}
