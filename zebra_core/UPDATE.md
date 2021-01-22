# 新版书城加载框架 DataProvider

> 提供列表数据的加载、解析、缓存等

[设计文档](DESIGN.md)

## 1.0.0

1. 工程建立

## 1.0.1

1. 修改 ReaderBaseDataProvider 获取响应bean 类型的字段

## 1.0.2

1. 升级 Android X
2. Kotlin 适配

## 1.0.3~1.0.6

1. 修改 Provider 回调使用 LiveData 形式

## 1.0.7

1. 添加 AdLoadMode

## 1.0.8

1. 修改 AdLoadMode

## 1.0.9 ~ 1.0.10

1. 支持设置广告超时时间

## 1.0.11

1. 支持设置自定义合并请求

## 1.0.12 【有严重问题，不可用！】

1. 修改缓存模式

## 1.0.13

1. 修复 1.0.12 版本缓存问题

## 1.0.14

1. 同步修改广告库相关

## 1.0.15

1. 解除与 AdSdk 的耦合

## 1.0.16 ~ 1.0.17

1. 解除与 BaseUI 的耦合

## 1.0.18 ~ 1.0.19

1. 修改缓存加密方式，由MD5修改为SHA256

## 1.0.20 ~ 1.0.21

1. 由于publish脚本的原因，浪费掉了两个版本号 T_T

## 1.0.22

1. 优化书城滑动时打断 ViewBindItem 的动画
2. BaseDataItem getStatPageId 做一下兼容
3. 去掉 Router 依赖

## 1.0.23

1. 添加 BaseDataItem 加载失败 Toast （Debug环境）

## 1.0.24

1. fix mStatPageInfo 获取错误

## 1.0.25

1. 重大调整，该版本将 Provider 过期时间的获取抛了出来，开发者自己来处理，需要重写 ReaderBaseDataProvider 的 getExpiredTime
