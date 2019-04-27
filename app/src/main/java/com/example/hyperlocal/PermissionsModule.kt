package com.example.hyperlocal

class PermissionsModule {

    private class PermissionAction {
        var permission = arrayListOf<String>()

        var requestPermission: () -> Unit =
            {
                //logError(Error("Permissionn Request not made"))
            }


    }
}