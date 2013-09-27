package eu.pulsation.ephedra

import android.app.Activity
import android.os.Bundle
import android.view.Menu

class EphedraMainActivity extends Activity {

  final private val TAG="eu.pulsation.ephedra.EphedraMainActivity"
  
  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    lazy val ephedraAlarmHelper = new AlarmHelper(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    if (findViewById(R.id.fragment_container) != null) {
      if (savedInstanceState != null) {
        return;
      }
    }
    val alertListFragment = new AlertListFragment()
    alertListFragment.setArguments(getIntent().getExtras())

    getFragmentManager().beginTransaction().add(R.id.fragment_container, alertListFragment).commit()
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }
}
