-- =========================================================
--  GroupBooking schema.sql
--  PostgreSQL
-- =========================================================

-- 1. Drop Tables
DROP TABLE IF EXISTS booking_trans;
DROP TABLE IF EXISTS booking_detail;
DROP TABLE IF EXISTS booking_file;


-- 2. Create Tables
-- 2.1 booking_file
CREATE TABLE booking_file (
    id            SERIAL PRIMARY KEY,
    file_name     VARCHAR(255) NOT NULL,
    create_dttm   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_rows    INTEGER      NOT NULL DEFAULT 0,
    complete_rows INTEGER      NOT NULL DEFAULT 0
);

-- 2.2 booking_detail
CREATE TABLE booking_detail (
    file_id       INTEGER      NOT NULL,
    row_num       INTEGER      NOT NULL,
    hotel_code    CHAR(5)      NOT NULL,
    arrivial_dt   DATE         NOT NULL,
    departure_dt  DATE         NOT NULL,
    room_type     CHAR(4)      NOT NULL,
    guest_name    VARCHAR(100) NOT NULL,
    status        CHAR(1)      NOT NULL CHECK (status IN ('N','S','C','F')),
    error_msg     VARCHAR(500),
    cnf_num       VARCHAR(50),
    PRIMARY KEY (file_id, row_num),
    FOREIGN KEY (file_id) REFERENCES booking_file(id) ON DELETE CASCADE
);

-- 2.3 booking_trans
CREATE TABLE booking_trans (
    id        SERIAL PRIMARY KEY,
    file_id   INTEGER NOT NULL,
    row_num   INTEGER NOT NULL,
    payload   JSONB   NOT NULL,
    response  JSONB,
    retry_cnt INTEGER NOT NULL DEFAULT 0,
    status    CHAR(1) NOT NULL CHECK (status IN ('N','C','F')),
    FOREIGN KEY (file_id, row_num)
        REFERENCES booking_detail(file_id, row_num)
        ON DELETE CASCADE
);


-- 3. Prepare Sample Data
-- 3.1 prepare booking_file data
INSERT INTO booking_file (file_name, total_rows, complete_rows)
VALUES ('FirstFile.xls', 3, 0);

-- 3.2 Prepare booking_detail data
INSERT INTO booking_detail
(file_id, row_num, hotel_code, arrivial_dt, departure_dt, room_type, guest_name, status)
VALUES
(1, 1, 'CHIRC', '2025-08-20', '2025-08-25', 'KING', 'Alice Smith', 'N'),
(1, 2, 'CHIRC', '2025-08-21', '2025-08-26', 'TWIN', 'Bob Johnson', 'N'),
(1, 3, 'CHIRC', '2025-08-22', '2025-08-27', 'KING', 'Carol White', 'N');

-- 3.3 prepare booking_trans data
INSERT INTO booking_trans (file_id, row_num, payload, status)
SELECT
    file_id,
    row_num,
    jsonb_build_object(
        'hotel_code', hotel_code,
        'arrivial_dt', arrivial_dt,
        'departure_dt', departure_dt,
        'room_type', room_type,
        'guest_name', guest_name
    ),
    'N'
FROM booking_detail;
