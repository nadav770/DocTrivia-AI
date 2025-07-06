# DocTrivia AI

**DocTrivia AI** is an intelligent backend platform for generating trivia quizzes from educational documents using AI (OpenAI/LLM), built with Java, Spring Boot, PostgreSQL, Docker, and fully documented via Swagger.

---

## 🚀 Project Overview

DocTrivia AI allows users to upload study documents, automatically generates trivia questions using OpenAI, and manages game history and user answers. The app provides a RESTful API for client applications (React, mobile, etc.), supports JWT authentication, and can be easily deployed with Docker.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.x**
- **PostgreSQL 16**
- **OpenAI API (or other LLM)**
- **Docker & Docker Compose**
- **Spring Security + JWT Authentication**
- **Swagger / OpenAPI 3 (springdoc)**
- **Lombok**
- **GitHub Actions (optional, CI/CD)**

---

## 📂 Project Structure

DocTrivia-AI/
├── doctrivia/ # Spring Boot backend source
│ ├── src/
│ ├── pom.xml
│ └── ...
├── client/ #  React frontend (recommended)
│ ├── src/
│ ├── package.json
│ └── ...
├── docker-compose.yml
├── README.md
└── ...

---

## ⚡ Main Features

- **Upload Document:** Accepts plain text or future PDF (todo).
- **Generate Trivia:** Uses AI to create 4-option multiple-choice questions from document.
- **Answer Management:** Stores user answers and calculates game score.
- **JWT Authentication:** Secures API endpoints.
- **API Documentation:** Swagger-UI auto-generated.
- **Easy Deployment:** Run locally or with Docker Compose.

---

## 🚧 Quickstart (Local Dev)

### 1. **Clone the Repository**
`bash
git clone https://github.com/yourorg/DocTrivia-AI.git
cd DocTrivia-AI/doctrivia

2. **Configure Environment**
Edit src/main/resources/application.properties as needed
(set PostgreSQL DB, OpenAI API Key, etc.)
3. **Run Database with Docker**

docker-compose up -d

4. **Run Backend**
   ./mvnw spring-boot:run
# or with IntelliJ "Run"

5. **Access Swagger-UI (API Docs)**

http://localhost:8080/swagger-ui.html

🤖 **AI Integration**

Uses OpenAI Chat Completions API

Requires a valid API key (openai.api.key in properties)

Example Prompt to AI:

"Generate 5 trivia questions based on this document, each with 4 answer options and mark the correct one. Return JSON."

🔐 **Authentication**

JWT tokens are required for protected endpoints.

Register/login with /api/auth/register and /api/auth/login.

Authenticate with the Bearer <token> in the Authorization header.

📦 **Example API Request**

POST /api/ai/generate-questions
Body:

{
"content": "Here is a summary of the chapter on World War II..."
}

📝 **How to Add PDF Support**

1.Add a file upload endpoint in your controller.

2.Use a PDF parser library (e.g. PDFBox) to extract text server-side.

3.Send the extracted text to the AI endpoint as before.

💡  **Future Improvements**
Frontend client in React for end-to-end demo

PDF file upload and parsing

Game history and stats UI

Multi-user support & leaderboard

👤 **Author**
Nadav (Backend Developer, Python & Java, AI Integration)
LinkedIn Profile  www.linkedin.com/in/nadav-hakmon 






