package ru.egoraganin.testapp.UI.View.ActionView;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchView extends android.support.v7.widget.SearchView
{
    private ImageView iconSearchView;
    private EditText searchEditText;
    private ImageView iconCloseImageView;
    private View plateView;

    public SearchView(Context context)
    {
        super(context);

        init();
    }

    public SearchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init()
    {
        iconSearchView = (ImageView)this.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchEditText = (EditText)this.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        iconCloseImageView = (ImageView)this.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        plateView = this.findViewById(android.support.v7.appcompat.R.id.search_plate);
    }

    public boolean closeSearchView()
    {
        boolean close = !this.isIconified();

        if(close) this.onActionViewCollapsed();

        return close;
    }

    public void setIconResource(@DrawableRes int resource)
    {
        setIconResource(resource, ICON_MODE_LEFT);
    }

    public void setIconResource(@DrawableRes int resource, int mode)
    {
        iconSearchView.setImageResource(resource);

        switch (mode)
        {
            case ICON_MODE_LEFT:
                searchEditText.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
            break;
            case ICON_MODE_RIGHT:
                searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resource, 0);
            break;
            case ICON_MODE_BOTTOM:
                searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resource);
            break;
            case ICON_MODE_TOP:
                searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, resource, 0, 0);
            break;
            case ICON_MODE_ALL:
                searchEditText.setCompoundDrawablesWithIntrinsicBounds(resource, resource, resource, resource);
            break;
        }
    }

    public void setHint(String hint)
    {
        searchEditText.setHint("");
    }

    public void setTextColor(@ColorRes int color)
    {
        searchEditText.setTextColor(getResources().getColor(color));
    }

    public void setIconCloseSearchView(@DrawableRes int resource)
    {
        iconCloseImageView.setImageResource(resource);
    }

    public void setUnderlineBackground(@DrawableRes int resource)
    {
        plateView.setBackgroundResource(resource);
    }

    public boolean isOpen()
    {
        return !this.isIconified();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public void closeSearchView(View centerView, final AnimationEndListener animationEndListener)
    {
        closeSearchView(centerView, new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                animationEndListener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public void closeSearchView(View centerView, Animator.AnimatorListener animatorListener)
    {
        animationSearchView(centerView, animatorListener, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animationSearchView(View centerView, Animator.AnimatorListener animatorListener, boolean show)
    {
        int cy = centerView.getHeight() / 2;
        int cx = centerView.getWidth();

        int startRadius = show ? 0 : centerView.getWidth();
        int endRadius = show ? centerView.getWidth() : 0;

        Animator animator = ViewAnimationUtils.createCircularReveal(centerView, cx, cy, startRadius, endRadius);
        animator.setDuration(1000);
        animator.start();

        animator.addListener(animatorListener);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public void showSearchView(View centerView, final AnimationEndListener animationEndListener)
    {
        showSearchView(centerView, new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                animationEndListener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public void showSearchView(View centerView, Animator.AnimatorListener animatorListener)
    {
        animationSearchView(centerView, animatorListener, true);
    }

    public static final int ICON_MODE_LEFT = 1;
    public static final int ICON_MODE_RIGHT = 2;
    public static final int ICON_MODE_TOP = 3;
    public static final int ICON_MODE_BOTTOM = 4;
    public static final int ICON_MODE_ALL = 5;

    public interface AnimationEndListener
    {
        void onAnimationEnd();
    }
}
