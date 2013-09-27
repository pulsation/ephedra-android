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
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }
}
