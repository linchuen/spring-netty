### 專案介紹
本專案目標是實現簡易的聊天室。

### 架構圖
![image](https://github.com/linchuen/spring-netty/blob/master/Structure.jpg)

### 專案包
* rest: 使用tomcat，主要為資料庫處理，並保留事務功能。
* websocket: 使用netty，主要為管理websocket連線，作為傳送訊息用。
* common: 共用類，主要為共用資料庫設定

### 主要package用途
* controller: 提供api接口
* component: 各種元件邏輯實作
* service: 用於元件的解耦，作為表達元件間的交互流程及檢查controller的請求
* config: 各種設定
* repository: 資料操作層，作為資料庫與資料物件分界

### 基本邏輯示意圖
![image](https://github.com/linchuen/spring-netty/blob/master/Logic.jpg)