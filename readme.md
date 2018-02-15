[![Build Status](https://travis-ci.org/lekaha/playone-android.svg?branch=master)](https://travis-ci.org/lekaha/playone-android) [![codecov](https://codecov.io/gh/lekaha/playone-android/branch/master/graph/badge.svg)](https://codecov.io/gh/lekaha/playone-android)

# Playone
This is an app solve you could not play basketball problem.

## Before you start

In order to run and test this app in your local environment at all, you should register an account on Auth0. 
Since the app is still in developing, some of online social connection are depended with the application key(now you can just use debug key) for secure issue.
Or, if you do not want to register a new account on Auth0 you can run and test this app with email registration rather than bind with social services.

### Auth0 + Android + API Seed

This is the seed project you need to use if you're going to create an app that will use Auth0, Android and an API that you're going to be developing. That API can be in any language.

### Configuring login

You must set your Auht0 `ClientId` and `Domain` in this sample so that it works. For that, just open the `app/src/main/res/values/auth0.xml` file and replace the `{CLIENT_ID}` and `{DOMAIN}` fields with your account information.
Also replace `{MOBILE_CUSTOM_SCHEME}` with your ClientId in lowercase with `a0` prefix, e.g.: `a0YOUR_CLIENT_ID`

## Build

### Builing and running in CLI

From the command line run the following commands inside the sample folder

```bash
./gradlew installDebug
adb shell am start -n com.auth0.sample/.MainActivity 
```

### Build and runing in Android Studio
