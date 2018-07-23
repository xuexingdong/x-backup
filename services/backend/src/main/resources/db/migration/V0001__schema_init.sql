-- 用户表
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "id"            char(32) COLLATE "pg_catalog"."default"     NOT NULL,
  "openid"        varchar(255) COLLATE "pg_catalog"."default",
  "username"      varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password"      varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "salt"          varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "points"        int4                                        NOT NULL default 0,
  "created_at"    timestamp(6)                                NOT NULL,
  "updated_at"    timestamp(6),
  "deleted"       bool                                        NOT NULL default false,
  "deleted_at"    timestamp(6),
  "deleted_by"    char(32) COLLATE "pg_catalog"."default",
  "deleted_token" char(32) COLLATE "pg_catalog"."default"     NOT NULL default ''
);

ALTER TABLE "public"."user"
  ADD CONSTRAINT "unq_username" UNIQUE ("username", "deleted_token");

ALTER TABLE "public"."user"
  ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");

-- 用户角色表
DROP TABLE IF EXISTS "public"."rel_user_role";
CREATE TABLE "public"."rel_user_role" (
  "id"      serial4      NOT NULL,
  "user_id" char(32)     NOT NULL,
  "role"    varchar(255) NOT NULL
);

ALTER TABLE "public"."rel_user_role"
  ADD CONSTRAINT "unq_user_role" UNIQUE ("user_id", "role");

ALTER TABLE "public"."rel_user_role"
  ADD CONSTRAINT "user_role_pkey" PRIMARY KEY ("id");
