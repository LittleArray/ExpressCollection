package top.ffshaozi.expresscollection.config

import android.content.ClipboardManager
import top.ffshaozi.expresscollection.utils.GetSMS

object AppState{
    lateinit var cm: ClipboardManager;
    lateinit var smsData: List<GetSMS.SMSData>;

}
