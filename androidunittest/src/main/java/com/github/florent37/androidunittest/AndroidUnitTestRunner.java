package com.github.florent37.androidunittest;

import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;
import org.robolectric.internal.bytecode.ShadowMap;
import org.robolectric.manifest.AndroidManifest;

import java.io.File;
import java.util.Properties;

public class AndroidUnitTestRunner extends RobolectricTestRunner {

    public static final String PATH_ASSET = "../../../assets/";
    public static final String PATH_RESOURCE = "../../../res/merged/";
    public static final String PATH_MANIFEST = "build/intermediates/manifests/full/%s/AndroidManifest.xml";

    public static final String CONFIG_APPLICATION = "application";
    public static final String CONFIG_MANIFEST = "manifest";
    public static final String CONFIG_ASSET_DIR = "assetDir";
    public static final String CONFIG_RESOURCE_DIR = "resourceDir";
    public static final String CONFIG_PACKAGE_NAME = "packageName";
    public static final String CONFIG_SDK = "sdk";

    public static final String PATH_PREFIX = "app/";
    final String packageName;
    final String flavor;
    final String buildType;
    final Class<? extends Application> applicationClass;

    public AndroidUnitTestRunner(Class<?> testClass, @NonNull String flavor, @NonNull String buildType, String packageName, @Nullable Class<? extends Application> applicationClass) throws InitializationError {
        super(testClass);
        this.packageName = packageName;
        this.applicationClass = applicationClass;
        this.flavor = flavor;
        this.buildType = buildType;
    }

    public String getPathManifest() {
        String path = buildType;
        if (flavor != null && !flavor.isEmpty()) {
            path = String.format("%s/%s", flavor, path);
        }
        return String.format(PATH_MANIFEST, path);
    }

    public String getPathAssets() {
        String prePath = "";
        String postPath = buildType;
        if (flavor != null && !flavor.isEmpty()) {
            prePath = "../";
            postPath = String.format("%s/%s", flavor, postPath);
        }
        return prePath + PATH_ASSET + postPath;
    }

    public String getPathResources() {
        String prePath = "";
        String postPath = buildType;
        if (flavor != null && !flavor.isEmpty()) {
            prePath = "../";
            postPath = String.format("%s/%s", flavor, postPath);
        }
        return prePath + PATH_RESOURCE + postPath;
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        return builder.build();
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String path = getPathManifest();

        // android studio has a different execution root for tests than pure gradle
        // so we avoid here manual effort to get them running inside android studio
        if (!new File(path).exists()) {
            path = PATH_PREFIX + path;
        }

        config = overwriteConfig(config, CONFIG_MANIFEST, path);
        config = overwriteConfig(config, CONFIG_ASSET_DIR, getPathAssets());
        config = overwriteConfig(config, CONFIG_RESOURCE_DIR, getPathResources());
        if (packageName != null) {
            config = overwriteConfig(config, CONFIG_PACKAGE_NAME, packageName);
        }
        if (applicationClass != null) {
            config = overwriteConfig(config, CONFIG_APPLICATION, applicationClass.getCanonicalName());
        }
        return super.getAppManifest(config);
    }

    @Override
    protected int pickSdkVersion(Config config, AndroidManifest manifest) {
        config = overwriteConfig(config, CONFIG_SDK, String.valueOf(Build.VERSION_CODES.JELLY_BEAN));
        return super.pickSdkVersion(config, manifest);
    }

    @Override
    protected ShadowMap createShadowMap() {
        return super.createShadowMap()
            .newBuilder()
            .build();
    }

    protected Config.Implementation overwriteConfig(Config config, String key, String value) {
        Properties properties = new Properties();
        properties.setProperty(key, value);
        return new Config.Implementation(config, Config.Implementation.fromProperties(properties));
    }
}
