INSERT INTO "public"."user" (id, username, password, openid, "salt", created_at)
VALUES
  ('d13fd44ef49a496487e2140d87873606', 'xuexingdong', '$2y$04$cFTjcEjsX0PtZkauKB.uK.F0l6oS/mkt8mU7krRvZ9Mr7eWIFxxIq',
   'oBYzjs7CPSav_IA2C8tGYl1i8mjM', 'xuexingdong', now());

INSERT INTO "public"."rel_user_role" (user_id, role)
VALUES
  ('d13fd44ef49a496487e2140d87873606', 'ADMIN');