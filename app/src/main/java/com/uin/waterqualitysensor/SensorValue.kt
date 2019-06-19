package com.uin.waterqualitysensor

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class SensorValue (var ph: Long? = 0,
                        var suhu: Long? = 0)
