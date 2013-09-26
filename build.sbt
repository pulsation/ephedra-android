import android.Keys._                                                                                                                                                                                                                         

android.Plugin.androidBuild

name := "Ephedra"

proguardOptions in Android ++= Seq(
  "-keepclasseswithmembers class eu.pulsation.ephedra.EphedraLoadRSSAsyncTask { protected eu.pulsation.ephedra.EphedraRSSFeed doInBackground(String...); }"
)
