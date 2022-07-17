package and.org.recordream.presentation.mypage

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment


class MypageAccountDeleteFragment : DialogFragment() {

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        /*
        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a new instance of TimePickerDialog and return it
            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                DateFormat.is24HourFormat(activity)
//            )
        }
*/
        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            // Do something with the time chosen by the user
        }
    }
}