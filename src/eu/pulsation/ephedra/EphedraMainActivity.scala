package eu.pulsation.ephedra

import android.app.ListActivity
import android.os.Bundle
import android.view.Menu

class EphedraMainActivity extends ListActivity
{
    /** Called when the activity is first created. */
    override def onCreate(savedInstanceState: Bundle)
    {
      lazy val ephedraAlarmHelper = new EphedraAlarmHelper(this)

      super.onCreate(savedInstanceState)
      setContentView(R.layout.main)
      ephedraAlarmHelper.startAlarm()
    }

    override def onCreateOptionsMenu(menu: Menu) : Boolean = {
      this.getMenuInflater().inflate(R.menu.menu, menu)
      true
    }
}
