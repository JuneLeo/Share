package com.card.internel.layout;

import android.view.ViewGroup;

import com.card.internel.card.CardManager;
import com.card.internel.card.ICard;


public interface ILayoutManager {

    void onCreateView();

    void onUpdateView();

    void onCreateCardView(ICard iCard);

    void onUpdateCardView(ICard iCard);

    void setContainer(ViewGroup container);

    ViewGroup getContainer();

    void setManager(CardManager manager);


}
