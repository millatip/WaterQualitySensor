package com.uin.waterqualitysensor

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class SensorValue (var ph: Double = 0.0,
                        var suhu: Double = 0.0)
