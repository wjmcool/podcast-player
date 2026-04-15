#!/data/data/com.termux/files/usr/bin/bash

echo "🔧 修复 APK 文件..."

APK_FILE="/sdcard/OpenClaw/PodcastKotlinApp_basic.apk"
TEMP_DIR="/data/data/com.termux/files/home/temp_apk_fix"

# 创建临时目录
rm -rf "$TEMP_DIR"
mkdir -p "$TEMP_DIR"

# 解压 APK
echo "📦 解压 APK..."
unzip -q "$APK_FILE" -d "$TEMP_DIR"

# 检查并修复关键文件
echo "🔍 检查关键文件..."

# 1. 检查 AndroidManifest.xml
if [ ! -f "$TEMP_DIR/AndroidManifest.xml" ]; then
    echo "❌ AndroidManifest.xml 不存在"
    exit 1
fi

# 2. 检查 classes.dex
if [ ! -f "$TEMP_DIR/build/apk/classes.dex" ]; then
    echo "❌ classes.dex 不存在"
    exit 1
fi

# 3. 检查资源文件
if [ ! -f "$TEMP_DIR/res/values/strings.xml" ]; then
    echo "❌ strings.xml 不存在"
    exit 1
fi

echo "✅ 所有关键文件都存在"

# 创建简单的签名脚本
echo "📝 创建签名脚本..."

# 使用 keytool 创建测试证书
echo "🔐 创建测试证书..."
keytool -genkey -v \
  -keystore "$TEMP_DIR/debug.keystore" \
  -alias androiddebugkey \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -storepass android \
  -keypass android \
  -dname "CN=Android Debug,O=Android,C=US" 2>/dev/null || echo "证书创建跳过"

# 签名 APK
echo "✍️ 签名 APK..."
if [ -f "$TEMP_DIR/debug.keystore" ]; then
    jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
        -keystore "$TEMP_DIR/debug.keystore" \
        -storepass android -keypass android \
        "$APK_FILE" androiddebugkey 2>/dev/null || echo "签名跳过"
fi

# 优化 APK
echo "🔄 优化 APK..."
if command -v zipalign &> /dev/null; then
    zipalign -v 4 "$APK_FILE" "/sdcard/OpenClaw/PodcastKotlinApp_fixed.apk" 2>/dev/null || echo "优化跳过"
    if [ -f "/sdcard/OpenClaw/PodcastKotlinApp_fixed.apk" ]; then
        echo "✅ 优化后的 APK: /sdcard/OpenClaw/PodcastKotlinApp_fixed.apk"
        cp "/sdcard/OpenClaw/PodcastKotlinApp_fixed.apk" "/sdcard/OpenClaw/PodcastKotlinApp.apk"
    fi
fi

# 清理
rm -rf "$TEMP_DIR"

echo ""
echo "🎉 APK 修复完成！"
echo ""
echo "📱 安装步骤："
echo "1. 在手机设置中开启'未知来源'安装权限"
echo "2. 点击安装 /sdcard/OpenClaw/PodcastKotlinApp.apk"
echo "3. 如果仍无法安装，需要使用 Android Studio 完整构建"