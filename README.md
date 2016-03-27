# word-extractor
墨墨提词算法

## 简介
提词即是从一段文本里提取出墨墨词库里面有的单词，用户可以将这些单词添加到自己的学习规划。

## 提交
将代码或项目直接放入 src 文件夹内即可。

## 关键功能
1. 用户提供一个文本，文本与某个词库（墨墨词库）做对比，提取出来文本和词库都有的单词；
2. 按用户提供的文本的单词出现前后顺序排列提取出来的单词
3. 重复的单词不重复提取
4. 可以提取短语，例如：
    1. `He knows a bit of Dutch` => `a bit of`.
    2. `as noisy as evey` => `as ... as`.
    3. `keep up with jenny to` => `keep up with sb.`，[更多代词](#会出现的代词)
4. 可以提取短语，例如词库里有个 'a bit of'， 要在句子 ‘He knows a bit of Dutch.' 提取出来; 又例如 'as ... as', 要在句子 'as noisy as evey' 中提取出来
5. 特殊符号的处理，如 clean-up 需要作为一个单词，也要拆分成独立单词，即 clean-up, clean, up
6. （选项功能）一般时态变形的单词的处理，如 look，如果文中的是 looked，需要优先从词库里查询是否有 look 这个单词，如果词库有 look 则不再继续查找，如果没有再查询 looked。ing 形态和加 s,es,ies 形态也同理。
7. （选项功能）不规则时态的处理，如 drunk，需要优先查找 drunk，如果词库有 drunk 则不再继续查找，如果没有再查询 drink。
8. 在保证正确性的前提下尽量提高提取速度，比如避免 auto boxing/unboxing

## 会出现的代词
`["do sth.", "do sth","sb.'s", "sth.", "sb.","sth", "sb", "one's", "somebody's", "somebody", "something"]`
