
-- =========================================
-- Table 1: app_users
-- Stores registered user information
-- =========================================

CREATE TABLE app_users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT uk_app_users_email UNIQUE (email)
) ENGINE=InnoDB;


-- =========================================
-- Table 2: job_applications
-- Stores applications belonging to users
-- =========================================

CREATE TABLE job_applications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,

    company_name VARCHAR(150) NOT NULL,
    job_title VARCHAR(150) NOT NULL,

    application_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'APPLIED',

    employment_type VARCHAR(30),
    location VARCHAR(150),
    job_url VARCHAR(1000),

    salary_min DECIMAL(12,2),
    salary_max DECIMAL(12,2),
    currency_code CHAR(3) DEFAULT 'INR',

    notes TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT fk_job_applications_user
        FOREIGN KEY (user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE,

    INDEX idx_job_applications_user_id (user_id),
    INDEX idx_job_applications_status (status),
    INDEX idx_job_applications_application_date (application_date)

) ENGINE=InnoDB;
