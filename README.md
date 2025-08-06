# Spring Boot Tomcat 容器配置實戰 ⚡

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 專案介紹

這是一個 Spring Boot Web 服務專案，主要展示如何配置和優化 Tomcat 容器，包含壓縮、效能監控、資料庫操作等功能。專案採用咖啡廳點餐系統作為實作範例，讓開發者能夠快速理解 Spring Boot 在生產環境中的最佳實踐。

> 💡 **為什麼選擇此專案？**
> - 完整的 Spring Boot 3.x 實戰範例
> - 展示 Tomcat 容器進階配置技巧
> - 包含效能監控和壓縮優化實作
> - 提供完整的 RESTful API 設計範例

### 🎯 專案特色

- **容器配置優化** - 展示 Tomcat 壓縮、連線池等進階設定
- **效能監控** - 實作自定義攔截器進行 API 效能追蹤
- **資料庫整合** - 使用 JPA 和 H2 資料庫進行資料操作
- **RESTful API** - 完整的咖啡廳點餐系統 API 設計
- **錯誤處理** - 完善的異常處理和錯誤回應機制

## 技術棧

### 核心框架
- **Spring Boot 3.4.5** - 現代化 Java 應用程式框架
- **Spring Data JPA** - 物件關聯對應和資料存取
- **Spring Web MVC** - RESTful API 開發框架
- **Spring Boot Actuator** - 應用程式監控和管理

### 開發工具與輔助
- **Maven** - 專案建置和依賴管理
- **Lombok** - 減少樣板程式碼
- **H2 Database** - 內嵌式資料庫
- **Joda Money** - 貨幣處理工具庫
- **Apache Commons Lang3** - 通用工具類別庫

## 專案結構

```
tomcat-demo/
├── src/
│   ├── main/
│   │   ├── java/tw/fengqing/spring/springbucks/waiter/
│   │   │   ├── controller/           # REST API 控制器
│   │   │   │   ├── CoffeeController.java
│   │   │   │   ├── CoffeeOrderController.java
│   │   │   │   ├── PerformanceInteceptor.java
│   │   │   │   └── request/         # 請求物件
│   │   │   ├── model/               # 資料模型
│   │   │   │   ├── Coffee.java
│   │   │   │   ├── CoffeeOrder.java
│   │   │   │   ├── OrderState.java
│   │   │   │   └── MoneyConverter.java
│   │   │   ├── repository/          # 資料存取層
│   │   │   │   ├── CoffeeRepository.java
│   │   │   │   └── CoffeeOrderRepository.java
│   │   │   ├── service/             # 業務邏輯層
│   │   │   │   ├── CoffeeService.java
│   │   │   │   └── CoffeeOrderService.java
│   │   │   ├── support/             # 支援工具
│   │   │   │   ├── MoneySerializer.java
│   │   │   │   ├── MoneyDeserializer.java
│   │   │   │   └── MoneyFormatter.java
│   │   │   └── WaiterServiceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql           # 資料庫結構
│   │       ├── data.sql             # 初始資料
│   │       └── coffee.txt           # 批次匯入資料
│   └── test/
│       └── java/
│           └── tw/fengqing/spring/springbucks/waiter/
│               └── WaiterServiceApplicationTests.java
├── pom.xml
└── README.md
```

## 快速開始

### 前置需求
- **Java 21** - 建議使用 OpenJDK 或 Oracle JDK
- **Maven 3.8+** - 專案建置工具
- **IDE** - 建議使用 IntelliJ IDEA 或 Eclipse

### 安裝與執行

1. **克隆此倉庫：**
```bash
git clone https://github.com/SpringMicroservicesCourse/tomcat-demo.git
```

2. **進入專案目錄：**
```bash
cd tomcat-demo
```

3. **編譯專案：**
```bash
mvn clean compile
```

4. **執行應用程式：**
```bash
mvn spring-boot:run
```

5. **測試 API 端點：**
```bash
# 查詢所有咖啡
curl http://localhost:8080/coffee/

# 查詢特定咖啡
curl http://localhost:8080/coffee/1

#明確告訴伺服器接受 gzip 壓縮，不包含解壓縮
curl -H "Accept-Encoding: gzip" -v --output - http://localhost:8080/coffee/1

# 測試壓縮功能，自動幫解壓縮 gzip 回應
curl -H "Accept-Encoding: gzip" --compressed http://localhost:8080/coffee/1
```

## 進階說明

### 環境變數
```properties
# 資料庫設定
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver

# JPA 設定
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# 伺服器設定
server.port=8080
server.compression.enabled=true
server.compression.min-response-size=512B
```

### 設定檔說明
```properties
# application.properties 主要設定
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true

# 壓縮設定（可透過程式碼或設定檔配置）
server.compression.enabled=true
server.compression.min-response-size=512B

# 錯誤處理設定
server.error.include-stacktrace=always
server.error.include-exception=true
```

## 核心功能說明

### 🚀 Tomcat 容器配置

專案展示了兩種配置 Tomcat 容器的方式：

#### 1. 透過設定檔配置
```properties
# 啟用壓縮功能
server.compression.enabled=true
server.compression.min-response-size=512B
```

#### 2. 透過程式碼配置
```java
@Override
public void customize(TomcatServletWebServerFactory factory) {
    Compression compression = new Compression();
    compression.setEnabled(true);
    compression.setMinResponseSize(DataSize.ofBytes(512));
    factory.setCompression(compression);
}
```

### 📊 效能監控

實作了自定義攔截器來監控 API 效能：

```java
@Slf4j
public class PerformanceInteceptor implements HandlerInterceptor {
    // 記錄請求處理時間和狀態
    @Override
    public void afterCompletion(HttpServletRequest request, 
                              HttpServletResponse response, 
                              Object handler, 
                              Exception ex) throws Exception {
        // 記錄效能指標
        log.info("{};{};{};{};{}ms", 
                request.getRequestURI(), 
                method, 
                response.getStatus(), 
                ex == null ? "-" : ex.getClass().getSimpleName(),
                sw.getTotalTimeMillis());
    }
}
```

### 💰 貨幣處理

使用 Joda Money 處理貨幣相關操作：

```java
@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {
    /**
     * 將 Money 物件轉換為資料庫中的長整數值
     * 將金額轉換為最小貨幣單位（分）
     */
    @Override
    public Long convertToDatabaseColumn(Money attribute) {
        return attribute == null ? null : attribute.getAmountMinorLong();
    }
}
```

## 參考資源

- [Spring Boot 官方文件](https://spring.io/projects/spring-boot)
- [Spring Boot Web 配置指南](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html)
- [Tomcat 容器配置說明](https://tomcat.apache.org/tomcat-10.0-doc/config/)
- [Joda Money 使用指南](https://www.joda.org/joda-money/)

## 注意事項與最佳實踐

### ⚠️ 重要提醒

| 項目 | 說明 | 建議做法 |
|------|------|----------|
| 安全性 | 生產環境錯誤資訊 | 關閉 `server.error.include-stacktrace` |
| 效能 | 壓縮設定 | 根據回應大小調整 `min-response-size` |
| 監控 | API 效能追蹤 | 使用自定義攔截器記錄關鍵指標 |

### 🔒 最佳實踐指南

- **容器配置** - 根據實際需求調整 Tomcat 連線池和壓縮設定
- **錯誤處理** - 生產環境應提供友善的錯誤頁面，避免暴露內部細節
- **效能監控** - 實作自定義攔截器追蹤 API 回應時間和錯誤率
- **資料庫操作** - 使用 JPA 和適當的資料庫連線池設定
- **API 設計** - 遵循 RESTful 設計原則，提供清晰的端點結構

### 🛠️ 開發環境建議

```properties
# 開發環境設定
spring.jpa.show-sql=true
server.error.include-stacktrace=always
logging.level.tw.fengqing.spring.springbucks=DEBUG
```

```properties
# 生產環境設定
spring.jpa.show-sql=false
server.error.include-stacktrace=never
logging.level.tw.fengqing.spring.springbucks=INFO
```

## 授權說明

本專案採用 MIT 授權條款，詳見 LICENSE 檔案。

## 關於我們

我們主要專注在敏捷專案管理、物聯網（IoT）應用開發和領域驅動設計（DDD）。喜歡把先進技術和實務經驗結合，打造好用又靈活的軟體解決方案。

## 聯繫我們

- **FB 粉絲頁**：[風清雲談 | Facebook](https://www.facebook.com/profile.php?id=61576838896062)
- **LinkedIn**：[linkedin.com/in/chu-kuo-lung](https://www.linkedin.com/in/chu-kuo-lung)
- **YouTube 頻道**：[雲談風清 - YouTube](https://www.youtube.com/channel/UCXDqLTdCMiCJ1j8xGRfwEig)
- **風清雲談 部落格**：[風清雲談](https://blog.fengqing.tw/)
- **電子郵件**：[fengqing.tw@gmail.com](mailto:fengqing.tw@gmail.com)

---

**📅 最後更新：2025-08-06**  
**👨‍💻 維護者：風清雲談團隊** 