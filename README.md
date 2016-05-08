# Android Unit Test

#Usage

```java
@RunWith(CustomTestRunner.class)
public class MainActivityTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @MActivity MainActivity activity;
    @Mock User user;

    @Test
    public void testOnResume() throws Exception {
        assertThat(activity.resumed).isFalse();

        androidUnitTest.activity().resume();

        assertThat(activity.resumed).isTrue();
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

## Context

Retrieve Context easily

```java
@RunWith(CustomTestRunner.class)
public class MyTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @MContext Context context;
}
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
