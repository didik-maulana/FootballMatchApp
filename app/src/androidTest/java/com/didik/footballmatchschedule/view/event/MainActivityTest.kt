package com.didik.footballmatchschedule.view.event

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    /* menguji recyclerview berjalan dengan baik
    * melakukan action scroll dan click pada list yang posisinya sampai 12*/
   @Test
    fun testRecyclerViewBehavior() {
        Thread.sleep(1000)
        onView(withText("LAST")).perform(click())

        Thread.sleep(3000)
       onView(withId(list_event))
               .check(matches(isDisplayed()))

       Thread.sleep(1000)
       onView(withId(list_event)).perform(
               RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
       onView(withId(list_event)).perform(
               RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
       Thread.sleep(3000)
   }

    // menguji tab berjalan dengan baik dan dapat di click
    @Test
    fun testTabLayout() {
        Thread.sleep(1000)
        onView(withId(menu_last)).perform(click())

        Thread.sleep(1000)
        onView(withId(menu_next)).perform(click())

        Thread.sleep(1000)
        onView(withId(menu_event_fav)).perform(click())

        Thread.sleep(3000)
    }

    // Test untuk menampilkan detail event ke 2 dari list
    @Test
    fun testDetailEvent() {
        Thread.sleep(1000)
        onView(withId(list_event))
                .check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(list_event)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(3000)
    }

    /* menguji tambah favorite pada event ke-1 dari list event
     * menguji hapus event yang tersimpan ke-1 dari list favorite */
    @Test
    fun addDeleteFav() {
        Thread.sleep(1000)
        onView(withId(list_event))
                .check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(list_event)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        onView(withId(R.id.add_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.add_fav)).perform(click())

        Thread.sleep(1000)
        pressBack()

        Thread.sleep(1000)
        onView(withId(menu_event_fav)).perform(click())

        Thread.sleep(1000)
        onView(withId(R.id.list_fav)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        onView(withId(R.id.add_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.add_fav)).perform(click())

        Thread.sleep(1000)
        pressBack()

        Thread.sleep(3000)
    }

}