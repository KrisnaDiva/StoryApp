package com.krisna.diva.storyapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.krisna.diva.storyapp.ui.view.LoginActivity
import com.krisna.diva.storyapp.ui.view.MainActivity
import com.krisna.diva.storyapp.ui.view.WelcomeActivity
import com.krisna.diva.storyapp.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LoginLogoutTest {

    private val email = "krisnadiva04@gmail.com"
    private val password = "password"

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)


    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testLoginLogout() {
        Intents.init()
        onView(withId(R.id.ed_login_email)).perform(typeText(email))
        onView(withId(R.id.ed_login_password)).perform(typeText(password))
        closeSoftKeyboard()
        onView(withId(R.id.btn_login)).perform(click())

        intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))


        onView(withId(R.id.navigation_profile)).perform(click())
        onView(withId(R.id.action_logout)).perform(click())

        intended(IntentMatchers.hasComponent(WelcomeActivity::class.java.name))
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_register)).check(matches(isDisplayed()))
    }
}