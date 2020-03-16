package com.qq.reader.view;


/**
 * @see {@link TipsManager}
 * @author p_jwcao
 *
 */
public interface IGuide {
	void doGuid(int type);
	int[] getArea(int type);
	void dismiss(int type);
	HighLightInfo getHighLightArea(int type);
	
}
