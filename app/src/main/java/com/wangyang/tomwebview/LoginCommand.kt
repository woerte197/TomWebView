import android.util.Log
import com.google.auto.service.AutoService
import com.wangyang.common.UserCenterService
import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack
import com.wangyang.tom_web.mainprocess.ICommand
import java.util.*

@AutoService(*[ICommand::class])
class LoginCommand : ICommand {
    private val userCenterService: UserCenterService =
        ServiceLoader.load(UserCenterService::class.java).iterator().next()

    override fun execute(
        jsonParams: MutableMap<String, String>?,
        callBack: IMainProcessToWebViewProcessCallBack?
    ) {
        Log.e(Companion.TAG, "execute: $userCenterService")
        if (!userCenterService.isLogin()) {
            userCenterService.login {
                Log.e(TAG, "execute: $it")
                callBack?.onResult(jsonParams?.get("callBackName"), it)
            }
        }
    }

    override fun name(): String = "login"

    companion object {
        private const val TAG = "LoginCommand"
    }

}