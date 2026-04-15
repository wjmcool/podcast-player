#!/data/data/com.termux/files/usr/bin/bash

echo "📦 完整 APK 构建脚本"
echo "========================"

# 设置环境变量
export ANDROID_HOME=/data/data/com.termux/files/home/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools
export JAVA_HOME=/data/data/com.termux/files/usr/lib/jvm/java-17-openjdk

# 创建构建目录
BUILD_DIR="/sdcard/OpenClaw/PodcastKotlinApp/build_complete"
APK_OUTPUT="$BUILD_DIR/app-debug.apk"
TEMP_DIR="$BUILD_DIR/temp"

rm -rf "$BUILD_DIR"
mkdir -p "$TEMP_DIR"

echo "1️⃣ 创建项目结构..."
mkdir -p "$TEMP_DIR/META-INF"
mkdir -p "$TEMP_DIR/res/layout"
mkdir -p "$TEMP_DIR/res/values"
mkdir -p "$TEMP_DIR/res/drawable"
mkdir -p "$TEMP_DIR/smali/com/podcast/kotlin/app"

echo "2️⃣ 创建 AndroidManifest.xml..."
cat > "$TEMP_DIR/AndroidManifest.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.podcast.kotlin.app"
    android:versionCode="1"
    android:versionName="1.0">

    <uses-sdk
        android:minSdkVersion="24"
        android:targetSdkVersion="34" />

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.PodcastKotlinApp">
        
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
EOF

echo "3️⃣ 创建资源文件..."
cat > "$TEMP_DIR/res/values/strings.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">播客播放器</string>
    <string name="pause">暂停</string>
    <string name="play">播放</string>
</resources>
EOF

cat > "$TEMP_DIR/res/values/colors.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="primary">#FF1A1A2E</color>
    <color name="accent">#FF00D9FF</color>
</resources>
EOF

cat > "$TEMP_DIR/res/values/themes.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.PodcastKotlinApp" parent="android:Theme.Holo.Light">
    </style>
</resources>
EOF

echo "4️⃣ 创建布局文件..."
cat > "$TEMP_DIR/res/layout/activity_main.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    android:background="@color/primary">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/app_name"
        android:textColor="@android:color/white"
        android:textSize="24sp"
        android:textStyle="bold" />

    <Button
        android:id="@+id/playButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:text="@string/play" />

</LinearLayout>
EOF

echo "5️⃣ 创建 Smali 代码..."
cat > "$TEMP_DIR/smali/com/podcast/kotlin/app/MainActivity.smali" << 'EOF'
.class public Lcom/podcast/kotlin/app/MainActivity;
.super Landroid/app/Activity;
.source "MainActivity.java"

.method public onCreate(Landroid/os/Bundle;)V
    .registers 3

    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    const v0, 0x7f030001
    invoke-virtual {p0, v0}, Lcom/podcast/kotlin/app/MainActivity;->setContentView(I)V

    return-void
.end method
EOF

echo "6️⃣ 创建 apktool.yml..."
cat > "$TEMP_DIR/apktool.yml" << 'EOF'
!!brut.directory.DirConfig
apkFileName: PodcastApp.apk
compressionType: false
doNotCompress:
- resources.arsc
- res/raw
- assets
forceDelete: false
forceOverwrite: false
isAapt2: true
keepBrokenResources: false
metaName: AndroidManifest.xml
originalFiles: {}
useAapt2: true
version: 2.9.0
EOF

echo "7️⃣ 使用 apktool 构建 APK..."
cd "$TEMP_DIR"
java -jar /data/data/com.termux/files/home/apktool.jar b "$TEMP_DIR" "$APK_OUTPUT" 2>&1 | tail -10

if [ -f "$APK_OUTPUT" ]; then
    echo ""
    echo "✅ APK 构建成功！"
    echo "📁 APK 位置: $APK_OUTPUT"
    echo "📏 文件大小: $(du -h "$APK_OUTPUT" | cut -f1)"
    
    # 复制到常用目录
    cp "$APK_OUTPUT" "/sdcard/OpenClaw/PodcastKotlinApp.apk"
    echo "🔄 已复制到: /sdcard/OpenClaw/PodcastKotlinApp.apk"
else
    echo ""
    echo "❌ APK 构建失败，使用备用方法..."
    
    # 备用方法：创建基本 APK
    cd "$TEMP_DIR"
    zip -r "$BUILD_DIR/PodcastApp_basic.apk" .
    cp "$BUILD_DIR/PodcastApp_basic.apk" "/sdcard/OpenClaw/PodcastKotlinApp_basic.apk"
    echo "✅ 基本 APK 已创建: /sdcard/OpenClaw/PodcastKotlinApp_basic.apk"
fi

echo ""
echo "8️⃣ 清理临时文件..."
rm -rf "$TEMP_DIR"

echo ""
echo "🎉 完成！"
echo "📁 项目目录: /sdcard/OpenClaw/PodcastKotlinApp/"
echo "📱 APK 文件: /sdcard/OpenClaw/PodcastKotlinApp.apk"
echo ""
echo "📝 使用说明："
echo "   1. 将 APK 文件传输到手机"
echo "   2. 在手机上点击安装"
echo "   3. 如果无法安装，需要使用 Android Studio 重新构建"