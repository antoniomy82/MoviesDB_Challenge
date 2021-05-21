package com.antoniomy82.moviesdb_challenge;

import org.junit.Test;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FragmentTestRuleTest {

    @Test
    public void createFragment() {
        FragmentTestRule<?, Fragment> testRule = new FragmentTestRule<>(FragmentActivity.class, Fragment.class);

        assertThat(testRule.createFragment(), is(notNullValue()));
    }

    @Test
    public void getFragmentNoCreate() {
        FragmentTestRule<?, Fragment> testRule = new FragmentTestRule<>(FragmentActivity.class, Fragment.class);

        assertThat(testRule.getFragment(), is(nullValue()));
    }
}
