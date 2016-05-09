package com.doo.study.rxjavapractice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;


public class Operators extends Activity{
    private static final String TAG = Operators.class.getSimpleName();

    @Bind(R.id.textView1)
    TextView textView1;

    @Bind(R.id.textView1_2)
    TextView textView1_2;

    @Bind(R.id.textView2)
    TextView textView2;

    @Bind(R.id.textView3)
    TextView textView3;

    @Bind(R.id.textView4)
    TextView textView4;

    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);

        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();


        // #01 Basic
        Observable<String> basicObservable
                = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxAndroid!");
                subscriber.onCompleted();
            }
        });

        basicObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext");

            }
        });


        basicObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "Sub1 : " + s);
                textView1.setText("Sub1 : " + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //error
                Log.d(TAG, "onError : " + throwable.getMessage());

            }
        }, new Action0() {
            @Override
            public void call() {
                //completed
                Log.d(TAG, "Sub1 : action 0");

            }
        });


        basicObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "Sub2 : " + s);
                textView1_2.setText("Sub2 : " +s);
            }
        });




        // #02 just()
        Observable
                .just("convert an object or several objects into an Observable that emits that object or those objects")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView2.setText(s);
                    }
                });



        // #03 map() & from()
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Observable<Integer> simpleObservable1 = Observable.from(numbers);
        compositeSubscription.add(
        simpleObservable1
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer val) {
                        Log.d(TAG, "#03 emit : " + Thread.currentThread());
                        return val * 10;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "#03 received: " + integer + " / " + Thread.currentThread());
                        textView3.setText(String.valueOf(integer));
                    }
                }));

        compositeSubscription.add(
        simpleObservable1
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer val) {
                        Log.d(TAG, "#04 emit : " + Thread.currentThread());
                        return val * -1;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "#04 received: " + integer + " / " + Thread.currentThread());
                        textView4.setText(String.valueOf(integer));
                    }
                }));

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
