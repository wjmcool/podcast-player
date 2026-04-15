#!/data/data/com.termux/files/usr/bin/bash

echo "📦 创建简单的 APK 文件..."

# 创建临时目录
TEMP_DIR="/data/data/com.termux/files/home/tmp_apk"
rm -rf "$TEMP_DIR"
mkdir -p "$TEMP_DIR"

# 创建基本的 APK 结构
mkdir -p "$TEMP_DIR/META-INF"
mkdir -p "$TEMP_DIR/res/layout"
mkdir -p "$TEMP_DIR/res/values"

# 创建简单的 AndroidManifest.xml
cat > "$TEMP_DIR/AndroidManifest.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.podcast.app">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:label="播客播放器"
        android:theme="@android:style/Theme.Holo.Light">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
EOF

# 创建简单的资源文件
cat > "$TEMP_DIR/res/values/strings.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">播客播放器</string>
</resources>
EOF

# 创建简单的布局
cat > "$TEMP_DIR/res/layout/activity_main.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="播客播放器"
        android:textSize="24sp" />

</LinearLayout>
EOF

# 创建 classes.dex (空的)
echo "创建 classes.dex..."
echo "dummy" > "$TEMP_DIR/classes.dex"

# 创建 APK 文件
cd "$TEMP_DIR"
zip -r "/sdcard/OpenClaw/PodcastKotlinApp/PodcastApp_simple.apk" .

echo "✅ 简单 APK 已创建！"
echo "📁 位置: /sdcard/OpenClaw/PodcastKotlinApp/PodcastApp_simple.apk"
echo ""
echo "注意：这是一个基本的 APK 框架，需要进一步完善才能正常运行。"