package com.leo.events.card.func;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.card.internel.view.AbstractView;
import com.leo.events.R;


/**
 * Created by spf on 2018/11/15.
 */
public class FuncCardView extends AbstractView<FuncPresenter, FuncCardVM> {

    @Override
    public View onCreateView(ViewGroup parent) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.card_func, null);
        return v;
    }

    @Override
    public void onUpdateView(View view, ViewGroup parent) {
    }
}
