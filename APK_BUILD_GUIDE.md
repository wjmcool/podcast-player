# Kotlin 播客 APK 构建指南

## 方法1：在电脑上使用 Android Studio（推荐）

### 步骤：
1. 将整个项目文件夹 `/sdcard/OpenClaw/PodcastKotlinApp/` 复制到电脑
2. 打开 Android Studio
3. 点击 "File" -> "Open" -> 选择项目文件夹
4. 等待 Gradle 同步完成
5. 点击 "Build" -> "Build Bundle(s) / APK(s)" -> "Build APK(s)"
6. APK 文件将生成在 `app/build/outputs/apk/debug/` 目录

### 如果 Gradle 同步失败：
1. 删除 `build.gradle.kts` 中的 `kotlin-android` 插件
2. 或者创建一个新的 Android 项目，将源代码复制过去

## 方法2：使用命令行构建（需要 Termux + Android SDK）

### 安装必要工具：
```bash
pkg install git wget unzip
```

### 下载 Android Studio 项目模板：
```bash
cd /data/data/com.termux/files/home
git clone https://github.com/android/studio-poet
```

### 创建新项目：
```bash
cd studio-poet
./gradlew createProject -PprojectName="PodcastApp" -Ppackage="com.podcast.app"
```

### 复制源代码：
```bash
cp -r /data/data/com.termux/files/home/.openclaw/workspace/PodcastKotlinApp/src/main/java/com/podcast/kotlin/app/* \
   /data/data/com.termux/files/home/PodcastApp/app/src/main/java/com/podcast/app/
cp -r /data/data/com.termux/files/home/.openclaw/workspace/PodcastKotlinApp/src/main/res/* \
   /data/data/com.termux/files/home/PodcastApp/app/src/main/res/
```

### 构建 APK：
```bash
cd /data/data/com.termux/files/home/PodcastApp
./gradlew assembleDebug
```

## 方法3：在线构建服务

1. 访问 https://appcircle.io 或类似服务
2. 上传项目文件
3. 在线构建 APK

## APK 安装

构建完成后，将 APK 文件传输到手机并安装：

```bash
# 使用 ADB 安装
adb install app/build/outputs/apk/debug/app-debug.apk

# 或直接在手机上安装
# 将 APK 文件复制到手机，然后点击安装
```

## 项目文件位置

- 主项目：`/sdcard/OpenClaw/PodcastKotlinApp/`
- 源代码：`/sdcard/OpenClaw/PodcastKotlinApp/src/`
- 构建输出：`/sdcard/OpenClaw/PodcastKotlinApp/build_output/`

## 注意事项

1. 确保手机已开启"未知来源"安装权限
2. APK 需要 Android 8.0 (API 24) 或更高版本
3. 应用需要 INTERNET 权限来加载播客内容