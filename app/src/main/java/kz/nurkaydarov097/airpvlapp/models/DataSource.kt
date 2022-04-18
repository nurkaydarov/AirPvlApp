package kz.nurkaydarov097.airpvl.models

import android.content.Context
import kz.nurkaydarov097.airpvlapp.R


class DataSource(val context: Context) {

    fun getPollutions():List<Air_pollution>{
        return listOf<Air_pollution>(

            Air_pollution(1, context.getString(R.string.pollution_indicator_good), context.getString(R.string.pollution_numerical_value_good), context.getString(R.string.pollution_meaning_good), R.drawable.bd_round_good),
            Air_pollution(2,context.getString(R.string.pollution_indicator_normal),context.getString(R.string.pollution_numerical_value_normal) ,context.getString(R.string.pollution_meaning_normal), R.drawable.bg_round_normal),
            Air_pollution(3, context.getString(R.string.pollution_indicator_bad),context.getString(R.string.pollution_numerical_value_bad), context.getString(R.string.pollution_meaning_bad), R.drawable.bg_round_bad ),
            Air_pollution(4, context.getString(R.string.pollution_indicator_danger), context.getString(R.string.pollution_numerical_value_danger), context.getString(R.string.pollution_meaning_danger), R.drawable.bg_round_danger),
            Air_pollution(5,context.getString(R.string.pollution_indicator_dangerous), context.getString(R.string.pollution_numerical_value_dangerous), context.getString(R.string.pollution_meaning_dangerous), R.drawable.bg_round_dangerous),
            Air_pollution(6, context.getString(R.string.pollution_indicator_very_dangerous), context.getString(R.string.pollution_numerical_value_very_dangerous), context.getString(R.string.pollution_meaning_very_dangerous), R.drawable.bg_round_dangerous)

        )
    }
}