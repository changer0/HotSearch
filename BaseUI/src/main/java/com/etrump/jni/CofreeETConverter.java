package com.etrump.jni;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;


public class CofreeETConverter {

    public static final int		ET_CONVERTER_REUSE_TTF_GLYPH_FLAG		= 0x0001;
    public static final int		ET_CONVERTER_LOAD_FTF_FROM_MEMORY_FLAG		= 0x0002;
    public static final int		ET_CONVERTER_LOAD_TTF_FROM_MEMORY_FLAG		= 0x0004;
    public static final int		ET_CONVERTER_GLYPH_CACHE_SIZE_MASK		= 0x00F0;
    public static final int		ET_CONVERTER_CHECK_OUTLINE_FLAG			= 0x0100;

    static {
        System.loadLibrary("etconverter");
    }
	
    private static CofreeETConverter sInstance = null;
	
    public static CofreeETConverter getInstance() {
        if (sInstance == null) {
            sInstance = new CofreeETConverter();
        }

        return sInstance;
    }

    /**
     * Produce a TTF font from FTF. if the ttfFilePath is existed, will reuse its 
     * outline data according to flags. 
     * @param ftfFilePath:	FTF file absolute path
     * @param ttfFilePath:	TTF file absolute path
     * @param unicodesSpecified:  specified unicodes
     * @param flags:  the option flags about how to convert 
     * @return success or failure
     */
    public native boolean native_ftf2ttf(String ftfFilePath, String ttfFilePath, String unicodesSpecified, int flags);
	
    /**
     * Check the TTF font with FTF
     * outline data according to flags. 
     * @param ftfFilePath:	FTF file absolute path
     * @param ttfFilePath:	TTF file absolute path
     * @param unicodesSpecified:  specified unicodes
     * @param flags:  the option flags about how to convert 
     * @return success or failure
     */
    public native boolean native_check_ttf(String ftfFilePath, String ttfFilePath, String unicodesSpecified, int flags);
	
    /**
     * Produce a TTF font from FTF. if the ttfFilePath is existed, will reuse its 
     * outline data according to flags. 
     * @param ftfFileData:      FTF file data
     * @param ttfFilePath:      TTF file absolute path
     * @param unicodesSpecified:  specified unicodes
     * @param flags:  the option flags about how to convert 
     * @return success or failure
     */
    public native boolean native_ftf2ttf_ex(byte[] ftfFileData, String ttfFilePath, String unicodesSpecified, int flags);

    /**
     * Check the TTF font with FTF
     * outline data according to flags. 
     * @param ftfFileData:      FTF file data
     * @param ttfFilePath:      TTF file absolute path
     * @param unicodesSpecified:  specified unicodes
     * @param flags:  the option flags about how to convert 
     * @return success or failure
     */
    public native boolean native_check_ttf_ex(byte[] ftfFileData, String ttfFilePath, String unicodesSpecified, int flags);

    /**
     * Quick check the TTF font with FTF
     * outline data according to flags. 
     * @param ftfFilePath:      FTF file absolute path
     * @param ttfFilePath:      TTF file absolute path
     * @return success or failure
     */
    public native boolean native_quick_check(String ftfFilePath, String ttfFilePath);

    /**
     * Quick check the TTF font with FTF
     * outline data according to flags. 
     * @param ftfFileData:      FTF file data
     * @param ttfFilePath:      TTF file absolute path
     * @return success or failure
     */
    public native boolean native_quick_check_ex(byte[] ftfFileData, String ttfFilePath);

    /**
     * Check the font is fulltype font or not.
     * @param ftfFilePath:      FTF file absolute path
     * @return success or failure
     */
    public native boolean native_is_ftf(String ftfFilePath);

    /**
     * Check the font is fulltype font or not.
     * @param ftfFileData:      FTF file data
     * @return success or failure
     */
    public native boolean native_is_ftf_ex(byte[] ftfFileData);


    /**
     * 获取文件MD5码
     *
     */
    public static String getMd5ByFile(File file) {
        String value = "";
        FileInputStream in = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            final byte[] buffer = new byte[8 * 1024];
            int byteCount;
    
            while( (byteCount = in.read(buffer)) > 0 ) {
                md5.update(buffer, 0, byteCount);
            }

            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);  
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {  
            try {  
                if(null != in)
                    in.close();  
            }
            catch (IOException e) {
                e.printStackTrace();  
            }
        }

        return value;  
    }  
}

