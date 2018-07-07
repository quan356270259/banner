package com.to.aboomy.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * auth aboom by 2018/2/25.
 */

public class QyBanner extends RelativeLayout {

    private QyViewPager mViewPager;
    private boolean     isSetAdapter;

    public QyBanner(Context context) {
        this(context, null);
    }

    public QyBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public QyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipChildren(Boolean.FALSE);
        initViews(context);
        initViewPagerScroll();
    }

    private void initViews(Context context) {
        mViewPager = new QyViewPager(context);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mViewPager);
    }

    public void setPageMargins(int left, int top, int right, int bottom, int pageMargin) {
        LayoutParams layoutParams = (LayoutParams) mViewPager.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setOffscreenPageLimit(3);
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    public void setAdapter(PagerAdapter adapter, IQyIndicator iQyIndicator) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
            if (iQyIndicator != null) {
                View view = iQyIndicator.getView();
                view.setLayoutParams(iQyIndicator.getParams());
                addView(view);
                iQyIndicator.setViewPager(mViewPager);
            }
        }
        isSetAdapter = true;
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isSetAdapter() {
        return isSetAdapter;
    }
}