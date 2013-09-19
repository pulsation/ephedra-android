package eu.pulsation.ephedra

import android.app.Activity
import android.os.Bundle

class EphedraMainActivity extends Activity
{
    /** Called when the activity is first created. */
    override def onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
    }
}
