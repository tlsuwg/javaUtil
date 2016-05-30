package com.example.com.suyi.base;
public interface NetCallBck {

	<T> boolean onDateCall(int key, String info, T... ts);

	<T> boolean onErrCall(int key, String error,T... ts);
}
