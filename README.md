
# DocTrivia AI

מערכת Backend ליצירת משחקי טריוויה חכמים על בסיס מסמכים, בשילוב AI, Kafka, PostgreSQL ודוקר.

## 🚀 תיאור הפרויקט

DocTrivia AI הוא שירות Backend המאפשר העלאת מסמכים, הפקת שאלות טריוויה אוטומטיות בעזרת AI, ניהול היסטוריית משחקים, עבודה אסינכרונית עם Kafka, שמירת נתונים ב-PostgreSQL, ותיעוד API מלא ב-Swagger.

## 🛠️ טכנולוגיות מרכזיות

- Java 21
- Spring Boot 3.x
- PostgreSQL
- Apache Kafka
- Docker & Docker Compose
- Swagger/OpenAPI (springdoc)
- Lombok
- GitHub Actions (CI/CD)
- אינטגרציה עם OpenAI API או LLM אחר

## 📦 מבנה הפרויקט

doc-trivia-ai/
│
├── src/main/java/com/yourorg/doctrivia/
│ ├── controller
│ ├── service
│ ├── repository
│ ├── model
│ ├── config
│ └── util
│
├── src/main/resources/
│ └── application.yml
├── Dockerfile
├── docker-compose.yml
├── README.md
└── ...


---

## 🌐 דוקומנטציית API (Swagger)

- מריצים את הפרויקט (מקומית או בדוקר)
- נכנסים לכתובת:
  http://localhost:8080/swagger-ui.html
- - אפשר לבדוק/לדבג כל Endpoint בקלות!

---

## ⚡ דוגמת שימוש (POST Document)

```json
POST /api/documents
{
"filename": "AI Handbook.pdf",
"content": "This document covers basics of AI, including neural networks.",
"status": "pending"
}
🧠 אינטגרציה עם AI
שירות Spring Boot שמתחבר ל-OpenAI או LLM אחר, מייצר שאלות טריוויה ממסמכים.

אפשר לשדרג את המימוש לכל AI/LLM שתבחרו (הפרויקט גמיש לחלוטין).

🕹️ תהליך עבודה כללי
העלאת מסמך דרך ה-API .

יצירת שאלות אוטומטית ע"י AI (או ידנית)

הצגת/שמירת שאלות במערכת (שייכות למסמך)

ניהול היסטוריית תשובות/משחקים למשתמש

הפקת סטטיסטיקות וסקירות למידה

🐳 הרצה מהירה עם Docker

docker-compose up --build

ירים את הסביבה: Postgres, Kafka, והפרויקט עצמו.

🤖 פיצ'רים מתקדמים
עבודה אסינכרונית עם Kafka (משחקים בזמן אמת, הודעות)

CI/CD מלא (GitHub Actions)

קונפיגורציה נוחה להחלפת ספקי AI

