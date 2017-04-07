package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/3/31.
 */

public class CookbookRecomResp {
    private CookbookResponse friedDishes;
    private CookbookResponse breakfast;
    private CookbookResponse snacks;
    private CookbookResponse soup;

    public CookbookResponse getFriedDishes() {
        return friedDishes;
    }

    public void setFriedDishes(CookbookResponse friedDishes) {
        this.friedDishes = friedDishes;
    }

    public CookbookResponse getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(CookbookResponse breakfast) {
        this.breakfast = breakfast;
    }

    public CookbookResponse getSnacks() {
        return snacks;
    }

    public void setSnacks(CookbookResponse snacks) {
        this.snacks = snacks;
    }

    public CookbookResponse getSoup() {
        return soup;
    }

    public void setSoup(CookbookResponse soup) {
        this.soup = soup;
    }
}
