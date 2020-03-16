package com.qq.reader.common.utils;

/**
 * DataBinding所需要用到的实体类.
 *
 * 每一种style都对应一个实体类，每一个大类抽一个基类（例如大书封，中书封）
 * 这个类在include的时候会携带具体的数据绑定到布局上
 *
 * 目前之抽取了一部分，会随时更新
 * @author liubin
 * @date 2018/7/3
 */
public class DataBindingBeanFactory {

    public interface OnHeaderClickListener {
        public void onReferMoreClick();
    }

    public interface OnListClickListener {
        public void onItemClick();
    }

    public interface OnCardClickListener {
        public void onCardClick();
        public void onButtonClick();
        public void onCoverClick();
    }


    public static class HeaderStyle1 extends BaseHeaderStyle {
        public boolean isShowRightText = false;
        public boolean isShowRightIcon = false;
        public String mRightText;

        @Override
        public String getRightText() {
            return mRightText;
        }

        @Override
        public void setRightText(String rightText) {
            mRightText = rightText;
        }

        public boolean isShowRightText() {
            return isShowRightText;
        }

        public void setShowRightText(boolean showRightText) {
            isShowRightText = showRightText;
        }

        public boolean isShowRightIcon() {
            return isShowRightIcon;
        }

        public void setShowRightIcon(boolean rightIcon) {
            isShowRightIcon = rightIcon;
        }
    }

    public static class CardSCoverStyle1 extends BaseCardCoverStyle {
    }



    public static class CardMCoverStyle4 extends BaseCardCoverStyle {
        public String mButtonTips;

        public String getButtonTips() {
            return mButtonTips;
        }

        public void setButtonTips(String buttonTips) {
            mButtonTips = buttonTips;
        }
    }

    public static class CardMCoverStyle3 extends BaseCardCoverStyle {
        public String mButtonTips;

        public String getButtonTips() {
            return mButtonTips;
        }

        public void setButtonTips(String buttonTips) {
            mButtonTips = buttonTips;
        }
    }

    public static class CardLCover extends BaseCardCoverStyle {

    }



    public static class CardLCoverH3 extends BaseCardCoverStyle{
        public boolean isShowText1 = false;
        public boolean isShowText2 = false;
        public boolean isShowImgTag1 = false;
        public boolean isShowImgTag2 = false;

        public boolean isShowText1() {
            return isShowText1;
        }

        public void setShowText1(boolean showText1) {
            isShowText1 = showText1;
        }

        public boolean isShowText2() {
            return isShowText2;
        }

        public void setShowText2(boolean showText2) {
            isShowText2 = showText2;
        }

        public boolean isShowImgTag1() {
            return isShowImgTag1;
        }

        public void setShowImgTag1(boolean showImgTag1) {
            isShowImgTag1 = showImgTag1;
        }

        public boolean isShowImgTag2() {
            return isShowImgTag2;
        }

        public void setShowImgTag2(boolean showImgTag2) {
            isShowImgTag2 = showImgTag2;
        }
    }

    public static class ListStyle1 extends BaseListStyle {

    }

    public static class ListStyle2 extends BaseListStyle {
        public boolean isShowRightText = false;
        public boolean isShowRightIcon = false;
        public boolean isShowRightButton = false;

        public boolean isShowRightText() {
            return isShowRightText;
        }

        public void setShowRightText(boolean showRightText) {
            isShowRightText = showRightText;
        }

        public boolean isShowRightIcon() {
            return isShowRightIcon;
        }

        public void setShowRightIcon(boolean rightIcon) {
            isShowRightIcon = rightIcon;
        }

        public boolean isShowRightButton() {
            return isShowRightButton;
        }

        public void setShowRightButton(boolean showRightButton) {
            isShowRightButton = showRightButton;
        }
    }

    public static class ListStyle5 extends BaseListStyle {
        public boolean isShowRightText = false;
        public boolean isShowRightIcon = false;
        public String mRightText;

        @Override
        public String getRightText() {
            return mRightText;
        }

        @Override
        public void setRightText(String rightText) {
            mRightText = rightText;
        }

        public boolean isShowRightText() {
            return isShowRightText;
        }

        public void setShowRightText(boolean showRightText) {
            isShowRightText = showRightText;
        }

        public boolean isShowRightIcon() {
            return isShowRightIcon;
        }

        public void setShowRightIcon(boolean rightIcon) {
            isShowRightIcon = rightIcon;
        }

    }

    public static class BaseCardCoverStyle {
        public String mCover;
        public String mTitle;
        public String mContent;
        public String mAuthor;
        public String mBid;
        public String mTag1;
        public String mTag2;
        public String mTag3;
        public String mExtra1;
        public String mExtra2;
        public String mScore;
        public String mLabel1;
        public String mLabel2;
        public boolean isShowScoreTag = false;
        public boolean isShowTag1 = false;
        public boolean isShowTag2 = false;
        public boolean isShowTag3 = false;

        public boolean isShowImgTag1 = false;
        public boolean isShowImgTag2 = false;
        public boolean isShowImgTag3 = false;

        public OnCardClickListener mOnCardClickListener;

        public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
            mOnCardClickListener = onCardClickListener;
        }

        public boolean isShowImgTag1() {
            return isShowImgTag1;
        }

        public void setShowImgTag1(boolean showImgTag1) {
            isShowImgTag1 = showImgTag1;
        }

        public boolean isShowImgTag2() {
            return isShowImgTag2;
        }

        public void setShowImgTag2(boolean showImgTag2) {
            isShowImgTag2 = showImgTag2;
        }

        public boolean isShowImgTag3() {
            return isShowImgTag3;
        }

        public void setShowImgTag3(boolean showImgTag3) {
            isShowImgTag3 = showImgTag3;
        }

        public boolean isShowScoreTag() {
            return isShowScoreTag;
        }

        public void setShowScoreTag(boolean showScoreTag) {
            isShowScoreTag = showScoreTag;
        }

        public boolean isShowTag1() {
            return isShowTag1;
        }

        public void setShowTag1(boolean showTag1) {
            isShowTag1 = showTag1;
        }

        public boolean isShowTag2() {
            return isShowTag2;
        }

        public void setShowTag2(boolean showTag2) {
            isShowTag2 = showTag2;
        }

        public boolean isShowTag3() {
            return isShowTag3;
        }

        public void setShowTag3(boolean showTag3) {
            isShowTag3 = showTag3;
        }

        public String getBid() {
            return mBid;
        }

        public void setBid(String bid) {
            mBid = bid;
        }

        public String getCover() {
            return mCover;
        }

        public void setCover(String cover) {
            mCover = cover;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public void setAuthor(String author) {
            mAuthor = author;
        }

        public String getTag1() {
            return mTag1;
        }

        public void setTag1(String tag1) {
            mTag1 = tag1;
        }

        public String getTag2() {
            return mTag2;
        }

        public void setTag2(String tag2) {
            mTag2 = tag2;
        }

        public String getTag3() {
            return mTag3;
        }

        public void setTag3(String tag3) {
            mTag3 = tag3;
        }

        public String getExtra1() {
            return mExtra1;
        }

        public void setExtra1(String extra1) {
            mExtra1 = extra1;
        }

        public String getExtra2() {
            return mExtra2;
        }

        public void setExtra2(String extra2) {
            mExtra2 = extra2;
        }

        public String getScore() {
            return mScore;
        }

        public void setScore(String score) {
            mScore = score;
        }

        public String getLabel1() {
            return mLabel1;
        }

        public void setLabel1(String label1) {
            mLabel1 = label1;
        }

        public String getLabel2() {
            return mLabel2;
        }

        public void setLabel2(String label2) {
            mLabel2 = label2;
        }
    }


    public static class BaseListStyle{
        public String mTitle;
        public String mContent;
        public String mRightText;
        public String mIcon;

        public boolean isShowList = true;

        public OnListClickListener mOnListClickListener;

        public void setOnListClickListener(OnListClickListener onListClickListener) {
            mOnListClickListener = onListClickListener;
        }

        public boolean isShowList() {
            return isShowList;
        }

        public void setShowList(boolean showList) {
            isShowList = showList;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public String getRightText() {
            return mRightText;
        }

        public void setRightText(String rightText) {
            mRightText = rightText;
        }

        public String getIcon() {
            return mIcon;
        }

        public void setIcon(String icon) {
            mIcon = icon;
        }
    }

    public static class BaseHeaderStyle{
        public String mTitle;
        public String mContent;
        public String mRightText;
        public boolean isShowHeader = true;
        public OnHeaderClickListener mOnHeaderClickListener;

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public boolean isShowHeader() {
            return isShowHeader;
        }

        public void setShowHeader(boolean showHeader) {
            isShowHeader = showHeader;
        }



        public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
            mOnHeaderClickListener = onHeaderClickListener;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getRightText() {
            return mRightText;
        }

        public void setRightText(String rightText) {
            mRightText = rightText;
        }
    }

}
