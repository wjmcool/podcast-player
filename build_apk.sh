#!/data/data/com.termux/files/usr/bin/bash

# 构建 Kotlin 播客 APK
echo "📦 开始构建 Kotlin 播客 APK..."

# 创建项目目录结构
PROJECT_DIR="/sdcard/OpenClaw/PodcastKotlinApp"
BUILD_DIR="$PROJECT_DIR/build"
APK_NAME="PodcastKotlinApp.apk"

# 清理旧的构建
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

# 复制源文件
echo "📄 复制源文件..."
cp -r /data/data/com.termux/files/home/.openclaw/workspace/PodcastKotlinApp/* "$BUILD_DIR/"

# 创建简单的构建说明
cat > "$BUILD_DIR/BUILD_INSTRUCTIONS.md" << 'EOF'
# Kotlin 播客 APK 构建说明

## 构建方法

### 方法1: 使用 Termux + Android SDK

1. 安装必要工具:
```bash
pkg install wget unzip openjdk-17
```

2. 下载 Android Command Line Tools:
```bash
cd /data/data/com.termux/files/home
wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
unzip commandlinetools-linux-8512546_latest.zip
```

3. 设置环境变量:
```bash
export ANDROID_HOME=/data/data/com.termux/files/home/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/bin:$ANDROID_HOME/platform-tools
```

4. 创建目录并移动工具:
```bash
mkdir -p $ANDROID_HOME/cmdline-tools
mv cmdline-tools $ANDROID_HOME/cmdline-tools/latest
```

5. 安装 SDK:
```bash
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

6. 构建 APK:
```bash
cd $BUILD_DIR
./gradlew assembleDebug
```

### 方法2: 使用 Android Studio

1. 将整个项目文件夹复制到电脑
2. 在 Android Studio 中打开项目
3. 点击 "Build" -> "Build Bundle(s) / APK(s)" -> "Build APK(s)"

## 项目文件结构

```
PodcastKotlinApp/
├── build.gradle.kts          # Gradle 构建配置
├── AndroidManifest.xml       # 应用清单
├── src/main/
│   ├── java/com/podcast/kotlin/app/
│   │   └── MainActivity.kt   # 主活动代码
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml    # 主界面布局
│       │   └── podcast_item.xml     # 播客项布局
│       ├── values/
│       │   ├── strings.xml          # 字符串资源
│       │   ├── colors.xml           # 颜色资源
│       │   └── themes.xml           # 主题资源
│       └── xml/
│           ├── backup_rules.xml
│           └── data_extraction_rules.xml
└── build_apk.sh             # 构建脚本
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
- 构建目录: `/sdcard/OpenClaw/PodcastKotlinApp/build/`
EOF

echo "✅ Kotlin 播客 APK 项目已准备好！"
echo "📁 项目目录: $PROJECT_DIR"
echo "📄 构建说明: $BUILD_DIR/BUILD_INSTRUCTIONS.md"

# 复制到常用目录
echo ""
echo "🔄 复制项目到常用目录..."
cp -r "$BUILD_DIR"/* "$PROJECT_DIR/"

echo "✅ 完成！"
echo "📁 项目位置: $PROJECT_DIR"