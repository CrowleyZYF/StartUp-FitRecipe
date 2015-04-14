# Facebook Native Login
## Fearure
使用了 Native  的方式来完成 Facebook 的第三方登录。React Native 会调用 Objective-C 的代码来完成登录逻辑，使用了 FacebookSDK 来做。

考虑到之后可能会有不需要 Native 支持的登录逻辑，所以建立了一个分支。

## Issue
1. 把 `Bolts.framework`, `FBSDKCoreKit.framework`, `FBSDKLoginKit.framework` 三个文件拖拽到项目的 `Frameworks` 文件夹中。导入时选择 `Craete Groups`，**不勾选** `Cope items if needed`。
2. 在项目设置页面的 `Build Settings` Tab 页下，在左上角选择 All。 找到 `Allow Non-modilar Includes In Framework Modules` 这个设置选项。把它改成 `Yes`。

如果不进行第二步，在项目 Build 的过程中会提示 `Cannot build FBSDKCoreKit`。
