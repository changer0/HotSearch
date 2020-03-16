package com.qq.reader.entity.audio.player.core;

import java.io.File;

import com.qq.reader.common.mark.OnlineChapter;
import com.tencent.mars.xlog.Log;

import android.os.Parcel;
import android.os.Parcelable;

public class SongInfo implements Parcelable {

	// 歌曲错误错误定义type
	public static final int ERR_NO = 0;
	public static final int ERR_NO_FILE = -1; // 错误1：文件不存在
	public static final int ERR_FILE = -2; // 错误2：文件格式错误

	private int err; // ..大于等于0标识没错，<0表示有错

	private String filePath;

	private long duration;// 歌曲时长

	private long filesize;// 文件大小

	private long id;//chapterId

	private String mSongName;//歌曲名字

    public OnlineChapter relatedChapter;

	public String mStatParam;

	public String mSecretKey;

    public boolean mIsNeedDecrypt = false;

    private long mBid;//书籍id
	public SongInfo(String filepath,long id) {
		if (filepath == null) {
			filePath = "";
		} else {
			filePath = filepath;
		}
		err = ERR_NO;
		this.id = id;
	}

	public SongInfo(Parcel in) {
		readFromParcel(in);
	}

//	private String nameCache;

    @Deprecated
	public String getName() {
//		if (filePath.length() == 0) {
//			return AudioConfig.DEFAULT_FILEPATH_EMPTY_SONG_NAME;
//		} else {
//			if (nameCache == null) {
//				nameCache = QQMusicUtil.getFilePathName(filePath);
//			}
//			return nameCache;
//		}
		return getSongName();
	}

	public void setDuration(long dur) {
		this.duration = dur;
	}

	public long getDuration() {
		return duration;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setErr(int err) {
		this.err = err;
	}

	public int getErr() {
		return this.err;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public static final Parcelable.Creator<SongInfo> CREATOR = new Parcelable.Creator<SongInfo>() {
		public SongInfo createFromParcel(Parcel in) {
			return new SongInfo(in);
		}

		public SongInfo[] newArray(int size) {
			return new SongInfo[size];
		}
	};

	public void readFromParcel(Parcel in) {
		this.filePath = in.readString();
		if (filePath == null) {
			filePath = "";
		}
		this.err = in.readInt();
		this.duration = in.readLong();
		this.filesize = in.readLong();
		this.id = in.readLong();
		this.mSongName = in.readString();
		this.mBid = in.readLong();
		this.mStatParam = in.readString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof SongInfo) {
			return (this.mBid == ((SongInfo) obj).mBid) && (this.filePath.equals(((SongInfo) obj).filePath));
		}
		return false;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		out.writeString(filePath);
		out.writeInt(err);
		out.writeLong(duration);
		out.writeLong(filesize);
		out.writeLong(id);
		out.writeString(mSongName);
		out.writeLong(mBid);
		out.writeString(mStatParam);
	}
    public void setBookId(long bid) {
		this.mBid = bid;
	}
	public long getBookId()  {
		return mBid;
	}
	public boolean hasLocalFile() {
		try {
			File f = new File(filePath);
			if (f != null && f.exists()) {
				return true;
			}
		} catch (Exception e) {
			Log.printErrStackTrace("SongInfo", e, null, null);
		}
		return false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSongName() {
		return mSongName;
	}

    public String getBookName() {
        if(relatedChapter!=null){
            return relatedChapter.getBookName();
        }
        return null;
    }


	public void setSongName(String mSongName) {
		this.mSongName = mSongName;
	}
}