/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.cameraview.demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Check, request permission utils
 * Created by neo on 7/26/2016.
 */
public class PermissionUtils {

  public static boolean checkToRequest(Activity activity, String permission, int requestCode) {
    int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);

    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        Log.i("PermissionUtils", "Should show request permission rationale");
      } else {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
      }

      return false;
    } else {
      return true;
    }
  }

  public static boolean needRequestPermissions(Context context, Fragment fragment, String[] permissions, int requestCode) {
    if (hasPermissions(context, permissions)) {
      return false;
    }

    fragment.requestPermissions(permissions, requestCode);
    return true;
  }

  public static boolean hasPermissions(Context context, String[] permissions) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
      for (String permission : permissions) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
          return false;
        }
      }
    }
    return true;
  }


  public static boolean isPermissionGranted(Activity activity, String permission) {
    return ContextCompat.checkSelfPermission(activity, permission) ==
        PackageManager.PERMISSION_GRANTED;
  }

  public static boolean isPermissionsGranted(Context context, @NonNull int[] grantResults) {
    for (int grantResult : grantResults) {
      if (grantResult != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }

    return true;
  }
}
