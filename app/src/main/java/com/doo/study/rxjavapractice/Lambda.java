package com.doo.study.rxjavapractice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * This class is to see how much the code is simplified by using Lambada expression.
 */
public class Lambda extends Activity{
    private static final String TAG = Lambda.class.getSimpleName();

    @Bind(R.id.button)
    Button button4;

    @Bind(R.id.textView2)
    TextView textView2;

    @Bind(R.id.textView4)
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);

        ButterKnife.bind(this);


        // #01 just()
        Observable
                .just("Hello Lambda!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView2.setText(s);
                    }
                });

        // With Lambda
        Observable
                .just("Hello Lambda!")
                .subscribe(s -> textView2.setText(s));




        //#02
        Observable
                .just("Hello Lambda!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String text) {
                        return text.length();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        textView4.setText("length : " + integer);
                    }
                });

        // With Lambda
        Observable
                .just("Hello Lambda!")
                .map((String text) -> {
                    return text.length();
                })
                .subscribe(length -> textView4.setText("length: " + length));


        Observable
                .just("Hello Lambda!")
                .map(text -> text.length())
                .subscribe(length -> textView4.setText("length: " + length));






        Observable
                .just("Hello Lambda!")
                .map( s -> s.toUpperCase())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        String str = "UpperCase : " + s + "/length: " + s.length();
                        Log.d(TAG, str);
                    }
                });

        // With Lambda
        Observable
                .just("Hello Lambda")
                .map( s -> s.toUpperCase())
                .map( s -> "UpperCase : " + s + "/length: " + s.length())
                .subscribe(str -> Log.d(TAG, str));


    }

}
