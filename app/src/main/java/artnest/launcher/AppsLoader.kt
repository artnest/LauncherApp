package artnest.launcher

import android.content.AsyncTaskLoader
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class AppsLoader(context: Context): AsyncTaskLoader<MutableList<AppModel>>(context) {
    var mInstalledApps: MutableList<AppModel>? = null
    val mPackageManager: PackageManager = context.packageManager

    override fun loadInBackground(): ArrayList<AppModel> {
        var apps = mPackageManager.getInstalledApplications(0)
        if (apps == null) {
            apps = mutableListOf<ApplicationInfo>()
        }
    }

}