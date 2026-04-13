# 📱 GitHub Desktop 上传指南

## 步骤1：下载并安装GitHub Desktop

### 在Android手机上：

1. **打开Google Play商店**
2. **搜索** "GitHub Desktop"
3. **点击安装**
4. **等待安装完成**

### 在PC/平板上：

**下载地址：** https://desktop.github.com/

---

## 步骤2：登录GitHub账号

1. **打开GitHub Desktop**
2. **File → Options → Accounts**
3. **点击Sign in**
4. **选择Sign in with GitHub**
5. **输入用户名：wjmcool**
6. **使用浏览器完成登录授权**

---

## 步骤3：克隆仓库

1. **File → Clone Repository**
2. **Repository URL：**
   ```
   https://github.com/wjmcool/podcast-player.git
   ```
3. **保存位置：** 选择 `/sdcard/OpenClaw/`
4. **Repository name：** podcast-player
5. **点击 Clone**

---

## 步骤4：检查项目文件

GitHub Desktop会自动克隆所有文件到：
```
/sdcard/OpenClaw/podcast-player/
```

确保以下文件都已显示：
- ✅ MainActivity.kt
- ✅ app/ 目录（所有源代码）
- ✅ build.gradle
- ✅ settings.gradle
- ✅ gradle/ 目录
- ✅ .github/ 目录

---

## 步骤5：提交更改

1. **点击 "Push origin" 按钮旁边的 "Publish repository"**
2. **Repository name：** podcast-player
3. **描述（可选）：** Kotlin Podcast Player Android App
4. **勾选所有选项**
5. **点击 Publish**

或者：
1. **点击所有文件（全部选中）**
2. **Commit to main**
3. **Commit message：**
   ```
   Initial commit - Podcast Player

   完整项目包含：
   - Kotlin播客播放器Android应用
   - 实现播放/暂停、音量调节、进度控制
   - Material Design UI设计
   - GitHub Actions自动构建配置
   - 支持5个预设播客节目

   技术栈：
   - Kotlin
   - AndroidX
   - Material Components
   - Media3 (ExoPlayer)
   - ViewBinding
   ```
4. **点击 "Commit"**

---

## 步骤6：推送到GitHub

1. **点击 "Push origin" 按钮**
2. **等待推送完成**
3. **显示成功消息**

---

## 步骤7：查看构建

1. **访问：** https://github.com/wjmcool/podcast-player/actions
2. **等待5-10分钟构建完成**
3. **在Artifacts部分下载APK**
4. **APK文件名：**
   - podcast-player-debug.apk（调试版）
   - podcast-player-release.apk（发布版）

---

## ✅ 完成！

**如果遇到问题：**
- 确保已登录GitHub账号
- 检查网络连接
- 确保存储空间充足

**需要帮助？**
- GitHub Desktop文档：https://docs.github.com/en/desktop
- GitHub帮助：https://docs.github.com/en/help