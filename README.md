# Android Unit Test

Save time & clear your unit tests on Android ! 

Use annotations to inject Context, Activities, Fragments and Views into your tests

<a target='_blank' rel='nofollow' href='https://app.codesponsor.io/link/iqkQGAc2EFNdScAzpwZr1Sdy/florent37/AndroidUnitTest'>
  <img alt='Sponsor' width='888' height='68' src='https://app.codesponsor.io/embed/iqkQGAc2EFNdScAzpwZr1Sdy/florent37/AndroidUnitTest.svg' />
</a>

# Usage

```java
@RunWith(CustomTestRunner.class)
public class MainActivityTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RContect Context context; //inject the app context
    @RActivity MainActivity activity; //generates the tested activity
    @Mock User user; //mock an user

    @Test
    public void testDisplayUser() throws Exception {
        // Given
        given(user.getName()).willReturn("florent");
        
        // When
        activity.display(user);
        
        // Then
        assertThat(activity.textView.getText()).isEqualTo("florent");
    }
}
```

## TestRunner

Simplify Robolectric Integration

```java
public class CustomTestRunner extends AndroidUnitTestRunner {
    public CustomTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE, BuildConfig.APPLICATION_ID, TestMyApplication.class);
    }
}
```

## Activity

Set initial activity state (by default activity is created())

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RActivity(state = CREATED / STARTED / RESUMED / PAUSED / STOPPED / DESTROYED)
    MainActivity activity;
    
    @Test
    public void testMyFunction(){
         androidUnitTest.activity().resume();
    }

}
```

Note that the injected activity is a spy !

```java
verify(activity, times(2)).someMethod(anyInt());
```

## Context

Retrieve Context easily

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RContext Context context;
}
```

Note that the injected context is a spy !

```java
verify(context, times(2)).someMethod(anyInt());
```

## View

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RView CustomView customView;
    
    @Test
    public void testDisplayUser() throws Exception {
        // Given
        given(user.getName()).willReturn("florent");
        
        // When
        mainView.display(user);
        
        // Then
        verify(customView).displayText("florent");
    }
}
```

Note that the injected view is a spy !

## Fragment

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RFragment MyFragment myFragment;
    @Mock User user;
    
    @Test
    public void testDisplayUser() throws Exception {
        // Given
        given(user.getName()).willReturn("florent");
        
        // When
        myFragment.display(user);
        
        // Then
        verify(myFragment).displayText("florent");
    }
}
```

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RFragment(
        attached = true / false,
        tag = "fragmentTag"
    )
    MyFragment myFragment;
        
    @Test
    public void testMyFunction() throws Exception {
        androidUnitTest.fragment().addToActivity(myFragment)
    }
}
```

Note that the injected fragment is a spy !

# Download

<a href='https://ko-fi.com/A160LCC' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi1.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

 [ ![Download](https://api.bintray.com/packages/florent37/maven/AndroidUnitTest/images/download.svg) ](https://bintray.com/florent37/maven/AndroidUnitTest/_latestVersion)

```java
testCompile 'com.github.florent37:androidunittest:(last version)'

testCompile 'junit:junit:4.12'
testCompile 'org.mockito:mockito-core:1.10.19'
testCompile 'org.robolectric:robolectric:3.0'
```

# Credits

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

<a href="https://play.google.com/store/apps/details?id=com.github.florent37.florent.champigny">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>
<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://fr.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>

License
--------

    Copyright 2016 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
