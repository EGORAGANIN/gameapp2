package ru.egoraganin.testapp.UI.Activity.Model;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;


public abstract class BaseViewGroup extends FrameLayout
{
    public BaseViewGroup(Context context)
    {
        super(context);

        insideInit();
    }

    public BaseViewGroup(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        insideInit();
    }

    public BaseViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        insideInit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        insideInit();
    }

    private void insideInit()
    {
        int resource = getLayoutResource();
        View view = LayoutInflater.from(getContext()).inflate(resource, null, false);
        ButterKnife.bind(this,view);
        addView(view);

        init(view);
    }

    protected abstract void init(View view);

    protected abstract int getLayoutResource();
}
