package com.doo.study.rxjavapractice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;


public class Events extends Activity{
    private static final String TAG = Events.class.getSimpleName();

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.button2)
    Button button2;

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_email)
    EditText editEmail;

    @Bind(R.id.submit_bt)
    Button submitBt;

    @Bind(R.id.textView)
    TextView textView;

    @Bind(R.id.textView2)
    TextView textView2;

    CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_event);

        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();



        // #01 Basic - click event listener
        Observable<String> basicObservable
                = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Button button = (Button) findViewById(R.id.button);
                String val = String.valueOf(new Random().nextInt());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        subscriber.onNext(val);
                        subscriber.onCompleted();
                    }
                });
            }
        });

        basicObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError : " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext");
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("number: " + s);
            }
        });





        // #02 RxBinding - RxView + RetroLambda
        RxView
                .clicks(button2)
                .map(event -> new Random().nextInt())
                .subscribe(value -> {
                    textView2.setText("number: " + value.toString());
                }, throwable -> {
                    Log.e(TAG,"Error: " + throwable.getMessage());
                    throwable.printStackTrace();
                });



        RxView
                .clicks(button2)
                .map(event -> new Random().nextInt())
                .subscribe(value -> {
                    textView2.setText("number: " + value.toString());
                }, Throwable::printStackTrace);






        // #02 Simple Registration Form
        Observable<Boolean> userNameValid =
                RxTextView.textChangeEvents(editName)
                        .map(e -> e.text())
                        .map(t -> t.length() > 4);

        Observable<Boolean> emailValid =
                RxTextView.textChangeEvents(editEmail)
                        .map(e -> e.text())
                        .map(t -> Patterns.EMAIL_ADDRESS.matcher(t).matches());

        emailValid.map(b -> b ? Color.BLACK : Color.RED)
                .subscribe(color -> editEmail.setTextColor(color));
        userNameValid.map(b -> b ? Color.BLACK : Color.RED)
                .subscribe(color -> editName.setTextColor(color));




//        // #03 Branching and Combining Event - combineLatest
//        Observable<Boolean> submitEnabled =
//                Observable.combineLatest(userNameValid, emailValid, (a, b) -> a && b);
//        submitEnabled.subscribe(enabled -> submitBt.setEnabled(enabled));





//        emailValid.doOnNext(b -> Log.d(TAG, "Email " + (b ? "Valid" : "Invalid")))
//                .map(b -> b ? Color.BLACK : Color.RED)
//                .subscribe(color -> editEmail.setTextColor(color));
//
//        userNameValid.doOnNext(b -> Log.d(TAG, "Name " + (b ? "Valid" : "Invalid")))
//                .map(b -> b ? Color.BLACK : Color.RED)
//                .subscribe(color -> editName.setTextColor(color));





        // #04 distinctUntilChanged() - to make subscriber called only when the validity changed
        emailValid.distinctUntilChanged()
                .doOnNext(b -> Log.d(TAG, "Email " + (b ? "Valid" : "Invalid")))
                .map(b -> b ? Color.BLACK : Color.RED)
                .subscribe(color -> editEmail.setTextColor(color));

        userNameValid.distinctUntilChanged()
                .doOnNext(b -> Log.d(TAG, "Name " + (b ? "Valid" : "Invalid")))
                .map(b -> b ? Color.BLACK : Color.RED)
                .subscribe(color -> editName.setTextColor(color));

        Observable<Boolean> submitEnabled =
                Observable.combineLatest(userNameValid, emailValid, (a, b) -> a && b);
        submitEnabled.subscribe(enabled -> submitBt.setEnabled(enabled));





        //#05 CompositeSubscription - unsubscribe all at once
        compositeSubscription.add(emailValid.distinctUntilChanged()
                .doOnNext(b -> Log.d(TAG, "Email " + (b ? "Valid" : "Invalid")))
                .map(b -> b ? Color.BLACK : Color.RED)
                .subscribe(color -> editEmail.setTextColor(color)));

        compositeSubscription.add(userNameValid.distinctUntilChanged()
                .doOnNext(b -> Log.d(TAG, "Name " + (b ? "Valid" : "Invalid")))
                .map(b -> b ? Color.BLACK : Color.RED)
                .subscribe(color -> editName.setTextColor(color)));

        compositeSubscription.add(submitEnabled.subscribe(enabled -> submitBt.setEnabled(enabled)));

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
