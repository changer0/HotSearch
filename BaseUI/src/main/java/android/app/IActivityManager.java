package android.app;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public abstract interface IActivityManager {
	public static class WaitResult implements Parcelable {
		public int result;
		public boolean timeout;
		public ComponentName who;
		public long thisTime;
		public long totalTime;
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
		}
	};
	  public String getProviderMimeType(Uri uri, int userId) throws RemoteException;
	
	 public WaitResult startActivityAndWait(IApplicationThread caller, String callingPackage,
			       Intent intent, String resolvedType, IBinder resultTo, String resultWho,
			int requestCode, int flags, String profileFile,
			ParcelFileDescriptor profileFd, Bundle options, int userId) throws RemoteException;
	 public WaitResult startActivityAndWait(IApplicationThread caller, 
		       Intent intent, String resolvedType, IBinder resultTo, String resultWho,
		int requestCode, int flags, String profileFile,
		ParcelFileDescriptor profileFd, Bundle options, int userId) throws RemoteException;
}
