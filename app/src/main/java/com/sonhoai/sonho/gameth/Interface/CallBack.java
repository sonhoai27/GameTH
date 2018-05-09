package com.sonhoai.sonho.gameth.Interface;

public interface CallBack<T> {
    public void onSuccess(T result);
    public void onFail(T result);
}
