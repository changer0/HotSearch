package com.yuewen.zebra.building;

/**
 *
 * 开发者需遵循的设计规范
 *
 *                  /  ZebraView [V]
 * ZebraFragment [P]
 *                  \  ZebraViewModel [M]
 *
 * @author zhanglulu
 */
public interface IZebraFragment {
    /**
     * 子类提供一个 ZebraView
     * @return IZebraView
     */
    public IZebraView onCreateZebraView();

    /**
     * 子类提供一个 ZebraViewModel
     * @return IZebraViewModel
     */
    public IZebraViewModel onCreateZebraViewModel();
}
