/**
 *  Ephedra Food Alerts
 *  Copyright (C) 2013-2014 Philippe Sam-Long aka pulsation
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.pulsation.ephedra

import android.app.{Activity, Fragment}
import android.os.Bundle
import android.view.{Menu, MenuItem}
import android.util.Log

import rx.lang.scala.subjects.PublishSubject

class EphedraMainActivity extends Activity {

  final private val TAG="eu.pulsation.ephedra.EphedraMainActivity"

  lazy val alertListFragment =  new AlertListFragment()
  lazy val alertDetailsFragment =  new AlertDetailsFragment()
  lazy val aboutFragment =  new AboutFragment()

  lazy val rssStoredData =  new RSSStoredData(this)

  // PubSub for RSS items that have already been read.
  lazy val readRSSItems =  PublishSubject[RSSItem]()

  /**
   * Switch from a fragment to another
   */
  def switchToFragment(fragment: Fragment) {
    val transaction = getFragmentManager().beginTransaction()

    transaction.replace(R.id.fragment_container, fragment)
    transaction.addToBackStack(null)

    transaction.commit()
  }

  /**
   * Show the initial fragment to be displayed
   */
  def displayInitialFragment(fragment: Fragment) {
    val transaction = getFragmentManager().beginTransaction()

    fragment.setArguments(getIntent().getExtras())
    transaction.add(R.id.fragment_container, fragment)

    transaction.commit()
  }

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    lazy val ephedraAlarmHelper = new AlarmHelper(this)

    super.onCreate(savedInstanceState)

    // Subscribe to item read event
    readRSSItems.subscribe(item => { rssStoredData.notifyReadRSSItem(item) })

    // Display notifications
    ephedraAlarmHelper.startAlarm()

    // Display list view
    setContentView(R.layout.main)
    if (findViewById(R.id.fragment_container) != null) {
      // Fragment container exists.
      if (savedInstanceState == null) {
        // We're not being restored from a previous state.
        alertListFragment.selectedRSSItem.subscribe(item => this.itemSelected(item))
        displayInitialFragment(alertListFragment)
      }
    }
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem) : Boolean = {
    item.getItemId() match {
      /*
      case R.id.parameters => {
        Log.v(TAG, "Parameters pressed.")
        true
      }
      */
      case R.id.about => {
        switchToFragment(aboutFragment)
        true
      }
      case _ => super.onOptionsItemSelected(item)
    }
  }

  // An alert has been selected in the list.
  def itemSelected(rssItem : RSSItem) = {

    val args = new Bundle()

    args.putString("title", rssItem.title)
    args.putString("content", rssItem.content)
    args.putString("description", rssItem.description)
    args.putString("link", rssItem.link)
    alertDetailsFragment.setArguments(args)

    switchToFragment(alertDetailsFragment)

    readRSSItems.onNext(rssItem)
  }
}
