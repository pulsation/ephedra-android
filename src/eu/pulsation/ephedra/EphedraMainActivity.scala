package eu.pulsation.ephedra

import android.app.{Activity, Fragment, FragmentTransaction}
import android.os.Bundle
import android.view.Menu
import android.util.Log

import scala.collection.mutable.{Subscriber, Publisher}

class EphedraMainActivity extends Activity with Subscriber[RSSItemSelectedEvent, Publisher[RSSItemSelectedEvent]] {

  final private val TAG="eu.pulsation.ephedra.EphedraMainActivity"

  lazy val alertListFragment = {
    new AlertListFragment()
  }

  lazy val alertDetailsFragment = {
    new AlertDetailsFragment()
  }

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

    setContentView(R.layout.main)

    ephedraAlarmHelper.startAlarm()

    if (findViewById(R.id.fragment_container) != null) {
      // Fragment container exists.
      if (savedInstanceState == null) {
        // We're not beeing restored from a previous state.
        displayInitialFragment(alertListFragment)
      }
    }
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }

  // An alert has been selected in the list.
  override def notify(pub: Publisher[RSSItemSelectedEvent], event: RSSItemSelectedEvent): Unit = {

    val rssItem = event.rssItem

    // Replace list fragment by details fragment
    val transaction: FragmentTransaction = this.getFragmentManager().beginTransaction()

    val args = new Bundle()
    args.putString("title", rssItem.title)
    args.putString("content", rssItem.content)
    args.putString("description", rssItem.description)
    args.putString("link", rssItem.link)
    alertDetailsFragment.setArguments(args)
    transaction.replace(R.id.fragment_container, alertDetailsFragment)
    transaction.addToBackStack(null)

    transaction.commit()
  }
}
