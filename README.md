### 專案介紹
本專案目標是實現簡易的聊天室。

### 架構圖
![image](https://github.com/linchuen/spring-netty/blob/master/Structure.jpg)

### 專案包
* rest: 使用tomcat，主要為資料庫處理，並保留事務功能。
* websocket: 使用netty，主要為管理websocket連線，作為傳送訊息用。
* common: 共用類，主要為共用資料庫設定