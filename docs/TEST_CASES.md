# Amazon.co.uk – Ana Sayfa Giriş Test Case'leri

| ID | User Story | Başlık | Ön Koşul | Adımlar | Beklenen Sonuç | Öncelik | Tip | Otomasyon |
|----|-----------|--------|----------|---------|----------------|---------|-----|-----------|
| TC-01 | US-01 | Ana sayfa başarıyla yüklenir | Tarayıcı açık | 1. https://www.amazon.co.uk aç | Başlık "Amazon.co.uk" içerir; logo ve arama kutusu görünür | P1 | Smoke | ✅ `@TC01` |
| TC-02 | US-02 | Çerezler kabul edilir | İlk ziyaret (temiz oturum) | 1. Ana sayfayı aç 2. "Accept" tıkla | Banner kapanır | P2 | Functional | ✅ `@TC02` |
| TC-03 | US-02 | Çerezler reddedilir | İlk ziyaret | 1. Ana sayfayı aç 2. "Decline" tıkla | Banner kapanır | P2 | Functional | ✅ `@TC03` |
| TC-04 | US-01, US-03 | Navigasyonda "Hello, sign in" görünür | Giriş yapılmamış | 1. Ana sayfayı aç | "Account & Lists" alanında "sign in" metni görünür | P1 | Smoke | ✅ `@TC04` |
| TC-05 | US-03 | Hover menüsünde Sign in ve yeni müşteri linki | Giriş yapılmamış | 1. "Account & Lists" üzerine gel | Flyout'ta "Sign in" butonu ve "Start here" linki görünür | P2 | UI | ✅ `@TC05` |
| TC-06 | US-03 | Ana sayfadan giriş sayfasına yönlendirme | Giriş yapılmamış | 1. "Hello, sign in" tıkla | Giriş sayfası açılır; e-posta alanı ve "Continue" butonu görünür | P1 | Smoke | ✅ `@TC06` |
| TC-07 | US-04 | Boş e-posta ile devam | Giriş sayfası açık | 1. E-posta boş 2. "Continue" tıkla | "Enter your email address or mobile phone number" hatası; şifre adımına geçilmez | P1 | Negative | ✅ `@TC07` |
| TC-08 | US-04 | Kayıtsız / geçersiz e-posta | Giriş sayfası açık | 1. `qa.invalid.user.<timestamp>@example.invalid` gir 2. "Continue" | Hata veya "hesap oluştur" yönlendirmesi; şifre alanı görünmez | P1 | Negative | ✅ `@TC08` (Scenario Outline) |
| TC-09 | US-05 | Geçerli e-posta + şifre ile giriş | Kayıtlı test hesabı, env: `AMAZON_EMAIL`, `AMAZON_PASSWORD` | 1. E-posta gir, Continue 2. Şifre gir, Sign in | Ana sayfada "Hello, &lt;Ad&gt;" görünür | P1 | E2E | ⚠️ `@credentials` (varsayılan koşuda hariç) |
| TC-10 | US-05 | Yanlış şifre | Kayıtlı test hesabı | 1. Geçerli e-posta 2. Yanlış şifre | "Your password is incorrect" hatası | P1 | Negative | ⚠️ `@credentials` |
| TC-11 | US-05 | CAPTCHA / OTP akışı | Şüpheli giriş tetiklenmiş | Manuel | CAPTCHA / OTP ekranı gösterilir | P3 | Security | ❌ Manuel (bot korumasının otomasyonla aşılması kapsam dışı) |

## Kapsam Dışı / Riskler
- Amazon, otomasyon tarayıcılarına zaman zaman CAPTCHA ("Enter the characters you see below") gösterir. Framework bu sayfayı **tespit eder ve testi açık bir mesajla işaretler**; CAPTCHA'yı aşmaya çalışmaz.
- Canlı üretim sitesine karşı test: veriler ve metinler Amazon tarafından değiştirilebilir; locator'lar çoklu fallback ile yazılmıştır.
