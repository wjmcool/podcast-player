# Podcast Player

一个现代化的 Android 播客播放器应用，支持多首播客管理和本地播放控制。

## 功能特性

- 🎙️ 多首播客支持
- 🎵 在线音频流播放
- 🎚️ 播放控制（播放/暂停、快进/后退）
- 🎨 Material Design 界面设计
- 🚀 Kotlin 编写，使用 Jetpack 组件

## 技术栈

- **语言**: Kotlin
- **架构**: MVVM
- **UI**: Material Components
- **媒体播放**: ExoPlayer / Media3
- **界面绑定**: ViewBinding

## 项目结构

```
PodcastPlayer/
├── app/
│   ├── src/main/
│   │   ├── java/
│   │   │   └── com/openclaw/podcast/
│   │   │       ├── MainActivity.kt
│   │   │       ├── PodcastActivity.kt
│   │   │       ├── MediaService.kt
│   │   │       ├── adapter/
│   │   │       │   └── PodcastAdapter.kt
│   │   │       └── model/
│   │   │           └── Podcast.kt
│   │   └── res/
│   ├── build.gradle.kts
├── .github/
│   └── workflows/
│       └── build.yml
└── build.gradle.kts
```

## 构建与运行

### 前置要求

- Android Studio Arctic Fox 或更高版本
- JDK 17
- Android SDK 34

### 构建步骤

1. 克隆仓库
2. 打开项目到 Android Studio
3. Sync Gradle 文件
4. 运行 `./gradlew assembleDebug` 构建 APK
5. 在 Android 设备上安装运行

## 示例播客列表

- 科技前沿 - 探索最新科技趋势
- 历史故事 - 历史上的有趣故事
- 健康养生 - 身心健康指南
- 商业思维 - 商业洞察分析
- 艺术欣赏 - 音乐绘画鉴赏

## 🔧 快速构建

### 构建 APK(本地)

```bash
./gradlew assembleDebug
```

### 构建 APK(GitHub Actions)

1. 访问仓库的 Actions 页面
2. 选择 "Build APK" 工作流
3. 点击 "Run workflow"
4. 下载生成的 APK

## 许可证

MIT License

## 作者

OpenClaw Team
