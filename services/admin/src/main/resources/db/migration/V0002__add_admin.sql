INSERT INTO user (id, username, password, remark_name, points, created_at)
VALUES ('d13fd44ef49a496487e2140d87873606',
        'xuexingdong',
        '$2y$04$QQ/mWVPjGFPmXQ/1Z1HqX.z.A97u7R5vjy5w0kaUZVbS/IupoBHgy',
        '',
        0,
        now());

INSERT INTO rel_user_role (user_id, role)
VALUES ('d13fd44ef49a496487e2140d87873606', 'ADMIN');