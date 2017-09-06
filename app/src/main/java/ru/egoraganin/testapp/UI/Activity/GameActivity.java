package ru.egoraganin.testapp.UI.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.egoraganin.testapp.R;
import ru.egoraganin.testapp.UI.Activity.Model.BaseViewGroup;
import ru.egoraganin.testapp.UI.Activity.Model.GameModelView;
import ru.egoraganin.testapp.UI.Fragment.BaseFragment.GameFragment;

/**
 * Created by Егор on 06.09.2017.
 */

public class GameActivity extends BaseActivity {

    public static final String GAME = "GAME";

    private GameModelView gameModelView;
    private final Bundle argument = new Bundle();
    private final GameFragment gameFragment = new GameFragment();

    private Drawer.OnDrawerItemClickListener onDrawerListener = new Drawer.OnDrawerItemClickListener() {
        @Override
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            argument.putInt(GAME, position);
            gameFragment.setArguments(argument);
            gameFragment.updateWebView();
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameModelView(this));

    }

    @Override
    protected void init(BaseViewGroup baseViewGroup) {
        gameModelView = (GameModelView) baseViewGroup;
        initToolbar(R.id.toolbar);
        addSideMenu();
        initFragmentManager(R.id.frame_layout_container);
        setFragment(gameFragment);
    }


    private void addSideMenu() {
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("2048"),
                        new PrimaryDrawerItem().withName("Flappy Bird")
                )
                .withOnDrawerItemClickListener(onDrawerListener)
                .build();

    }

}
