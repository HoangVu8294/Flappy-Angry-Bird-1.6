package com.fab.ui;

import aurelienribon.tweenengine.TweenAccessor;

public class ValueTween implements TweenAccessor<Value>{
	
	@Override
	public int getValues(Value val, int type, float[] ret) { ret[0] = val.value(); return 1; }

	@Override
	public void setValues(Value val, int type, float[] ret) { val.setValue(ret[0]); }
}
