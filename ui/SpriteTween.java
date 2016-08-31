package com.fab.ui;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteTween implements TweenAccessor<Sprite> {

    public static final int VAR = 1;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
        case VAR: returnValues[0] = target.getColor().a; return 1;
        default: return 0;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
        case VAR: target.setColor(1, 1, 1, newValues[0]); break;
        }
    }

}
