package ru.egoraganin.testapp.UI.Fragment.BaseFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment
{
    private View view;

    protected Unbinder unbinder;

    protected abstract void init(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        int resource = getLayoutResource();

        if(resource != -1)
        {   View view = inflater.inflate(resource, container, false);
            unbinder = ButterKnife.bind(this,view);
            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected int getLayoutResource()
    {
        return -1;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        this.view = view;

        init(view);

        super.onViewCreated(view, savedInstanceState);
    }

    public void initToolbar(@IdRes int toolbarResource)
    {
        Toolbar toolbar = (Toolbar) view.findViewById(toolbarResource);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    public boolean onBackPressed()
    {
        return false;
    }

    public void startActivity(Class nameClass){
        startActivity(new Intent(getContext(),nameClass));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
