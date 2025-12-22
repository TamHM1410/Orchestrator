-- =============================================
-- Flyway Migration: Create user_auth_model table
-- =============================================

CREATE TABLE user_auth_model (
                                 id BIGSERIAL PRIMARY KEY,               -- Auto-increment
                                 ip TEXT,                                -- Client IP
                                 fail_login_attempts INT DEFAULT 0,      -- Number of failed logins
                                 username VARCHAR(255) NOT NULL,         -- Username
                                 user_id BIGINT NOT NULL,                -- Reference to user table (FK nếu muốn)
                                 refresh_token TEXT,                      -- JWT refresh token
                                 created_at TIMESTAMP DEFAULT now(),     -- Optional: track creation time
                                 updated_at TIMESTAMP DEFAULT now()      -- Optional: track last update
);

-- Optional: Add index for quick lookup by username or user_id
CREATE UNIQUE INDEX idx_user_auth_username ON user_auth_model(username);
CREATE INDEX idx_user_auth_user_id ON user_auth_model(user_id);
