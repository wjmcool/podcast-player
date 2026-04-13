# 🔧 APK 构建指南

## 🚀 自动构建（推荐）

### 步骤 1: 触发 GitHub Actions

1. 访问仓库：https://github.com/wjmcool/podcast-player
2. 点击顶部导航栏的 **"Actions"** 标签
3. 在左侧侧边栏选择 **"Build APK"** 工作流
4. 点击右上角的 **"Run workflow"** 按钮
5. 确认分支选择为 `podcast_qwen`
6. 点击 **"Run workflow"**

### 步骤 2: 等待构建完成

- 构建过程通常需要 3-5 分钟
- 您可以点击当前运行中的工作流查看实时日志

### 步骤 3: 下载 APK

1. 构建完成后，页面会在底部显示 **"Artifacts"** 区域
2. 找到 **"podcast-player-debug"** 文件
3. 点击文件名下载 APK
4. APK 文件名：`app-debug.apk`

---

## 📱 安装 APK

1. 将下载 APK 传输到 Android 手机
2. 在手机上启用 **"安装未知应用"** 权限（设置 → 安全）
3. 点击 APK 文件进行安装
4. 打开应用享受播客！

---

## 🛠️ 本地构建（高级用户）

### 前置要求

- Android Studio Arctic Fox 或更高版本
- JDK 17
- Android SDK 34
- Git

### 构建步骤

```bash
# 1. 克隆仓库
git clone https://github.com/wjmcool/podcast-player.git
cd podcast-player

# 2. 构建 Debug APK
./gradlew assembleDebug

# 3. APK 位置
# app/build/outputs/apk/debug/app-debug.apk
```

---

## ❓ 常见问题

### 问题 1: 构建失败 "BUILD FAILED"

**原因**: 依赖项版本不匹配或网络问题

**解决方法**:
1. 检查 Gradle 版本：确保 `gradle-wrapper.properties` 中是 8.2
2. 检查 Java 版本：确保使用 JDK 17
3. 检查网络连接

### 问题 2: "No such file or directory: gradlew"

**原因**: Gradle Wrapper 文件未正确添加

**解决方法**:
1. 确保 `gradlew` 和 `gradle/wrapper/gradle-wrapper.properties` 文件已提交
2. 运行 `git status` 检查文件状态

### 问题 3: 下载 APK 找不到

**原因**: 构建还未完成或已过期

**解决方法**:
1. 刷新 GitHub Actions 页面
2. 等待 5 分钟让构建完成
3. 检查构建日志确认成功

---

## 📂 项目文件结构

```
PodcastPlayer/
├── .github/
│   └── workflows/
│       └── build.yml              # CI/CD 配置
├── app/
│   ├── src/main/
│   │   ├── java/com/openclaw/podcast/
│   │   │   ├── MainActivity.kt
│   │   │   ├── MediaService.kt
│   │   │   ├── adapter/
│   │   │   └── model/
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   │   ├── ic_launcher.xml
│   │   │   │   ├── ic_launcher_foreground.xml
│   │   │   │   └── bg_*.xml
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## 🎯 下一步

1. ✅ 触发自动构建
2. ✅ 下载 APK 文件
3. ✅ 在手机上安装测试
4. ✅ 报告 bug 或提出改进建议

---

**最后更新**: 2026-04-13  
**状态**: ✅ 构建配置已完成
