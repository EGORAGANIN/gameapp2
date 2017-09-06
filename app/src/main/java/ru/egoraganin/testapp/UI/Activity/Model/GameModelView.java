package ru.egoraganin.testapp.UI.Activity.Model;

import android.animation.Animator;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ru.egoraganin.testapp.R;

/**
 * Created by Егор on 06.09.2017.
 */

public class GameModelView extends BaseViewGroup {

    public GameModelView(Context context) {
        super(context);
    }

    public GameModelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameModelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameModelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(View view) {

}

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_game;
    }

}
