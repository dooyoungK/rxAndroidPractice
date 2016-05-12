package com.doo.study.rxjavapractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.doo.study.rxjavapractice.retrofit.CustomListAdapter;
import com.doo.study.rxjavapractice.retrofit.GithubApi;
import com.doo.study.rxjavapractice.retrofit.GithubService;
import com.doo.study.rxjavapractice.retrofit.model.Github;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class GithubServiceActicity extends AppCompatActivity {

    private static final String TAG = GithubServiceActicity.class.getSimpleName();

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.edit_id)
    EditText editId;

    @Bind(R.id.requestBtn)
    Button requestBtn;

    private CustomListAdapter adapter;
    private GithubApi githubApi;
    private CompositeSubscription subscriptions;
    private String userIdInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        ButterKnife.bind(this);
        githubApi = GithubService.createService(GithubApi.class);

        subscriptions = new CompositeSubscription();

        adapter = new CustomListAdapter(this, R.layout.github_user_list_item);
        listView.setAdapter(adapter);

        Observable<String> inputObs = RxTextView.textChanges(editId)
                .map(String::valueOf);

        inputObs.distinctUntilChanged()
                .doOnNext(t -> userIdInput = t)
                .subscribe(t -> requestBtn.setEnabled(t.length() != 0));


    }

    @OnClick(R.id.clearBtn)
    public void onClearButtonClicked(){
        adapter.clear();
    }

    @OnClick(R.id.requestBtn)
    public void onFetchButtonClicked(){
        Log.d(TAG,"user id input > " + userIdInput);
            githubApi.getUser(userIdInput)
                     .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Github>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(Github response) {
                            // add to list
                            Log.d(TAG,"response : " + response.toString());
                            adapter.add(response);
                        }
                    });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
        ButterKnife.unbind(this);
    }

}
