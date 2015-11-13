# android-sample-app

## 0. Development Environment

- Android Studio 1.4
- Android SDK
- Java 1.7

<br/>
## 1. Way to build this app

- git clone https://github.com/daliworks/android-sample-app.git
- Import the ThingplusSampleApp project in the Project folder on the Android Stuio
- Replace below three values with your own OAuth2 for [OAuth2 Authorization](http://oauth.net/2/) from **res/values/strings.xml**
 - Please refer [Thingplus support document](http://support.thingplus.net/ko/rest-api/getting-started.html) to generate your own OAuth2 Client ID, Client Secret and so on.
 -  string name="**oauth_client_id**"
 -  string name="**oauth_client_secret**"
 -  string name="**oauth_redirect_uri**"
- Build and Run this application on Android Stuio.
- Sign-in to Thingplus from the app you launched.
 
## 2. ScreenShots

![Thingplus Sample Application capture](/assets/thingplus_sample_capture.png)

![Thingplus Sample Application capture](/assets/thingplus_sample_capture_2.png)

![Thingplus Sample Application capture](/assets/thingplus_sample_capture_3.png)