package com.qq.reader.adv;

import com.alibaba.android.arouter.facade.template.IProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menwentong on 2017/8/2.
 */

public interface IAdvService extends IProvider{

    public void getAdListFormServer();

//    public void buildBookMoreInfo(String bid);

    public ArrayList<Advertisement> getAdvs(String type);
    public ArrayList<Advertisement>  getAllAdvs(String type);
    public List<Advertisement> getDebugALLAdv(String type) ;
    public Advertisement getAdv(String type);
//    public Advertisement getRandomAdv(String type);
//    public Advertisement getIndexAdv(String type,int index);

    public void updateAdv(Advertisement adv);
    public void updateAdv(Advertisement adv,boolean deleteFromCache);

//    public boolean isAdvResPackageReady(Advertisement adv);
    public String getAdvResPackageUnZipDir(Advertisement adv);
//    public void downloadMainTabAdvResPackage(Advertisement adv);

    public boolean isNeedToShowAdv();
//    public boolean isNeedToRefreshShelfAdv();
    public void updateCloseAdvTime();
//    public void clearCloseAdvStatus();


    public boolean checkDiscoveryMonthlyPackageAdv(boolean cancelRedPoint);
    public boolean checkListenZoneTip(boolean cancelRedPoint);
    public boolean checkH5GameCenterIsShowAdv();
    public boolean checkH5GameCenterPackageAdv(boolean cancelRedPoint);

//    public Advertisement getProfileMonthlyAdv();
//    public Advertisement getProfileTextAdv();
//    public Advertisement getProfileTextAdv(int index);
    public boolean getAdvNewTipState(Object obj);
    public void setAdvNewTipState(Object obj, boolean isShown);

//    public Advertisement getCurAdv(long bid,int chapterId,boolean hasVoteComponent);
//    public Advertisement getCurAdv(String bid);

    /*******************************************阿童木*************************************************/

    //飞读2.0新增广告位从阿童木下发
    void getAdListFromATM();
    public void updateStateForATM(Advertisement adv);
    public void updateLastShowTimeForATM(Advertisement adv);
    public List<Advertisement> getAdvsForATM(String adv);

}
