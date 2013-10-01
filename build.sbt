import android.Keys._                                                                                                                                                                                                                         

android.Plugin.androidBuild

name := "Ephedra"

proguardOptions in Android ++= Seq(
  "-keep class android.widget.TextView"
)

scalacOptions ++= Seq(
  "-feature"
)
