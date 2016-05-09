# Android Unit Test

Save time & clear your unit tests on Android ! 

Use annotations to inject Context, Activities, Fragments and Views into your tests

#Usage

```java
@RunWith(CustomTestRunner.class)
public class MainActivityTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RActivity MainActivity activity;
    @Mock User user;

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

## Activity State

Set initial activity state (by default activity is created())

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RActivity(
        created = true / false,
        started = true / false,
        resumed = true / false,
        paused = true / false,
        stoped = true / false,
        destroyed = true / false
        )
    MainActivity activity;

}
```

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RActivity MainActivity activity;
    
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

# Download

 [ ![Download](https://api.bintray.com/packages/florent37/maven/AndroidUnitTest/images/download.svg) ](https://bintray.com/florent37/maven/AndroidUnitTest/_latestVersion)

```java
testCompile 'com.github.florent37:androidunittest:(last version)'

testCompile 'junit:junit:4.12'
testCompile 'org.mockito:mockito-core:1.10.19'
testCompile 'org.robolectric:robolectric:3.0'
```

#Credits

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/profile/view?id=297860624">
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
