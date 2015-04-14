package com.webyousoon.android.memonimo;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ApplicationTest extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(ApplicationTest.class)
                .includeAllPackagesUnderHere().build();
    }

    public ApplicationTest() {
        super();
    }
}