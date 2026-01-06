CREATE DATABASE skillhub;

\connect skillhub

-- =====================================================
-- TABLA: users
-- =====================================================

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL
);

-- =====================================================
-- TABLA: courses
-- =====================================================

CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    module VARCHAR(50) NOT NULL,
    badge VARCHAR(255) NOT NULL
);

-- =====================================================
-- TABLA: progress
-- =====================================================

CREATE TABLE progress (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    course_id INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL,
    started_at DATE,
    completed_at DATE,

    CONSTRAINT fk_progress_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_progress_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_user_course
        UNIQUE (user_id, course_id)
);

-- =====================================================
-- DATA DE PRUEBA
-- =====================================================

-- Usuarios
-- Contraseña real para todos: password123
-- Hash BCrypt
INSERT INTO users (username, email, password, role, active) VALUES
(
  'admin',
  'test1@gmail.com',
  '$2a$10$haB3QJfhMNYqUZ0itfxmL.q7OrWyr8wdrkb7Ej3l3tTzH3qUjEXue',
  'ADMIN',
  TRUE
),
(
  'brayan',
  'test2@gmail.com',
  '$2a$10$haB3QJfhMNYqUZ0itfxmL.q7OrWyr8wdrkb7Ej3l3tTzH3qUjEXue',
  'USER',
  TRUE
),
(
  'juan',
  'test3@gmail.com',
  '$2a$10$haB3QJfhMNYqUZ0itfxmL.q7OrWyr8wdrkb7Ej3l3tTzH3qUjEXue',
  'USER',
  TRUE
);

-- Cursos
INSERT INTO courses (title, description, module, badge) VALUES
(
  'Fullstack Fundamentals',
  'HTML, CSS, JavaScript y fundamentos de backend',
  'FULLSTACK',
  'assets/imagenes/ins1.png'
),
(
  'APIs REST con Spring Boot',
  'Diseño e implementación de APIs REST',
  'APIS',
  'assets/imagenes/ins2.png'
),
(
  'Introducción a Cloud',
  'Fundamentos de computación en la nube',
  'CLOUD',
  'assets/imagenes/ins3.png'
),
(
  'Data Engineering Basics',
  'Procesamiento de datos y pipelines',
  'DATA',
  'assets/imagenes/ins4.png'
);

-- Progreso
INSERT INTO progress (user_id, course_id, status, started_at, completed_at) VALUES
(2, 1, 'COMPLETED', '2025-01-01', '2025-01-10'),
(2, 2, 'IN_PROGRESS', '2025-01-10', NULL),
(3, 3, 'COMPLETED', '2025-02-01', '2025-02-05');

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
