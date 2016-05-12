package com.doo.study.rxjavapractice.retrofit.model;

/**
 * Created by dooyoungki on 5/10/16.
 */
public class Github {
    private String login;
    private String blog;
    private int public_repos;

    public Github(String login, String blog, int public_repos) {
        this.login = login;
        this.blog = blog;
        this.public_repos = public_repos;
    }


    public String getLogin(){
        return login;
    }

    public String getBlog(){
        return blog;
    }

    public int getPublicRepos(){
        return public_repos;
    }

    @Override
    public String toString() {
        return "\nlogin: " + login + "\nblog: " + blog + "\nrepos: " + public_repos;
    }
}
