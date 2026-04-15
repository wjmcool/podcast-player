#!/data/data/com.termux/files/usr/bin/bash

# 设置环境变量
export ANDROID_HOME=/data/data/com.termux/files/home/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# 创建简单的构建目录
BUILD_DIR="/sdcard/OpenClaw/PodcastKotlinApp/build_output"
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

# 复制源代码
cp -r /data/data/com.termux/files/home/.openclaw/workspace/PodcastKotlinApp/src "$BUILD_DIR/"

echo "✅ APK 项目结构已准备好！"
echo "📁 位置: $BUILD_DIR"

# 创建使用说明
cat > "$BUILD_DIR/README.md" << 'ENDOFREADME'
# Kotlin 播客 APK 项目

## 构建说明

由于在 Termux 中完整构建 APK 需要 Android Gradle Plugin，这里提供了项目源代码。

### 在电脑上构建

1. 将整个项目文件夹复制到电脑
2. 在 Android Studio 中打开项目
3. 点击 "Build" -> "Build Bundle(s) / APK(s)" -> "Build APK(s)"

### 项目结构

```
src/
├── main/
│   ├── java/com/podcast/kotlin/app/
│   │   └── MainActivity.kt    # 主活动代码
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml     # 主界面布局
│       │   └── podcast_item.xml      # 播客项布局
│       └── values/
│           ├── strings.xml           # 字符串资源
│           ├── colors.xml            # 颜色资源
│           └── themes.xml            # 主题资源
```

## 应用功能

- 播客列表展示
- 播放/暂停控制
- 上一个/下一个切换
- 快进/快退功能
- 进度条控制
- 音量调节
- 键盘快捷键支持

## 文件位置

- 项目目录: `/sdcard/OpenClaw/PodcastKotlinApp/`
- 源代码: `/sdcard/OpenClaw/PodcastKotlinApp/build_output/src/`
ENDOFREADME

echo "✅ 完成！"
echo "📁 项目位置: $BUILD_DIR"