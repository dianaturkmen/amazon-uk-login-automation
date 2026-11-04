# Amazon.co.uk – Ana Sayfa Giriş (Sign-In) User Story'leri

**Epic:** AMZ-AUTH – Ana sayfadan hesaba giriş

---

## US-01 – Ana sayfanın yüklenmesi
**As a** ziyaretçi
**I want** amazon.co.uk ana sayfasını açtığımda sayfanın düzgün yüklenmesini
**So that** alışverişe ve hesabıma giriş yapmaya başlayabileyim.

**Acceptance Criteria**
1. Sayfa başlığı "Amazon.co.uk" içerir.
2. Amazon logosu ve arama kutusu görünür.
3. Üst navigasyonda "Hello, sign in / Account & Lists" alanı görünür.

## US-02 – Çerez (cookie) onayı
**As a** UK ziyaretçisi
**I want** çerez tercihimi kabul veya reddedebilmeyi
**So that** GDPR/PECR kapsamındaki hakkımı kullanabileyim ve sayfayla etkileşebileyim.

**Acceptance Criteria**
1. İlk ziyarette çerez banner'ı görünür ("Accept" ve "Decline" seçenekleri).
2. Seçim yapıldığında banner kapanır ve tekrar görünmez.

## US-03 – Ana sayfadan giriş sayfasına ulaşma
**As a** kayıtlı müşteri
**I want** ana sayfadaki "Hello, sign in" bağlantısıyla giriş sayfasına gidebilmeyi
**So that** hesabıma hızlıca giriş yapabileyim.

**Acceptance Criteria**
1. "Account & Lists" üzerine gelindiğinde (hover) açılan menüde "Sign in" butonu ve "New customer? Start here." bağlantısı bulunur.
2. "Hello, sign in"e tıklandığında giriş sayfası açılır.
3. Giriş sayfasında e-posta/telefon alanı ve "Continue" butonu bulunur.

## US-04 – Giriş formunun doğrulanması (negatif senaryolar)
**As a** müşteri
**I want** e-posta alanını boş veya hatalı girdiğimde anlaşılır bir hata mesajı görmeyi
**So that** hatamı düzeltip tekrar deneyebileyim.

**Acceptance Criteria**
1. Boş e-posta ile "Continue" → "Enter your email address or mobile phone number" benzeri hata.
2. Geçersiz formatta / kayıtlı olmayan e-posta → hata mesajı veya "hesap oluştur" yönlendirmesi; kullanıcı şifre adımına geçemez.

## US-05 – Geçerli bilgilerle giriş
**As a** kayıtlı müşteri
**I want** geçerli e-posta ve şifremle giriş yapabilmeyi
**So that** siparişlerime, listelerime ve hesap ayarlarıma erişebileyim.

**Acceptance Criteria**
1. Geçerli e-posta → şifre adımı gelir.
2. Geçerli şifre → ana sayfaya dönülür, navigasyonda "Hello, &lt;Ad&gt;" görünür.
3. Yanlış şifre → "Your password is incorrect" hatası, kullanıcı giriş yapmamış olarak kalır.

> Not: US-05 gerçek bir test hesabı gerektirir ve Amazon'un bot korumasına (CAPTCHA/OTP) takılabilir. Otomasyonda `@credentials` etiketiyle ayrılmıştır ve varsayılan koşuda çalışmaz.
