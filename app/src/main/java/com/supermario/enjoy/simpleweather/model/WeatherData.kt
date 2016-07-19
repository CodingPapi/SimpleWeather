package com.supermario.enjoy.simpleweather.model


data class WeatherData(val alarms: List<Alarms>,
                                 val now: Now,
                                 val suggestion: Suggestion,
                                 val aqi: Aqi,
                                 val basic: Basic,
                                 val daily_forecast: List<Daily_forecast>,
                                 val hourly_forecast: List<Hourly_forecast>,
                                 val status: String) {


    data class Suggestion(val uv: Uv,
                          val cw: Cw,
                          val trav: Trav,
                          val comf: Comf,
                          val drsg: Drsg,
                          val sport: Sport,
                          val flu: Flu)

    data class Uv(val txt: String,
                  val brf: String)

    data class Drsg(val txt: String,
                    val brf: String)

    data class Sport(val txt: String,
                     val brf: String)

    data class Cw(val txt: String,
                  val brf: String)

    data class Trav(val txt: String,
                    val brf: String)

    data class Comf(val txt: String,
                    val brf: String)

    data class Flu(val txt: String,
                   val brf: String)

    data class Basic(val city: String,
                     val update: Update,
                     val lon: String,
                     val id: String,
                     val cnty: String,
                     val lat: String)

    data class Update(val loc: String,
                      val utc: String)

    data class Daily_forecast(val date: String,
                              val pop: String,
                              val hum: String,
                              val vis: String,
                              val astro: Astro,
                              val pres: String,
                              val pcpn: String,
                              val tmp: Tmp,
                              val cond: Cond,
                              val wind: Wind) {
        data class Cond(val txt_n: String,
                        val code_n: String,
                        val code_d: String,
                        val txt_d: String)
    }

    data class Wind(val sc: String,
                    val spd: String,
                    val deg: String,
                    val dir: String)


    data class Astro(val ss: String,
                     val sr: String)

    data class Tmp(val min: String,
                   val max: String)

    data class Now(val hum: String,
                   val vis: String,
                   val pres: String,
                   val pcpn: String,
                   val fl: String,
                   val tmp: String,
                   val cond: Cond,
                   val wind: Wind) {

        data class Cond(var txt: String,
                        var code: String)
    }

    data class Alarms(val txt: String,
                      val stat: String,
                      val level: String,
                      val title: String,
                      val type: String)

    data class Aqi(val city: City)

    data class City(val no2: String,
                    val o3: String,
                    val pm25: String,
                    val qlty: String,
                    val so2: String,
                    val aqi: String,
                    val pm10: String,
                    val co: String)

    data class Hourly_forecast(val date: String,
                               val pop: String,
                               val hum: String,
                               val pres: String,
                               val tmp: String,
                               val wind: Wind)

}
