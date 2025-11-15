# AGENTS.md

## Setup commands
- 项目构建：使用 Gradle，执行 `./gradlew build` 进行构建；执行 `./gradlew test` 运行测试。
- 开发运行：使用 Android Studio 或 `./gradlew assembleDebug` 生成调试APK 后，通过 `adb install` 部署到模拟器。

## Code style guidelines
- **语言与风格：**使用 Kotlin 编写代码，遵循 Kotlin 官方编码规范（例如命名采用驼峰式，类名用帕斯卡式，省略不必要的分号等）。
- **UI 开发：**新功能界面一律使用 xml 构建UI组件
- **异步编程：**优先使用 **Kotlin 协程**（Coroutines）来处理异步任务，而非旧式的AsyncTask或RxJava。协程是Android上进行异步编程的推荐解决方案，可避免阻塞主线程:contentReference[oaicite:38]{index=38}。

## Android best practices
- **架构模式：**采用 MVVM 架构，通过 ViewModel 管理UI状态与业务逻辑。ViewModel 与 xml 的 view
- **生命周期管理：**避免在 Activity 或 Fragment 的初始化中执行长时间任务；如需执行，使用生命周期感知的协程（例如使用 `lifecycleScope` 或 `viewModelScope` 启动协程）。确保在组件销毁时取消相应协程，防止内存泄漏。
- **UI 组件：** 目前是 Android view 体系的 xml 实现，而不要用 compose。
- **异步与线程：**所有耗时操作（如网络请求、数据库访问）必须放在后台线程或协程中执行，避免阻塞主线程造成ANR。使用 `suspend` 函数配合 `withContext(Dispatchers.IO)` 切换线程，或利用 `LiveData`/`Flow` 将异步结果通知UI。

## Learning mode instructions
- **解释和注释：**这个项目用于学习示例，因此请在代码中添加适当的注释，并在给出的回答中包含步骤讲解或原理说明，帮助理解Kotlin和Android相关概念。
- **循序渐进：**在指导实现某个功能时，尽量按步骤完成，每一步都提供可工作的代码片段和说明，确保用户可以逐步编译运行并观察结果。
- **参考资料：**如果遇到复杂概念，可简要解释其原理，并提示用户查阅Android官方文档获取更多信息。
