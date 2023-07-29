package top.ffshaozi.expresscollection.ui.screen.state

import android.content.ClipboardManager
import android.content.ContentResolver
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import top.ffshaozi.expresscollection.utils.GetSMS

object AppState{
    lateinit var cm: ClipboardManager;
    lateinit var smsData: List<GetSMS.SMSData>;
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    lateinit var cr:ContentResolver
}
