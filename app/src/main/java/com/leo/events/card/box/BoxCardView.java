package com.leo.events.card.box;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.card.internel.view.AbstractView;
import com.leo.events.R;


/**
 * Created by spf on 2018/11/15.
 */
public class BoxCardView extends AbstractView<BoxPresenter, BoxCardVM> {

    @Override
    public View onCreateView(final ViewGroup parent) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.card_apple, null);
        View apple = v.findViewById(R.id.apple);
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCardPresenter.btnClick();
            }
        });
        return v;
    }

    @Override
    public void onUpdateView(View view, ViewGroup parent) {
    }
}
