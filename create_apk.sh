#!/data/data/com.termux/files/usr/bin/bash

# 创建简单的 APK 文件结构
echo "📦 创建 APK 文件结构..."

APK_DIR="/sdcard/OpenClaw/PodcastKotlinApp/SimpleAPK"
rm -rf "$APK_DIR"
mkdir -p "$APK_DIR/META-INF"
mkdir -p "$APK_DIR/res/layout"
mkdir -p "$APK_DIR/res/values"
mkdir -p "$APK_DIR/res/drawable"
mkdir -p "$APK_DIR/smali/com/podcast/kotlin/app"

# 创建简单的 AndroidManifest.xml
cat > "$APK_DIR/AndroidManifest.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.podcast.kotlin.app">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
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

# 创建简单的 strings.xml
cat > "$APK_DIR/res/values/strings.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">播客播放器</string>
    <string name="pause">暂停</string>
    <string name="play">播放</string>
</resources>
EOF

# 创建简单的主布局
cat > "$APK_DIR/res/layout/activity_main.xml" << 'EOF'
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
        android:textSize="24sp"
        android:textStyle="bold" />

    <Button
        android:id="@+id/playButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:text="播放" />

</LinearLayout>
EOF

# 创建简单的 Java 源代码
cat > "$APK_DIR/smali/com/podcast/kotlin/app/MainActivity.smali" << 'EOF'
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

# 创建证书
openssl genrsa -out "$APK_DIR/META-INF/keystore.pem" 2048 2>/dev/null

echo "✅ APK 文件结构已创建！"
echo "📁 位置: $APK_DIR"
echo ""
echo "📱 要完整构建 APK，需要使用以下工具："
echo "   1. apktool - 解包/打包 APK"
echo "   2. jarsigner - 签名 APK"
echo "   3. zipalign - 优化 APK"
echo ""
echo "📦 安装这些工具："
echo "   pkg install apktool zipalign"
echo "   然后使用 apktool b $APK_DIR app.apk"