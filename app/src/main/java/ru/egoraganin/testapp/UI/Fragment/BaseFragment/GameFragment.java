package ru.egoraganin.testapp.UI.Fragment.BaseFragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import im.delight.android.webview.AdvancedWebView;
import ru.egoraganin.testapp.R;
import ru.egoraganin.testapp.UI.Activity.GameActivity;

/**
 * Created by Егор on 06.09.2017.
 */

public class GameFragment extends BaseFragment {

    @BindView(R.id.webView_content)
    AdvancedWebView webViewContent;

    @Override
    protected void init(View view) {
        webViewContent.loadUrl("file:///android_asset/2048-master/2048-master/index.html");
    }

    public void updateWebView() {
        if (getArguments() != null) {
            switch (getArguments().getInt(GameActivity.GAME)) {
                case 1:
                    webViewContent.loadUrl("file:///android_asset/2048-master/2048-master/index.html");
                    break;
                case 2:
                    webViewContent.loadUrl("file:///android_asset/clumsy-bird-master/clumsy-bird-master/index.html");
                    break;
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_game;
    }

}
