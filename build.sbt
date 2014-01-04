import android.Keys._                                                                                                                                                                                                                         

android.Plugin.androidBuild

name := "ephedra-android"

proguardOptions in Android ++= Seq(
  "-keep class android.widget.TextView"
)

scalacOptions ++= Seq(
  "-feature"
)
