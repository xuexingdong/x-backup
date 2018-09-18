-- 用户表
DROP TABLE IF EXISTS "user";
CREATE TABLE "public"."user" (
  "id"            char(32) COLLATE def                    NOT NULL,
  "openid"        varchar(255),
  "username"      varchar(255)                            NOT NULL,
  "password"      varchar(255)                            NOT NULL,
  "salt"          varchar(255)                            NOT NULL,
  "points"        int4                                    NOT NULL default 0,
  "created_at"    timestamp(6)                            NOT NULL,
  "updated_at"    timestamp(6),
  "deleted"       bool                                    NOT NULL default false,
  "deleted_at"    timestamp(6),
  "deleted_by"    char(32) COLLATE "pg_catalog"."default",
  "deleted_token" char(32) COLLATE "pg_catalog"."default" NOT NULL default ''
);

ALTER TABLE "public"."user"
  ADD CONSTRAINT "unq_username" UNIQUE ("username", "deleted_token");

ALTER TABLE "public"."user"
  ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");

-- 用户角色表
DROP TABLE IF EXISTS "rel_user_role";
CREATE TABLE "rel_user_role" (
  "id"      serial4      NOT NULL,
  "user_id" char(32)     NOT NULL,
  "role"    varchar(255) NOT NULL
);

ALTER TABLE "rel_user_role"
  ADD CONSTRAINT "unq_user_role" UNIQUE ("user_id", "role");

ALTER TABLE "rel_user_role"
  ADD CONSTRAINT "user_role_pkey" PRIMARY KEY ("id");
