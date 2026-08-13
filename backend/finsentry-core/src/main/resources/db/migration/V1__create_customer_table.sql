CREATE TABLE Customers(
    id BIGSERIAL PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    middle_name varchar(255),
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    phone varchar(20)  NOT NULL UNIQUE,
    date_of_birth date NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);