package com.qq.reader.delegate;//
//package com.qq.reader.delegate;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.text.TextUtils;
//import android.view.MenuItem;
//
//import com.qq.reader.core.utils.ToastUtils;
//import com.tencent.mars.xlog.Log;
//
//
//public class MenuDelegate {
//
//    private static final int NO_ID = 0;
//
//    public static void showDescription(Context context, MenuItem item) {
//        final String title = (String) item.getTitle();
//        final String text = TextUtils.isEmpty(title) ? dumpMenuItem(context, item): title;
//        ToastUtils.showToast_Short(text);
//    }
//
//    private static String dumpMenuItem(Context context, MenuItem item) {
//        StringBuilder out = new StringBuilder();
//        int id = item.getItemId();
//        if (id != NO_ID) {
//            final Resources r = context.getResources();
//            if (resourceHasPackage(id) && r != null) {
//                try {
//                    String pkgname;switch (id&0xff000000) {
//                        case 0x7f000000:
//                            pkgname="app";
//                            break;
//                        case 0x01000000:
//                            pkgname="android";
//                            break;
//                        default:
//                            pkgname = r.getResourcePackageName(id);
//                            break;
//                    }
//                    String typename = r.getResourceTypeName(id);
//                    String entryname = r.getResourceEntryName(id);
//                    out.append("[");
//                    out.append(pkgname);
//                    out.append(":");
//                    out.append(typename);
//                    out.append("/");
//                    out.append(entryname);
//                    out.append("]");
//                } catch (Resources.NotFoundException e) {
//                    Log.printErrStackTrace("MenuDelegate", e, null, null);
//                    e.printStackTrace();
//                }
//            }
//        }
//        return out.toString();
//    }
//
//    /**
//     * Return true if given resource identifier includes a package.
//     *
//     * @hide
//     */
//    public static boolean resourceHasPackage(int resid) {
//        return (resid >>> 24) != 0;
//    }
//}
