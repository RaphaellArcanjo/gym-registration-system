CREATE TABLE student(
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(75) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    sex CHAR(1) CHECK (sex IN ('M', 'F')),
    date_of_birth DATE NOT NULL
);