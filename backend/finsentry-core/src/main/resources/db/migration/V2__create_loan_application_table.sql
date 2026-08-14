CREATE TABLE loan_applications (
                                   id BIGSERIAL PRIMARY KEY,

                                   customer_id BIGINT NOT NULL,
                                   loan_product varchar(50) NOT NULL,
                                   loan_amount NUMERIC(15, 2) NOT NULL,
                                   tenure_months INTEGER NOT NULL,
                                   purpose varchar(500) NOT NULL,
                                   status varchar(50) NOT NULL,

                                   created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                   updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

                                   version BIGINT NOT NULL DEFAULT 0,

                                   CONSTRAINT fk_loan_application_customer
                                       FOREIGN KEY (customer_id)
                                           REFERENCES Customers(id)
);