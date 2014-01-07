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

import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.TextView
import android.text.method.LinkMovementMethod

import android.view.{LayoutInflater, ViewGroup}

class AboutFragment extends Fragment {

  final private val TAG="eu.pulsation.ephedra.AboutFragment"
  
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.about, container, false)
  }

  def dataOriginDetails : TextView = {
    getView().findViewById(R.id.about_data_origin_details) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def responsabilityDetails : TextView = {
    getView().findViewById(R.id.about_data_responsability_details) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def dataUseConditions : TextView = {
    getView().findViewById(R.id.about_data_use_conditions) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  override def onStart() {
    // Activate web links
    dataOriginDetails.setMovementMethod(LinkMovementMethod.getInstance())
    responsabilityDetails.setMovementMethod(LinkMovementMethod.getInstance())
    dataUseConditions.setMovementMethod(LinkMovementMethod.getInstance())
    
    super.onStart()
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
