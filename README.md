
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

