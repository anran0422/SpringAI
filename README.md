# AI Hub

## 项目介绍

基于 SpringAI + RAG + Ollama + OpenAI 的 智能平台。

本项目一共包含四个小功能，是在循序渐近的学习过程中开发的：AI 智能聊天、哄哄模拟器、智能客服、ChatPDF。
本项目历经分阶段研发与持续优化，成功构建起四大核心功能模块：基于自然语言处理的智能问答系统、情感类的互动模拟、支持多轮对话的智能客服解决方案，以及具备智能文档解析能力的ChatPDF工具组。

![](https://cloud-1311088844.cos.ap-beijing.myqcloud.com/public_share/project/AIHub/AIHub%E4%B8%BB%E9%A1%B5.jpg)

## 项目预览

**AI 智能聊天**
- 能够根据用户输入进行深度思考
- 支持多模态输入，如文本、语音、文件等
- 记录用户对话记录，便于随时查看

支持普通会话，或者输入文本、文件等多模态，并且这种对话支持上下文

![](https://cloud-1311088844.cos.ap-beijing.myqcloud.com/public_share/project/AIHub/%E5%A4%9A%E6%A8%A1%E6%80%81%E8%81%8A%E5%A4%A9.jpg)

**哄哄模拟器**
- 通过编写安全、不可篡改的 prompt 与玩家进行互动
- 互动可以得到及时反馈，并非简单无聊的机器聊天

大模型大法中的 Prompt 模式，通过设置 System 提示词让模型实现强大的功能。
并且采用的提示词工程，避免了常见问题：提示注入、越狱攻击、数据泄漏攻击、模型欺骗以及拒绝服务攻击等。

![](https://cloud-1311088844.cos.ap-beijing.myqcloud.com/public_share/project/AIHub/%E5%93%84%E5%93%84%E6%A8%A1%E6%8B%9F%E5%99%A8.jpg)

**智能客服**

AI擅长的是非结构化数据的分析，与用户对话并理解用户意图是AI擅长的，数据库操作是Java擅长的，结合两者的能力来实现智能客服功能。
1. 提前把这些操作定义为Function（SpringAI中叫Tool），
2. 然后将Function的名称、作用、需要的参数等信息都封装为Prompt提示词与用户的提问一起发送给大模型
3. 大模型在与用户交互的过程中，根据用户交流的内容判断是否需要调用Function
4. 如果需要则返回Function名称、参数等信息
5. Java解析结果，判断要执行哪个函数，代码执行Function，把结果再次封装到Prompt中发送给AI
6. AI继续与用户交互，直到完成任务

![](https://cloud-1311088844.cos.ap-beijing.myqcloud.com/public_share/project/AIHub/%E6%99%BA%E8%83%BD%E5%AE%A2%E6%9C%8D.jpg)

**ChatPDF**
- 接入 RAG，帮助用户筛选信息
- 用户通过输入，可以快速从导入的 PDF 获取信息

为了解决大模型存在知识限制问题，接入 RAG（外接知识库）。从庞大的知识库中找到与用户问题相关的一小部分，组装成提示词发送给大模型。

![](https://cloud-1311088844.cos.ap-beijing.myqcloud.com/public_share/project/AIHub/chatPDF.jpg)

## 知识点
- 从 0 搭建一个AI Hub
- 掌握大模型应用开发的 4 种模式
- 掌握编写安全、不可篡改的 Prompt 指令
- 通过 SpringAI 框架简化人工工作服务
- 接入 RAG 实现知识简化，快速掌握文档重要信息
- 多模态对话，让机器人聊天不再孤单
