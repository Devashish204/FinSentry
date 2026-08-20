CREATE TABLE documents (
                           id BIGSERIAL PRIMARY KEY,

                           loan_application_id BIGINT NOT NULL,
                           type VARCHAR(50) NOT NULL,
                           storage_ref VARCHAR(500) NOT NULL,
                           verification_status VARCHAR(50) NOT NULL,
                           uploaded_at TIMESTAMP WITH TIME ZONE NOT NULL,

                           CONSTRAINT fk_document_loan_application
                               FOREIGN KEY (loan_application_id)
                                   REFERENCES loan_applications(id)
);