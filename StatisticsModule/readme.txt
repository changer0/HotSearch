Module AAR 引入实践
1.构建aar 并应用到项目中
在Studio Project中选中StatisticsModule 在顶部功能栏点击Build->Make Module 'StatisticsModule'
构建完成后在Module目录下build/outputs/aar目录下找到对应文件
copy aar文件到app项目下libs/aar目录下即可

2.gradle配置：
a.Project的build.gradle,在allprojects {repositories {}} 下配置如下信息
    flatDir {
        // Module aar，在多 module 中都要适用的情况下，
        // 其他的module编译会报错，所以需要在所有工程的repositories
        // 下把Library module中的libs目录添加到依赖关系中
        dirs project(':app').file('libs/aar')
    }
b.在需要依赖统计module的module下gradle配置dependencies中，添加如下配置
    oppoDebugCompile(name: 'StatisticsModule-oppo-debug', ext: 'aar')
    oppoReleaseCompile(name: 'StatisticsModule-oppo-release', ext: 'aar')
    oppoRelease_with_signCompile(name: 'StatisticsModule-oppo-release', ext: 'aar')
c.当前依赖StatisticsModule的有 主工程app,AdvModule
（如上，在2.a前提下，所有module均可添加相同配置，而不需指定目录）


3.开发测试：
部分时候，需要多次频繁修改调试时，修改gradle中对项目的依赖为module即可
既:(以OPPO举例)
    oppoDebugCompile(name: 'StatisticsModule-oppo-debug', ext: 'aar')
    oppoReleaseCompile(name: 'StatisticsModule-oppo-release', ext: 'aar')
    oppoRelease_with_signCompile(name: 'StatisticsModule-oppo-release', ext: 'aar')
//    oppoDebugCompile project(path: ':StatisticsModule',configuration:'oppoDebug')
//    oppoReleaseCompile project(path: ':StatisticsModule',configuration:'oppoRelease')
//    oppoRelease_with_signCompile project(path: ':StatisticsModule', configuration: 'oppoRelease')
变更为 ->
//    oppoDebugCompile(name: 'StatisticsModule-oppo-debug', ext: 'aar')
//    oppoReleaseCompile(name: 'StatisticsModule-oppo-release', ext: 'aar')
//    oppoRelease_with_signCompile(name: 'StatisticsModule-oppo-release', ext: 'aar')
    oppoDebugCompile project(path: ':StatisticsModule',configuration:'oppoDebug')
    oppoReleaseCompile project(path: ':StatisticsModule',configuration:'oppoRelease')
    oppoRelease_with_signCompile project(path: ':StatisticsModule', configuration: 'oppoRelease')


// TODO
VIVO暂时未编译
将常用的常修改的RDM eventName/新埋点PageNames/DataTypes提出到module外
构建framwork module?/放到Common中?(common 其他项目依赖，可能导致修改全局编辑速度变慢)
StatisticsModuleCore + StatisticsModule 两级



