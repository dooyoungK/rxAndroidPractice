# rxAndroid Practice

===========

This is a simple example code for practicing rxJava in Android application. It will provide how the code can be changed and simplified by using rxJava.

-----
rxAndroidPractice provides the following concepts:
* Some basic operators
* Easier view binding - RxBinding 
* Lambda expression in Android application - RetroLambda
* Practice - Simple registration form and handling asynchronous task 

Setup
-----
It requires to install Java 8 version.

And, add the following to your project `build.gradle` if you don't have :

        apply plugin: 'me.tatarka.retrolambda'


        compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
            }


        dependencies {
            compile fileTree(dir: 'libs', include: ['*.jar'])
            testCompile 'junit:junit:4.12'
            compile 'com.android.support:appcompat-v7:23.1.1'
            compile 'com.android.support:design:23.3.0'
            compile 'com.jakewharton:butterknife:7.0.1'
            compile 'io.reactivex:rxandroid:1.1.0'
            compile 'com.jakewharton.rxbinding:rxbinding:0.3.0'
            compile 'com.google.android.gms:play-services-appindexing:8.1.0'
        }


Also, for using RetroLambda library add the fllowing lines in your app gradle file. :

    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.0-rc1'
        classpath 'me.tatarka:gradle-retrolambda:3.2.5'

    }



Developed By
-----
* Dooyoung Gi

***
