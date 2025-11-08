# Online-Shopping E-Ticaret Uygulaması

Bu proje, **Spring Boot** ve **React** kullanılarak geliştirilmiş bir **Java tabanlı e-ticaret web uygulamasıdır**.  
Uygulama, temel e-ticaret işlemlerini (ürün, kategori, müşteri ve sipariş yönetimi) içerir ve veriler **PostgreSQL** veritabanında tutulur.


---

## Teknolojiler ve Araçlar

- Java 17+
- Spring Boot 3.x
  - Spring Web
  - Spring Data JPA
  - Spring Boot DevTools
- PostgreSQL
- Lombok
- React
- Postman (API testleri için)

---

##  Öğrenim Amacı

Bu proje, **Spring Boot ile REST API geliştirme**, **katmanlı mimari kullanımı** ve **frontend-backend entegrasyonu** konularını öğrenmek isteyenler için hazırlanmıştır.  
Proje aynı zamanda veri tabanı ilişkileri ve CRUD işlemlerinin pratik bir şekilde uygulanmasını gösterir.
-Spring Boot ile REST API geliştirme
-Katmanlı mimariyi uygulama (Controller, Service, Repository)
-Spring Data JPA ile veri tabanı işlemlerini yönetme
-Frontend ve backend arasında veri iletişimi

---

##  Genel Mimari Açıklama

### Model (Entity)

- `Products`, `Categories`, `Customers`, `Orders`, `OrderItems`, `Basket`, `BasketItems` ve `Cities` sınıfları, veritabanındaki ilgili tabloları temsil eder.
- @Entity, @Table, @OneToMany, @ManyToOne ve @JsonIgnoreProperties anotasyonları ile veritabanı ve JSON serileştirme işlemleri yönetilir.
- Bir ürün bir kategoriye aittir (Many-to-One) ,bir ürün birden fazla sipariş öğesi ile ilişkili olabilir (One-to-Many)

### Repository

- Spring Data JPA kullanılarak CRUD işlemleri ve kategoriye göre ürün getirme gibi özel sorgular otomatik olarak sağlanır.

### Service

- İş mantığı bu katmanda yer alır ve Controller ile Repository arasında köprü görevi görür.
- Entity’den DTO’ya dönüşüm işlemleri burada gerçekleştirilir.

### Controller

- HTTP istekleri bu katman tarafından karşılanır.
- Endpoint’ler aracılığıyla ürünleri listeleme, kategoriye göre filtreleme ve ürün detaylarını çekme gibi işlemler yapılır.

### CORS Yapılandırması

- React frontend’in backend API’lerine erişimi için gerekli CORS ayarları Spring Boot tarafında tanımlanmıştır.

---

##  Öne Çıkan Özellikler

Bu e-ticaret platformunda:

- Ürün, kategori, müşteri, sipariş ve sepet yönetimi yapılır.
- Sepete ürün ekleme, çıkarma ve sipariş tamamlama akışları desteklenir.
- Backend tarafında Spring Boot ile REST API geliştirilmiştir.
- Frontend tarafında React ile kullanıcı arayüzü oluşturulmuştur.
- PostgreSQL veritabanında ilişkisel yapı kullanılmıştır.



