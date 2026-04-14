package com.xiaobao.podcast.data.repository

import com.xiaobao.podcast.data.model.Episode

object SampleEpisodes {
    val episodes = listOf(
        Episode(
            id = "1",
            title = "AI 时代的编程学习之道",
            author = "小宝科技台",
            description = "在 AI 工具爆发的今天，程序员如何保持核心竞争力？我们来聊聊 Copilot、Claude 和自学的未来。",
            coverUrl = "https://picsum.photos/seed/pod1/300",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            duration = 18 * 60 * 1000L + 32 * 1000L,
            publishedAt = "2026-01-15"
        ),
        Episode(
            id = "2",
            title = "手机自动化：省时 100 倍的工作流",
            author = "小宝效率派",
            description = "用 Shizuku + Tasker 打造全自动手机助手，让重复操作说拜拜。包含 5 个真实案例演示。",
            coverUrl = "https://picsum.photos/seed/pod2/300",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            duration = 24 * 60 * 1000L + 15 * 1000L,
            publishedAt = "2026-01-22"
        ),
        Episode(
            id = "3",
            title = "从零搭一个私人博客",
            author = "小宝开发笔记",
            description = "Hexo + Vercel + 自定义域名，全程实战演示，30 分钟上线。附赠 SEO 小技巧。",
            coverUrl = "https://picsum.photos/seed/pod3/300",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            duration = 31 * 60 * 1000L + 7 * 1000L,
            publishedAt = "2026-02-01"
        ),
        Episode(
            id = "4",
            title = "播客经济的商业逻辑",
            author = "小宝商业观察",
            description = "喜马拉雅、Spotify 如何赚钱？主播们真实收入揭秘，普通人入局还来得及吗？",
            coverUrl = "https://picsum.photos/seed/pod4/300",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            duration = 22 * 60 * 1000L + 48 * 1000L,
            publishedAt = "2026-02-10"
        ),
        Episode(
            id = "5",
            title = "Android 刷机避坑指南",
            author = "小宝搞机频道",
            description = "BL锁、解Bootloader、刷ROM、Magisk ROOT——完整流程与常见报错处理。",
            coverUrl = "https://picsum.photos/seed/pod5/300",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
            duration = 29 * 60 * 1000L + 55 * 1000L,
            publishedAt = "2026-02-18"
        ),
        Episode(
            id = "6",
            title = "LLM 微调真的有用吗？",
            author = "小宝AI实验室",
            description = "LoRA、QLoRA、Full-tune 有什么差异？实测数据说话，普通人该选哪条路。",
            coverUrl = "https://picsum.photos/seed/pod6/300",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
            duration = 26 * 60 * 1000L + 20 * 1000L,
            publishedAt = "2026-03-01"
        )
    )
}
