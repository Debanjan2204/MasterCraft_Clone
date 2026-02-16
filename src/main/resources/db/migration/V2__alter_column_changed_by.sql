ALTER TABLE ticket_history ALTER COLUMN changed_by TYPE VARCHAR(255) USING changed_by::VARCHAR;
